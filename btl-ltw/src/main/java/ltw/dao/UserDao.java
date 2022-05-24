package ltw.dao;

import java.util.List;

import ltw.model.User;

public interface UserDao {
	User findUser(int id);
	User findUser(String username);
	Boolean exitsUser(String username);
	void addUser(User user);
	void deleteUser(int id);
	void updateUser(User user);
	List<User> getUsers(String text);
	Boolean checkUser(String username,String password);
}
