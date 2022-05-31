package ltw.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentReply {
	private int id;
	private int idcomment;
	private User user;
	private String noidung;
	private Date ngaydang;
	
	public String getDate() {
		SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy");
		return fm.format(ngaydang);
	}
	
}
