package com.aaa.test;

import java.util.List;

import org.junit.Test;

import com.aaa.dao.impl.UserDaoImpl;
import com.aaa.entity.User;

public class JavaTest {
	@Test
	public void test1() throws Exception {
		List<User> list = new UserDaoImpl().getAll();
		System.out.println(list);
	}
	@Test
	public void test2() throws Exception {
		String username = "zhangsan";
		String password = "123456";
		boolean bool = new UserDaoImpl().getUser(username,password);
		System.out.println(bool);
	}
	@Test
	public void test3() throws Exception {
		String username = "lisi";
		String password = "123456";
		
		User user=new UserDaoImpl().login(username, password);
		System.out.println(user);
	}
}	
