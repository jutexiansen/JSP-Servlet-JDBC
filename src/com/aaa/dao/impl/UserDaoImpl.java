package com.aaa.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.aaa.dao.IUserDao;
import com.aaa.db.DBUtil;
import com.aaa.entity.User;

public class UserDaoImpl implements IUserDao {

	public boolean addUser(User user) {
		String sql = "insert into user values (null,?,?,?,?)";
		boolean bool = false;
		;

		bool = DBUtil.executeUpdate(sql, user.getUsername(),
				user.getPassword(), user.getNickname(), user.getEmail());

		return bool;
	}

	public boolean deleteUser(int id) {
		String sql = "delete from user where id =?";
		boolean bool = false;

		bool = DBUtil.executeUpdate(sql, id);

		return bool;
	}

	public boolean updateUser(User user) {
		String sql = "update user set username = ?,password=?,nickname=?,email=?where id =?";
		boolean bool = false;

		bool = DBUtil.executeUpdate(sql, user.getUsername(),
				user.getPassword(), user.getNickname(), user.getEmail(),
				user.getId());

		return bool;
	}

	public User getUserById(int id) {
		String sql = "select * from user where id =?";
		List<User> list = DBUtil.selectList(sql, User.class, id);
		return list.get(0);

	}

	public List<User> getAll() {
		String sql = "select * from user";
		List<User> list = DBUtil.selectList(sql, User.class);
		return list;
	}

	public boolean getUser(String username, String password) {
		String sql = "select * from user where username =? and password=?";
		List<User> list = DBUtil.selectList(sql, User.class,username,password);
		boolean bool =false;
		if(list.size()>0){
			bool = true;
		}
		return bool;
	}

	
	public  User  login(String username,String password) {
		   User u=null;
		   Connection connection =null;
		   PreparedStatement pstmt=null;
		   ResultSet resultSet=null;
	 
		  //赋值
		  try {
			connection=DBUtil.getConn();
			  //静态sql语句
			String sql = "select * from user where username=? and password=?"; 
		    pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);  
			resultSet = pstmt.executeQuery();
			if(resultSet.next()){
				u=new User();
				u.setUsername(resultSet.getString("username"));
				u.setPassword(resultSet.getString("password"));
				System.out.println("登录成功！");
			}else{
				System.out.println("用户名或者密码错误！");
			}  
		} catch (Exception e) {
			e.printStackTrace();
		}finally {	
			DBUtil.close(pstmt, connection);	
		}
		 return u;
	 
	}
}
