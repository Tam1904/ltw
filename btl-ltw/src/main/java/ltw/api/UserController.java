package ltw.api;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ltw.dao.UserDao;
import ltw.model.User;

@Controller
@RequestMapping("/managerUser")
public class UserController {
	
	@Autowired
	private UserDao uDao;

	@GetMapping
	public String index(Model model) {
		List<User> users = uDao.getUsers("");
		model.addAttribute("users", users);
		return "quanlyUser";
	}
	
	@PostMapping("/updateUser")
	public ResponseEntity<String> update(@ModelAttribute("id")int id, @ModelAttribute("ten")String ten,@ModelAttribute("username")String username, 
			@ModelAttribute("password") String password, @ModelAttribute("email") String email,
			@ModelAttribute("gioithieu") String gioithieu, @ModelAttribute("phanquyen") String phanquyen,@ModelAttribute("action")String type,Model model) {
		User user = new User();
		user.setId(id);
		user.setTen(ten);
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setGioithieu(gioithieu);
		user.setPhanquyen(phanquyen);
		if(type.equals("Add")) {
			if(!uDao.exitsUser(user.getUsername())) {
				uDao.addUser(user);
			}
		}
		else {
			uDao.updateUser(user);
		}
		return new ResponseEntity<>("",HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteUser")
	public ResponseEntity<String> delete(@ModelAttribute("listma")String listma) {
		StringTokenizer tk = new StringTokenizer(listma);
		while(tk.hasMoreTokens()) {
			String id = tk.nextToken();
			uDao.deleteUser(Integer.parseInt(id));
		}
		return new ResponseEntity<>("OK",HttpStatus.OK);
	}
	
	@GetMapping("/checkUser")
	public ResponseEntity<String> find(@ModelAttribute("username")String username){
		String s = "";
		if(uDao.exitsUser(username)) {
			s = "Người dùng đã tồn tại";
		}
		return new ResponseEntity<>(s,HttpStatus.OK);
	}
	
	@GetMapping("/autosearchUser")
	public ResponseEntity<String> findList(@ModelAttribute("text")String text,@ModelAttribute("typed")String typed){
		String s = "";
		List<User> users = uDao.getUsers(text);
		for(User user : users) {
			s+="<tr class=' cart-table-tr ' style=' min-height: 70px; '>"
					+ "											<td class=' cart-table-td '>";
			if(typed.equals("Hoàn tất")) {
				s+="<div class=' cart-icon '>"
						+ "<i id='icon' class='fas fa-highlighter edit' onclick='chooseUser(this)' style='display:block'></i>"
						+ "<input type='checkbox' class='checkbox' value= "+ user.getId()+ ">"
						+ "</div>";
			}
			else {
				s+="<div class=' cart-icon '>"
						+ "<i id='icon' class='fas fa-highlighter edit' onclick='chooseUser(this)' style='display:none'></i>"
						+ "<input type='checkbox' class='checkbox' value= "+ user.getId()+ " style='display:block'>"
						+ "</div>";
			}
					s+= "<td>"
							+ "											<div class=\"cart-product\">"
							+ "												<div class=\"cart-product-link\">"
							+ "													<span class=\"cart-product-link-name\""
							+ "														style=\"color:var(--black-color)\">Họ"
							+ "														và tên:</span>"
							+ "													<span id=\"ten\" class=\"cart-product-name\""
							+ "														style=\"color:var(--black-color)\" >" + user.getTen() + "</span>"
							+ "													<span id=\"gioithieu\" class=\" cart-product-name\""
							+ "														style=\"color:var(--black-color);display: none;\"> " + user.getGioithieu() + "</span>"
							+ "													<span id=\"id\" class=\" cart-product-name\""
							+ "														style=\"color:var(--black-color);display: none;\"> " + user.getId() + "</span>"
							+ "													<span id=\"anh\" class=\" cart-product-name\""
							+ "														style=\"color:var(--black-color);display: none;\"> " + user.getAnh() + "</span>"
							+ "												</div>"
							+ "											</div>"
							+ "										</td>"
							+ ""
							+ "										<td class=\"cart-table-td\">"
							+ "											<div class=\"cart-product\">"
							+ "												<div class=\"cart-product-link\">"
							+ "													<span class=\"cart-product-link-name\""
							+ "														style=\"color:var(--black-color)\">Tên tài khoản:</span>"
							+ "													<span id=\"username\" class=\"cart-product-name\""
							+ "														style=\"color:var(--black-color)\" > " + user.getUsername() + "</span>"
							+ "												</div>"
							+ "											</div>"
							+ "										</td>"
							+ "										<td>"
							+ "											<div class=\"cart-product\">"
							+ "												<div class=\"cart-product-link\">"
							+ "													<span class=\"cart-product-link-name\""
							+ "														style=\"color:var(--black-color)\">Mật khẩu:</span>"
							+ "													<span id=\"password\" class=\"cart-product-name\""
							+ "														style=\"color:var(--black-color)\"> " + user.getPassword() + "</span>"
							+ "												</div>"
							+ "											</div>"
							+ "										</td>"
							+ "										<td>"
							+ "											<div class=\"cart-product-price-nth\">"
							+ "												<span class=\"cart-product-price-nth-link\">Email:</span>"
							+ "												<div id=\"email\" class=\"cart-product-price\" >"  + user.getEmail() + "</div>"
							+ "											</div>"
							+ "										</td>"
							+ "										<td>"
							+ "											<div class=\"cart-product-price-nth\">"
							+ "												<span class=\"cart-product-price-nth-link\">Phân quyền:</span>"
							+ "												<div id=\"phanquyen\" class=\"cart-product-price\">"  + user.getPhanquyen() + "</div>"
							+ "											</div>"
							+ "										</td>"
					+ "										</tr>";
		}
			
		
		return new ResponseEntity<>(s,HttpStatus.OK);
	}
	
}
