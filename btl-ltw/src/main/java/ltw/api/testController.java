package ltw.api;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ltw.dao.CommentDao;
import ltw.dao.PostDao;
import ltw.model.Comment;
import ltw.model.CommentReply;
import ltw.model.Post;
import ltw.model.User;


//import com.example.demo.model.Comment;
//import com.example.demo.model.Product;
//import com.example.demo.model.Product;

@RestController
public class testController {

	@Autowired
	private PostDao pDao;
	
	@Autowired
	private CommentDao cDao;
	
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
		List<Post> posts = pDao.getPostsRelated(post.getLinhvuc(),id);
		model.addAttribute("post", posts);
		List<Comment> comments = cDao.getComments(id);
		HashMap<Integer, Comment> map = new HashMap<>();
		for(Comment comment : comments) {
			if(!map.containsKey(comment.getId())) {
				map.put(comment.getId(), comment);
			}
			else {
				Comment com = map.get(comment.getId());
				List<CommentReply> comre = com.getCommentReplys();
				comre.addAll(comment.getCommentReplys());
				com.setCommentReplys(comre);
				map.put(comment.getId(), com);
			}
		}
		return map.values().toString();
	}
	
	@PostMapping("/test")
	public String text(User user) {
		return user.toString();
	}

}
