package ltw.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ltw.dao.PostDao;
import ltw.model.Post;

@Controller
@RequestMapping("/post")
public class PostController {
	@Autowired
	private PostDao pDao;
	
	@GetMapping
	public String index(Model model, @ModelAttribute("id")int id) {
		Post post = pDao.getPost(id);
		model.addAttribute("post", post);
		List<String> linhvucs = pDao.getLinhVuc();
		model.addAttribute("linhvuc", linhvucs);
		List<Post> posts = pDao.getPostsRelated(post.getLinhvuc());
		model.addAttribute("post", posts);
		return "post";
	}
	
	@GetMapping("/{id}")
	public String indexPost(Model model, @PathVariable("id")int id) {
		Post post = pDao.getPost(id);
		model.addAttribute("post", post);
		List<String> linhvucs = pDao.getLinhVuc();
		model.addAttribute("linhvuc", linhvucs);
		List<Post> posts = pDao.getPostsRelated(post.getLinhvuc());
		model.addAttribute("posts", posts);
		return "post";
	}
}

 