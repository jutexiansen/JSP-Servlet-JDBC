package com.aaa.dao;

import java.util.List;

import com.aaa.entity.User;

public interface IUserDao {
	boolean addUser(User user);
	boolean deleteUser(int id);
	boolean updateUser(User user);
	User getUserById(int id);
	List<User> getAll();
	boolean getUser(String username,String password);
}
