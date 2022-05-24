package ltw.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ltw.dao.PostDao;
import ltw.dao.UserDao;
import ltw.model.Post;
import ltw.model.User;

@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private UserDao uDao;
	
	@Autowired
	private PostDao pDao;
	
	@GetMapping
	public String index() {
		return "index";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute(new User());
		return "dangnhap";
	}
	
	@GetMapping("/logout")
	public String logout(Model model, HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/";
	}
	
	@GetMapping("/account")
	public String account() {
		return "editAccount";
	}
	
	@PostMapping("/account")
	public String saveAccount(User user,@RequestParam("xacnhan")String xacnhan, Model model, HttpSession session) {
		if(!xacnhan.trim().equals(user.getPassword())) {
			model.addAttribute("message", "Mật khẩu xác nhận chưa chính xác");
			return "editAccount";
		}
		uDao.updateUser(user);
		return "redirect:/";
	}
	
	@GetMapping("/account/detail/{id}")
	public String accountDetail(@PathVariable("id")int id, HttpSession session, Model model) {
		User user = uDao.findUser(id);
		List<Post> posts = pDao.getPostsByID(id);
		PagedListHolder<?> pages = new PagedListHolder<>(posts);
		int pagesize = 3;
		pages.setPageSize(pagesize);
		pages.setPage(0);
		int current =1;
		int begin = 1;
		int end = Math.min(4, pages.getPageCount());
		model.addAttribute("current", current);
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		session.setAttribute("PageList", pages);
		model.addAttribute("posts", pages.getPageList());
		model.addAttribute("user", user);
		return "gioithieubanthan";
	}
	
	@GetMapping("/account/detail/{id}/page/{current}")
	public String managerPage(Model model, HttpSession session, @PathVariable("current")int current, @PathVariable("id")int id) {
		User user = uDao.findUser(id);
		model.addAttribute("user", user);
		PagedListHolder<?> pages = (PagedListHolder<?>) session.getAttribute("PageList");
		int pagesize = 3;
		pages.setPageSize(pagesize);
		if(current-1<0) {
			current = 1;
		}
		if(current >= pages.getPageCount()) {
			current = pages.getPageCount();
		}
		pages.setPage(current-1);
		int begin = Math.max(1, current- 4);
		int end = Math.min(4, pages.getPageCount());
		model.addAttribute("current", current);
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		session.setAttribute("PageList", pages);
		model.addAttribute("posts", pages.getPageList());
		return "gioithieubanthan";
	}
	
	@PostMapping("/login")
	public String checklogin(User user, HttpSession session, Model model) {
		if(uDao.checkUser(user.getUsername(),user.getPassword())) {
			user = uDao.findUser(user.getUsername());
			session.setAttribute("user", user);
			return "redirect:/";
		}
		else {
			model.addAttribute("message", "Thông tin tài khoản hoặc mật khẩu không chính xác");
			model.addAttribute("user",user);
			return "dangnhap";
		}
	}
}
