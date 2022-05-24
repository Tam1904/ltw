package ltw.dao;

import java.util.List;

import ltw.model.Post;

public interface PostDao {
	void post(Post post, int idUser);
	void delete(int id);
	void duyet(int id);
	List<Post> getPosts(String text);
	List<Post> getPostsThongKe(String text);
	List<Post> getPostsThongKe(String text,String linhvuc);
	List<Post> getPostsThongKeOld(String text,String linhvuc);
	List<Post> getPostsThongKeNew(String text,String linhvuc);
	List<Post> getPostsByID(int id);
	Post getPost(int id);
	List<Post> getPostsRelated(String linhvuc);
	List<String> getLinhVuc();
}
