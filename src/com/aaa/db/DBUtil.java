package com.aaa.db;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DBUtil {
	private static DataSource  source= null;
	static{
		try {
			Properties properties = new Properties();
			properties.load(DBUtil.class.getClassLoader().getResourceAsStream("druid.properties"));
			source = DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConn(){
		Connection conn = null;
		try {
			conn = source.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static boolean executeUpdate(String sql , Object...obj) {
		Connection conn = null;
		PreparedStatement prt = null;
		boolean bool = false;
		conn = getConn();
	
		try {
			prt = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				prt.setObject(i+1, obj[i]);
			}
			int rs = prt.executeUpdate();
			if(rs>0){
				bool = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		return bool;
		
	}
	
	public static List<Map<String,Object>> executeQuery(String sql , Object...obj){
		Connection conn = null;
		PreparedStatement prt = null;
		ResultSet ret= null;
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		
		conn = getConn();
		try {
			prt = conn.prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				prt.setObject(i+1, obj[i]);
			}
			ret = prt.executeQuery();
			int count = ret.getMetaData().getColumnCount();
			while(ret.next()){
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= count; i++) {
					String key = ret.getMetaData().getColumnName(i);
					Object value = ret.getObject(i);
					map.put(key, value);
				}
				list.add(map);
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}

	public static <T> List<T> selectList(String sql, Class<T> c, Object... args) {
		Connection conn = null;
		PreparedStatement prt = null;
		ResultSet ret = null;
		List<T> arr = new ArrayList<T>();
		try {
			conn = getConn();
			prt = conn.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				prt.setObject(i + 1, args[i]);
			}
			ret = prt.executeQuery();
			while (ret.next()) {

				T obj = c.newInstance();
				BeanInfo info = Introspector.getBeanInfo(obj.getClass());
				PropertyDescriptor[] pro = info.getPropertyDescriptors();
				for (PropertyDescriptor p : pro) {
					String name = p.getName();
					if (!"class".equals(name)&&name != null) {
						p.getWriteMethod().invoke(obj, ret.getObject(name));
					}
					

				}arr.add(obj);
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return arr;
		

	}

	public static void close(PreparedStatement prt, Connection conn) {
		// TODO Auto-generated method stub
		try {
			if (prt!=null) {
				prt.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}	finally{
			try {
				if (conn!=null) {
					prt.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
