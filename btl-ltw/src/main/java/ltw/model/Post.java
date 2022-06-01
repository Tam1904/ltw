package ltw.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	private int id;
	private User user ;
	private String tieude;
	private Date ngaydang;
	private String noidung;
	private String linhvuc;
	private int binhluan;
	private int luotxem;
	private int luotthich;
	private int trangthai;
	private String video;
	private String anh;
	
	
	public List<String> getNoidungs(){
		StringTokenizer tk = new StringTokenizer(this.noidung,"@");
		List<String> list = new ArrayList<>();
		while(tk.hasMoreTokens()) {
			String s = tk.nextToken();
			list.add(s);
		}
		return list;
	}
	
	public String getDate() {
		SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy");
		return fm.format(ngaydang);
	}
	
}
