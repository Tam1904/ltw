package ltw.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ltw.dao.PostDao;
import ltw.model.Post;


//import com.example.demo.model.Comment;
//import com.example.demo.model.Product;
//import com.example.demo.model.Product;

@RestController
public class testController {

	@Autowired
	private PostDao pDao;
	
	@GetMapping("/test/{id}")
	public String detailId(@PathVariable("id")int id, Model model) {
		Post post = pDao.getPost(id);
		return post.getNoidungs().toString();
	}
	
	@GetMapping("/{id}")
	public String indexPost(Model model, @PathVariable("id")int id) {
		Post post = pDao.getPost(id);
		model.addAttribute("post", post);
		List<String> linhvucs = pDao.getLinhVuc();
		model.addAttribute("linhvuc", linhvucs);
		List<Post> posts = pDao.getPostsRelated(post.getLinhvuc());
		model.addAttribute("post", posts);
		return post.toString();
	}

}
