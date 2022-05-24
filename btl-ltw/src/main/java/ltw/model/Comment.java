package ltw.model;

import java.util.Date;

import lombok.Data;

@Data
public class Comment {
	private int id;
	private int iduser;
	private int idpost;
	private String noidung;
	private Date ngay;
}
