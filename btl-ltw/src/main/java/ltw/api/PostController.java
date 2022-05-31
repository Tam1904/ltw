package ltw.api;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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

import ltw.dao.CommentDao;
import ltw.dao.PostDao;
import ltw.model.Comment;
import ltw.model.CommentReply;
import ltw.model.Post;
import ltw.model.User;

@Controller
@RequestMapping("/post")
public class PostController {
	@Autowired
	private PostDao pDao;
	
	@Autowired
	private CommentDao cDao;
	
	@GetMapping("/{id}")
	public String indexPost(Model model, @PathVariable("id")int id, HttpSession session) {
		session.removeAttribute("text");
		Post post = pDao.getPost(id);
		pDao.incrementView(id);
		model.addAttribute("post", post);
		List<String> linhvucs = pDao.getLinhVuc();
		model.addAttribute("linhvucs", linhvucs);
		List<Post> posts = pDao.getPostsRelated(post.getLinhvuc(),id);
		model.addAttribute("posts", posts);
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
		model.addAttribute("comments", map.values());
		User user = (User) session.getAttribute("user");
		if(user !=null && checkUser(user.getId(), comments)) {
			model.addAttribute("quyen", false);
		}
		return "post";
	}
	
	public boolean checkUser(int iduser, List<Comment> comments) {
		for(Comment comment: comments) {
			if(comment.getUser().getId()==iduser) {
				return true;
			}
		}
		return false;
	}
	
	@PostMapping("/like+")
	public ResponseEntity<String> incLike(@ModelAttribute("idpost")int idpost) {
		pDao.incrementLike(idpost);
		return new ResponseEntity<> ("1", HttpStatus.OK);
	}
	
	@PostMapping("/like-")
	public ResponseEntity<String> decLike(@ModelAttribute("idpost")int idpost) {
		pDao.decrementLike(idpost);
		return new ResponseEntity<> ("1", HttpStatus.OK);
	}
	
	@PostMapping("/comment")
	public String postComment(@RequestParam("comment")String comment, @RequestParam("idpost")int idpost,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null) {
			return "redirect:/login";
		}
		pDao.comment(comment, idpost, user.getId());
		return "redirect:/post/" + idpost;
	}
	
	@PostMapping("/postReply")
	public String postCommentReply(@RequestParam("comment")String comment, @RequestParam("idcomment")int idcomment,
			@RequestParam("idpost")int idpost, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user==null) {
			return "redirect:/login";
		}
		pDao.commentReply(comment,idcomment, user.getId(),idpost);
		return "redirect:/post/" + idpost;
	}
}

 