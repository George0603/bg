package com.bg.sqlite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import android.test.AndroidTestCase;

public class Test extends AndroidTestCase {

	public Test() {

	}

	public void createdata() {
		SQLiteopenhelper data = new SQLiteopenhelper(getContext());
		data.getReadableDatabase();
	}

	public void insert() {
		String sql = "insert into babys (babyname,sex,parity,birthday,length,weight,mark) values (?,?,?,?,?,?,?)";
		Object[] args = { "z", "男", "二胎", "1990-10-09", 45, 5, 0 };
		DataManger manger = new DataManger(getContext());
		manger.update(sql, args);
	}

	public void update() {
		String sql = "update login set username = ? ,password = ? where pid = ?";
		Object[] args = { "皮皮", "234", "1" };
		DataManger manger = new DataManger(getContext());
		manger.update(sql, args);
	}

	public void delete() {
		String sql = "delete from login where pid = ?";
		Object[] args = { "2" };
		DataManger manger = new DataManger(getContext());
		manger.update(sql, args);
	}

	public void selectone() {
		String sql = "select * from login where pid = ? ";
		DataManger manger = new DataManger(getContext());
		Map<String, String> map = manger.queryone(sql, new String[] { "1" });
		System.out.println("---------" + map.get("username"));
		System.out.println("---------" + map.get("password"));
	}

	public void selecmore() {
		String sql = "select * from login where name like ? ";
		DataManger manger = new DataManger(getContext());
		List<Map<String, String>> list = manger.querymore(sql,
				new String[] { "李%" });
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			System.out.println("--------------" + map.get("username"));
			System.out.println("--------------" + map.get("password"));
		}
	}

	public void selecAll() {
		String sql = "select * from babys";
		DataManger manger = new DataManger(getContext());
		List<Map<String, String>> list = manger.querymore(sql, null);
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			System.out.println("--------------" + map.get("username"));
			System.out.println("--------------" + map.get("password"));
		}
	}

}
