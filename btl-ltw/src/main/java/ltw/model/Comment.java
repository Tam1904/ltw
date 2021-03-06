package ltw.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
	private int id;
	private User user;
	private int idpost;
	private List<CommentReply> commentReplys;
	private String noidung;
	private Date ngaydang;

	public String getDate() {
		SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy");
		return fm.format(ngaydang);
	}

	public boolean getQuyen(int iduser) {
		for (CommentReply cm : commentReplys) {
			if (cm.getUser().getId() == iduser) {
				return false;
			}
		}
		return true;
	}
}
