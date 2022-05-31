package ltw.dao;

import java.util.List;

import ltw.model.Comment;

public interface CommentDao {
	List<Comment> getComments(int idpost);
}
