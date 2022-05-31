package ltw.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ltw.model.Comment;
import ltw.model.CommentReply;

@Repository
public class CommentDaoAPI implements CommentDao{
	@Autowired
	private UserDao uDao;

	@Autowired
	private JdbcTemplate jdbc;
	@Override
	public List<Comment> getComments(int idpost) {
		String sql ="select * from comment where comment.idpost = ? ";
		List<Comment> comments = jdbc.query(sql, this::rowMapComment,idpost);
		return comments;
	}
	
	
	public List<CommentReply> getCommentsReply(int idcomment) {
		String sql ="select * from commentreply where commentreply.idcomment = ?";
		List<CommentReply> comments = jdbc.query(sql, this::rowMapCommentReply,idcomment);
		return comments;
	}
	
	private CommentReply rowMapCommentReply(ResultSet rs,int rowNum)  throws SQLException{
		return new CommentReply(rs.getInt(1),rs.getInt(2),uDao.findUser(rs.getInt(3)),rs.getString(4),rs.getDate(5));
	}
	
	private Comment rowMapComment(ResultSet rs,int rowNum)  throws SQLException{
		return new Comment(rs.getInt(1),uDao.findUser(rs.getInt(2)),rs.getInt(3),getCommentsReply(rs.getInt(1)),rs.getString(4),rs.getDate(5));
	}

}
