package ltw.api;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String index(Model model, HttpSession session) {
		List<Post> posts = pDao.getPostsTrangThai("");
		List<Post> tops = pDao.getTopPosts();
		session.setAttribute("tops", tops);
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
		session.removeAttribute("text");
		return "index";
	}
	
	@GetMapping("/page/{current}")
	public String indexPage(Model model, HttpSession session,@PathVariable("current")int current) {
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
	
	@GetMapping("/signin")
	public String signin(Model model) {
		model.addAttribute(new User());
		return "dangky";
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
	
	@PostMapping("/signin")
	public String checksignin(User user,@RequestParam("passwordv2")String passwordv2,  HttpSession session, Model model) {
		if(uDao.exitsUser(user.getUsername())) {
			model.addAttribute("user", user);
			model.addAttribute("messageOne", "Tài khoản đã tồn tại");
			return "dangky";
		}
		else if(!uDao.exitsUser(user.getUsername()) && !user.getPassword().equals(passwordv2)) {
			session.setAttribute("user", user);
			model.addAttribute("user", user);
			model.addAttribute("messageOne", "Mật khẩu xác nhận chưa chính xác");
			return "dangky";
		}
		else {
			uDao.addUser(user);
			session.setAttribute("user", user);
			return "redirect:/";
		}
	}
	
	@GetMapping("/filter")
	public String filter(@ModelAttribute("linhvuc")String linhvuc, HttpSession session, Model model) {
		String text = (String) session.getAttribute("text");
		if(text==null) {
			text="";
		};
		model.addAttribute("filter", linhvuc);
		
		List<Post> posts = pDao.getPostsTrangThai(text, linhvuc );
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
		return "index";
	}
	
	
	@PostMapping("/search")
	public String search(@RequestParam("text")String text, HttpSession session, Model model) {
		List<Post> posts = pDao.getPostsTrangThai(text);
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
		session.setAttribute("text", text);
		return "index";
	}
	
	
	@PostMapping("/autoSearch")
	public ResponseEntity<String> autosearch(@ModelAttribute("text")String text) {
		List<Post> posts = pDao.getPostsTrangThai(text);
		if(posts.size()>5) {
			posts = posts.subList(0, 5);
		}
		String s= "";
		for(Post post : posts) {
			s+="<li class=\"header__seach-history-item\">"
					+ "														<a class=\"header__seach-history-item-link\" href='/post/"+ post.getId() + "'"
					+ "															title= '" + post.getTieude() + " ' >"
					+ "															<img src=" + post.getAnh() +  ""
					+ "																alt=\"\" class=\"header__seach-history-item-img\">"
					+ "															<div class=\"header__seach-history-item-product\">"
					+ "																<span class=\"header__seach-history-item-product-name\">" + post.getTieude() + " </span>"
					+ "																<div class=\"header__seach-history-item-product-price\">"
					+ "																	<span>" + post.getLuotxem()   + "</span>"
					+ "																	<span style=\"margin-left: 5px;\">Views</span>"
					+ "																</div>"
					+ "															</div>"
					+ "														</a>"
					+ "													</li>";
		}
		return new ResponseEntity<String>(s,HttpStatus.OK);
	}
	
	
	@PostMapping("/xoapost/{id}")
	public String xoa(@PathVariable("id")int id, @RequestParam("iduser")int iduser) {
		pDao.delete(id);
		return "redirect:/account/detail/" + iduser ;
	}
}
