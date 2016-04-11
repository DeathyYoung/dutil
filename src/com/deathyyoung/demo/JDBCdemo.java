package com.deathyyoung.demo;

import java.util.LinkedList;
import java.util.Map;

import com.deathyyoung.jdbc.factory.JDBCFactory;
import com.deathyyoung.jdbc.util.JDBCUtil;

class JDBCDemoThread extends Thread {
	int id = 0;

	static int countConn = 0;
	static int count = 0;
	static int max = 0;
	static boolean stop = false;

	public JDBCDemoThread(int id) {
		this.id = id;
	}

	public void run() {
		countConn++;
		JDBCUtil ju = new JDBCUtil("test");
		String sql = "SELECT COUNT(1) AS c, p.COMMAND AS com FROM information_schema.PROCESSLIST p WHERE db = ? GROUP BY p.COMMAND";
		LinkedList<Map<String, Object>> rst = null;
		for (int c = 0; c < 100; c++) {
			// System.out.println(count++);

			if (stop) {
				return;
			}
			if (countConn % 10 == 0 || count % 10 == 0) {
				System.out.println("conn: " + countConn + " stat: " + count);
			}
			if (countConn % 10 == 0) {
				System.out.println("CONN: " + countConn);
			}
			count++;
			rst = ju.executeQuery(sql, "test");
			count--;
			int all = 0;
			for (int i = 0; i < rst.size(); i++) {
				Map<String, Object> info = rst.get(i);
				System.out.println(id + "==>Command: " + info.get("com") + " ["
						+ info.get("c") + "] ");
				all += Integer.parseInt(info.get("c").toString());
			}
			if (all >= max) {
				max = all;
			} else {
				System.out.println("BIG BANG!");
				System.out.println("BIG BANG!");
				System.out.println("BIG BANG!");
				System.out.println("BIG BANG!");
				System.out.println("BIG BANG!");
				System.out.println("BIG BANG!");
				System.out.println("BIG BANG!");
				System.out.println("BIG BANG!");
				System.out.println("BIG BANG!");
				stop = true;
			}
		}
		countConn--;
	}
}

class JDBCDemoThread2 extends Thread {
	static int count = 0;
	static JDBCUtil ju = new JDBCUtil("test");

	int id = 0;

	public JDBCDemoThread2(int id) {
		this.id = id;
	}

	public void run() {
		// try {
		// Thread.sleep(id*10);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		String sql = "SELECT COUNT(1) AS c, p.COMMAND AS com FROM information_schema.PROCESSLIST p WHERE db = ? GROUP BY p.COMMAND";
		LinkedList<Map<String, Object>> rst = null;
		for (int c = 0; c < 1; c++) {
			// System.out.println(count++);
			rst = ju.executeQuery(sql, "test");
			for (int i = 0; i < rst.size(); i++) {
				Map<String, Object> info = rst.get(i);
				System.out.println(id + "==>Command: " + info.get("com") + " ["
						+ info.get("c") + "] ");
			}
			// count--;
		}
	}
}

public class JDBCdemo {

	public static void main(String[] args) {
		String alias = "cadal_metadata_full_dbo2";
		JDBCUtil ju = JDBCFactory.getJDBCUtil(alias);
		String sql = "select BookNo, Title from cbook where bookno = ?";
		LinkedList<Map<String, Object>> rst = ju.executeQuery(sql, 3);
		System.out.println(rst.get(0).get("BookNo"));
		System.out.println(rst.get(0).get("Title"));
	}
}