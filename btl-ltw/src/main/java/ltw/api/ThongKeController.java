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
import ltw.model.Post;

@Controller
@RequestMapping("/thongke")
public class ThongKeController {
	
	@Autowired
	private PostDao pDao;

	@GetMapping
	public String index(Model model, HttpSession session) {
		List<Post> posts = pDao.getPostsThongKe("","");
		PagedListHolder<?> pages = new PagedListHolder<>(posts);
		List<String> linhvucs = pDao.getLinhVuc();
		session.setAttribute("linhvucs", linhvucs);
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
		session.removeAttribute("linhvuc");
		session.setAttribute("thongke", 1);
		return "thongke";
	}
	
	@GetMapping("/linhvuc")
	public String linhvuc(Model model, HttpSession session) {
		String linhvuc = "Món Ngon Việt Nam";
		List<Post> posts = pDao.getPostsThongKe("",linhvuc);
		session.setAttribute("linhvuc", linhvuc);
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
		session.setAttribute("thongke", 2);
		return "thongke";
	}
	
	@GetMapping("/page/{current}")
	public String managerPage(Model model, HttpSession session, @PathVariable("current")int current) {
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
		return "thongke";
	}
	
	@PostMapping("/search")
	public String thongkeSearch(Model model,HttpSession session, @RequestParam("text")String text) {
		String linhvuc = (String) session.getAttribute("linhvuc");
		if(linhvuc == null) {
			linhvuc = "";
		}
		List<Post> posts = pDao.getPostsThongKe(text,linhvuc);
		session.setAttribute("text", text);
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
		return "thongke";
	}
	
	@PostMapping("/loc")
	public String thongkeLoc(Model model,HttpSession session, @RequestParam("linhvuc")String linhvuc) {
		String text = (String) session.getAttribute("text");
		if(text == null) {
			text = "";
		}
		List<Post> posts = pDao.getPostsThongKe(text,linhvuc);
		session.setAttribute("linhvuc", linhvuc);
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
		return "thongke";
	}
	
	@GetMapping("/new")
	public String thongkeNew(Model model,HttpSession session) {
		String linhvuc = (String) session.getAttribute("linhvuc");
		if(linhvuc == null) {
			linhvuc = "";
		}
		String text = (String) session.getAttribute("text");
		if(text == null) {
			text = "";
		}
		List<Post> posts = pDao.getPostsThongKeNew(text,linhvuc);
		session.setAttribute("text", text);
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
		return "thongke";
	}
	
	@GetMapping("/old")
	public String thongkeOld(Model model,HttpSession session) {
		String linhvuc = (String) session.getAttribute("linhvuc");
		if(linhvuc == null) {
			linhvuc = "";
		}
		String text = (String) session.getAttribute("text");
		if(text == null) {
			text = "";
		}
		List<Post> posts = pDao.getPostsThongKeOld(text,linhvuc);
		session.setAttribute("text", text);
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
		return "thongke";
	}
}
