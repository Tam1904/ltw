package ltw.api;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.SessionAttribute;

import ltw.dao.PostDao;
import ltw.model.Post;
import ltw.model.User;

@Controller
@RequestMapping("/blog")
public class BlogController {
	@Autowired
	private PostDao pDao;
	
	@GetMapping()
	public String index() {
		return "post";
	}
	
	@GetMapping("/dangpost")
	public String dangpost (HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user == null) {
			return "redirect:/login";
		}
		return "dangbai";
	}
	
	@PostMapping("/post")
	public String post (Post post, @SessionAttribute("user") User user) {
		pDao.post(post, user.getId());
		return "index";
	}
	
	
	@GetMapping("/manager")
	public String manager(Model model, HttpSession session) {
		List<Post> posts = pDao.getPosts("");
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
		return "quanlyblog";
	}
	
	@GetMapping("/manager/page/{current}")
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
		return "quanlyblog";
	}
	
	
	@PostMapping("/manager/search")
	public String managerSearch(Model model,HttpSession session, @RequestParam("text")String text) {
		List<Post> posts = pDao.getPosts(text);
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
		return "quanlyblog";
	}
	
	
	
	@PostMapping("/detail/{id}")
	public String detailId(@PathVariable("id")int id, Model model) {
		Post post = pDao.getPost(id);
		model.addAttribute("post", post);
		return "chitietbaidang";
	}
	
	
	@PostMapping("/duyet/{id}")
	public String duyet(@PathVariable("id")int id) {
		pDao.duyet(id);
		return "redirect:/blog/manager/all";
	}
	
	@PostMapping("/xoa/{id}")
	public String xoa(@PathVariable("id")int id) {
		pDao.delete(id);
		return "redirect:/blog/manager/all";
	}
	
	@GetMapping("/manager/all")
	public String managerPostAll(Model model,HttpSession session) {
		String text = (String) session.getAttribute("text");
		if(text == null) {
			text = "";
		}
		List<Post> posts = pDao.getPosts(text);
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
		return "quanlyblog";
	}
	
	@PostMapping("/manager/daduyet")
	public String daduyet(Model model, HttpSession session) {
		String text = (String) session.getAttribute("text");
		if(text == null) {
			text = "";
		}
		List<Post> posts = pDao.getPosts(text);
		List<Post> postDa = new ArrayList<>();
		for(Post post: posts) {
			if(post.getTrangthai()==1) {
				postDa.add(post);
			}
		}
		PagedListHolder<?> pages = new PagedListHolder<>(postDa);
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
		return "quanlyblog";
	}
	
	@PostMapping("/manager/chuaduyet")
	public String chuaduyet(Model model, HttpSession session) {
		String text = (String) session.getAttribute("text");
		if(text == null) {
			text = "";
		}
		List<Post> posts = pDao.getPosts(text);
		List<Post> postCh = new ArrayList<>();
		for(Post post: posts) {
			if(post.getTrangthai()==0) {
				postCh.add(post);
			}
		}
		PagedListHolder<?> pages = new PagedListHolder<>(postCh);
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
		return "quanlyblog";
	}
	
	@GetMapping("/manager/back")
	public String back(Model model, HttpSession session) {
		PagedListHolder<?> pages = (PagedListHolder<?>) session.getAttribute("PageList");
		int pagesize = 3;
		pages.setPageSize(pagesize);
		pages.setPage(0);
		int begin = 1;
		int end = Math.min(4, pages.getPageCount());
		model.addAttribute("current", 1);
		model.addAttribute("begin", begin);
		model.addAttribute("end", end);
		session.setAttribute("PageList", pages);
		model.addAttribute("posts", pages.getPageList());
		return "quanlyblog";
	}
}
