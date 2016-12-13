package com.bg.sqlite;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bg.model.Baby;
import com.bg.model.BabyData;

import android.content.Context;
import android.provider.ContactsContract.Data;

public class Controldata {

	private Context context;

	public Controldata(Context context) {
		this.context = context;
		SQLiteopenhelper data = new SQLiteopenhelper(context);
		// 創建數據庫，必須要先進行getReadableDatabase() 或迿 getWritableDatabase()
		data.getReadableDatabase();
	}

	public boolean insert_baby(Baby baby) {
		String sql = "insert into babys (babyname,sex,parity,birthday,height,weight,mark) values (?,?,?,?,?,?,?)";
		Object[] args = { baby.getBabyname(), baby.getSex(), baby.getParity(),
				baby.getBirthday(), baby.getHeight(), baby.getWeight(),
				baby.getMarks() };
		DataManger manger = new DataManger(context);
		boolean flag = manger.update(sql, args);
		return flag;
	}

	public boolean insert_data(BabyData babydata) {
		String sql = "insert into datas (babyname,date,height,weight,bmi,height_weight,head,seat,bust) values (?,?,?,?,?,?,?,?,?)";
		Object[] args = { babydata.getBabyname(), babydata.getDate(),
				babydata.getHeight(), babydata.getWeight(), babydata.getBmi(),
				babydata.getHeight_weight(), babydata.getHead(),
				babydata.getSeat(), babydata.getBust() };
		DataManger manger = new DataManger(context);
		boolean flag = manger.update(sql, args);
		return flag;
	}

	public boolean update_baby(Baby baby, String babyname) {
		String sql = "update babys set babyname = ?,sex = ?,parity = ? , "
				+ "birthday = ?,height = ? ,weight = ? where babyname = ?";
		Object[] args = { baby.getBabyname(), baby.getSex(), baby.getParity(),
				baby.getBirthday(), baby.getHeight(), baby.getWeight(),
				babyname };
		DataManger manger = new DataManger(context);
		boolean flag = manger.update(sql, args);
		return flag;
	}

	public boolean delete_baby(String babyname) {
		String sql = "delete from babys where babyname = ?";
		Object[] args = { babyname };
		DataManger manger = new DataManger(context);
		boolean flag = manger.update(sql, args);
		return flag;
	}

	public void deleteAll() {
		String sql = "delete from babys";
		DataManger manger = new DataManger(context);
		manger.update(sql, null);
	}

	// public Map<String, String> selectone(String babyname) {
	// String sql = "select * from babys where babyname = ? ";
	// DataManger manger = new DataManger(context);
	// Map<String, String> map = manger.queryone(sql,
	// new String[] { babyname });
	// return map;
	// }

	// public void selecmore(String str) {
	// String sql = "select * from babys where name like ? ";
	// DataManger manger = new DataManger(context);
	// List<Map<String, String>> list = manger.querymore(sql,
	// new String[] { str + "%" });
	// for (int i = 0; i < list.size(); i++) {
	// Map<String, String> map = list.get(i);
	// }
	// }

	// public List<Map<String, String>> selectAll() {
	// String sql = "select * from babys";
	// DataManger manger = new DataManger(context);
	// List<Map<String, String>> myList = new ArrayList<Map<String, String>>();
	// Map<String, String> myMap = null;
	// List<Map<String, String>> list = manger.querymore(sql, null);
	// for (int i = 0; i < list.size(); i++) {
	// myMap = new HashMap<String, String>();
	// Map<String, String> map = list.get(i);
	// myMap.put("babyname", map.get("babyname"));
	// myMap.put("sex", map.get("sex"));
	// myMap.put("parity", map.get("parity"));
	// myMap.put("birthday", map.get("birthday"));
	// myMap.put("length", map.get("length"));
	// myMap.put("weight", map.get("weight"));
	// myMap.put("mark", map.get("mark"));
	// myList.add(myMap);
	// }
	// return myList;
	// }

	public List<String> querybabyname() {
		String sql = "select babyname from babys order by rowid asc";
		DataManger manger = new DataManger(context);
		List<String> list = new ArrayList<String>();
		List<Map<String, String>> querylist = manger.querymore(sql, null);
		if (querylist.size() != 0) {
			for (int i = 0; i < querylist.size(); i++) {
				list.add(querylist.get(i).get("babyname"));
			}
		}

		return list;
	}

	public List<BabyData> query_data(String babyname) {
		String sql = "select * from datas where babyname = ? order by date asc";
		DataManger manger = new DataManger(context);
		String[] args = { babyname };
		List<BabyData> querylist = manger.query_data(sql, args);
		return querylist;
	}

	public List<Baby> query_baby(String babyname) {
		String sql;
		if (babyname == null) {
			sql = "select * from babys order by rowid asc";
		} else {
			sql = "select * from babys where babyname = ? order by rowid asc";
		}

		DataManger manger = new DataManger(context);
		String[] args = { babyname };
		List<Baby> querylist = manger.query_baby(sql, args);
		return querylist;
	}

}
