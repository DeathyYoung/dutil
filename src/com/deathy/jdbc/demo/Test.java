package com.deathy.jdbc.demo;

import java.util.LinkedList;
import java.util.Map;

import com.deathy.jdbc.util.JDBCUtil;

public class Test {
	public static void main(String[] args) {
		JDBCUtil ju = new JDBCUtil();

		// 遍历
		String sql = "select * from userinfo";
		LinkedList<Map<String, Object>> result = ju.executeQuery(sql);
		for (int i = 0; i < result.size(); i++) {
			String id = result.get(i).get("id").toString();
			String username = result.get(i).get("username").toString();
			System.out.println("id[username] = " + id + "[" + username + "]");
		}
		//插入
		int newId = 3;
		String newUsername = "candy";
		
		sql = "insert userinfo values (?, ?)";
		ju.execute(sql, newId, newUsername);
		
		System.out.println("===============================================");
		
		// 遍历
		sql = "select * from userinfo";
		result = ju.executeQuery(sql);
		for (int i = 0; i < result.size(); i++) {
			String id = result.get(i).get("id").toString();
			String username = result.get(i).get("username").toString();
			System.out.println("id[username] = " + id + "[" + username + "]");
		}
	}
}
