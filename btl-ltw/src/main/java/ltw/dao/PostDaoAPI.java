package ltw.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ltw.model.Post;

@Repository
public class PostDaoAPI implements PostDao{
	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private UserDao uDao;

	@Override
	public void post(Post post, int idUser) {
		String sql = "insert into post(iduser,tieude,ngaydang,noidung,linhvuc,binhluan,luotxem,luotthich,anh,video,trangthai) "
				+ "value(?,?,now(),?,?,0,0,0,?,?,0)";
		jdbc.update(sql,idUser,post.getTieude(),post.getNoidung(),post.getLinhvuc(),post.getAnh(),post.getVideo());
	}

	@Override
	public void delete(int id) {
		String sql = "delete from post where id = ?";
		jdbc.update(sql,id);
	}

	@Override
	public void duyet(int id) {
		String sql = "update post set trangthai = 1 where id = ?";
		jdbc.update(sql,id);
	}

	@Override
	public List<Post> getPosts(String text) {
		String sql = "select * from post where ((select instr(tieude,?))>0)";
		return jdbc.query(sql, this::mapRowToPost,text);
	}
	
	
	private Post mapRowToPost(ResultSet rs, int rowNum) throws SQLException{
		return new Post(rs.getInt(1),uDao.findUser(rs.getInt(2)),rs.getString(3),rs.getDate(4),rs.getString(5),rs.getString(6),rs.getInt(7),rs.getInt(8),rs.getInt(9),rs.getInt(10),rs.getString(11),rs.getString(12));
	}

	@Override
	public Post getPost(int id) {
		String sql = "select * from post where id = ?";
		return jdbc.query(sql, this::mapRowToPost,id).get(0);
	}

	@Override
	public List<Post> getPostsThongKe(String text) {
		String sql = "select * from post where ((select instr(tieude,?))>0) and trangthai = 1 order by luotxem desc, luotthich desc, binhluan desc";
		return jdbc.query(sql, this::mapRowToPost,text);
	}

	@Override
	public List<Post> getPostsThongKe(String text, String linhvuc) {
		String sql = "select * from post where ((select instr(tieude,?))>0) and ((select instr(linhvuc,?))>0) and trangthai=1 order by luotxem desc, luotthich desc, binhluan desc";
		return jdbc.query(sql, this::mapRowToPost,text,linhvuc);
	}

	@Override
	public List<String> getLinhVuc() {
		String sql = "select DISTINCT(linhvuc) from post ";
		return jdbc.query(sql, this::mapRowtoLinhVuc);
	}
	
	private String mapRowtoLinhVuc(ResultSet rs, int rowNum) throws SQLException{
		return rs.getString(1);
	}

	@Override
	public List<Post> getPostsThongKeOld(String text, String linhvuc) {
		String sql = "select * from post where ((select instr(tieude,?))>0) and ((select instr(linhvuc,?))>0) and trangthai=1 order by ngaydang asc, luotxem desc, luotthich desc, binhluan desc";
		return jdbc.query(sql, this::mapRowToPost,text,linhvuc);
	}

	@Override
	public List<Post> getPostsThongKeNew(String text, String linhvuc) {
		String sql = "select * from post where ((select instr(tieude,?))>0) and ((select instr(linhvuc,?))>0) and trangthai=1 order by ngaydang desc, luotxem desc, luotthich desc, binhluan desc";
		return jdbc.query(sql, this::mapRowToPost,text,linhvuc);
	}

	@Override
	public List<Post> getPostsByID(int id) {
		String sql = "select * from post where iduser= ? and trangthai=1 order by ngaydang desc, luotxem desc, luotthich desc, binhluan desc";
		return jdbc.query(sql, this::mapRowToPost,id);
	}

	@Override
	public List<Post> getPostsRelated(String linhvuc,int idpost) {
		String sql = "select * from post where linhvuc= ? and id!= ? and trangthai=1 order by ngaydang desc,luotxem desc, luotthich desc, binhluan desc LIMIT 4";
		return jdbc.query(sql, this::mapRowToPost,linhvuc,idpost);
	}

	@Override
	public List<Post> getPostsTrangThai(String text) {
		String sql = "select * from post where ((select instr(tieude,?))>0) and trangthai =1 order by ngaydang desc";
		return jdbc.query(sql, this::mapRowToPost,text);
	}

	@Override
	public List<Post> getTopPosts() {
		String sql = "select * from post where trangthai =1 order by luotxem desc LIMIT 10";
		return jdbc.query(sql, this::mapRowToPost);
	}

	@Override
	public List<Post> getPostsTrangThai(String text, String linhvuc) {
		String sql = "select * from post where ((select instr(tieude,?))>0) and trangthai =1 and ((select instr(linhvuc,?))>0)  order by ngaydang desc";
		return jdbc.query(sql, this::mapRowToPost,text,linhvuc);
	}

	@Override
	public void incrementView(int idpost) {
		String sql = "update post set luotxem = luotxem + 1 where id = ?";
		jdbc.update(sql,idpost);
	}

	@Override
	public void incrementLike(int idpost) {
		String sql = "update post set luotthich = luotthich + 1 where id = ?";
		jdbc.update(sql,idpost);
	}

	@Override
	public void decrementLike(int idpost) {
		String sql = "update post set luotthich = luotthich - 1 where id = ?";
		jdbc.update(sql,idpost);
	}

	@Override
	public void comment(String comment, int idpost, int iduser) {
		String sql="insert into comment(iduser,idpost,noidung,ngay) value(?,?,?,now())";
		jdbc.update(sql,iduser,idpost,comment);
		sql = "update post set binhluan = binhluan +1 where id = ?";
		jdbc.update(sql,idpost);
	}

	@Override
	public void commentReply(String comment, int idcomment, int iduser,int idpost) {
		String sql="insert into commentreply(iduser,idcomment,noidung,ngaydang) value(?,?,?,now())";
		jdbc.update(sql,iduser,idcomment,comment);
		sql = "update post set binhluan = binhluan +1 where id = ?";
		jdbc.update(sql,idpost);
	}

}
