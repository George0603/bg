package com.bg.sqlite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.bg.bgmedical.ChildInfordetail;
import com.bg.model.Baby;
import com.bg.model.BabyData;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DataManger {

	private SQLiteopenhelper helper;
	private SQLiteDatabase data;

	public DataManger(Context context) {
		helper = new SQLiteopenhelper(context);
		// 創建數據庫，必須要先進行getReadableDatabase() 或者  getWritableDatabase()
		data = helper.getWritableDatabase();
	}

	public boolean update(String sql, Object[] args) {
		boolean flag = false;
		try {
			if (args == null) {
				data.execSQL(sql);
			}else {
				data.execSQL(sql, args);
			}
			
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 精確查詢
	 * 
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public Map<String, String> queryone(String sql, String[] selectionArgs) {
		Map<String, String> map = new HashMap<String, String>();
		Cursor cursor = data.rawQuery(sql, selectionArgs);
		int cur_len = cursor.getColumnCount();
		while (cursor.moveToNext()) {
			for (int i = 0; i < cur_len; i++) {
				String name = cursor.getColumnName(i);
				String values = cursor.getString(cursor.getColumnIndex(name));
				if (values == null) {
					values = "";
				}
				map.put(name, values);
			}
		}
		cursor.close();
		cursor = null;
		return map;
	}

	/**
	 * 模糊查詢
	 * 
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public List<Map<String, String>> querymore(String sql,
			String[] selectionArgs) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Cursor cursor = data.rawQuery(sql, selectionArgs);
		int cur_len = cursor.getColumnCount();
		while (cursor.moveToNext()) {
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < cur_len; i++) {
				String name = cursor.getColumnName(i);
				String values = cursor.getString(cursor.getColumnIndex(name));
				if (values == null) {
					values = "";
				}
				map.put(name, values);
			}
			list.add(map);
		}
		cursor.close();
		cursor = null;
		return list;
	}

	/**
	 * 遊標查詢
	 * @param sql
	 * @param selectionArgs
	 * @return
	 */
	public Cursor querycursor(String sql, String[] selectionArgs) {
		Cursor cursor = data.rawQuery(sql, selectionArgs);
		return cursor;
	}
	
	public List<BabyData> query_data(String sql,
			String[] selectionArgs) {
		List<BabyData> list = new ArrayList<BabyData>();
		Cursor cursor = data.rawQuery(sql, selectionArgs);
		 
		while (cursor.moveToNext()) {
			BabyData babydata = new BabyData();
			babydata.setBabyname(cursor.getString(cursor.getColumnIndex("babyname")));
			
			String str = cursor.getString(cursor.getColumnIndex("date"));   
			SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date da = format.parse(str);
				String st = sdf.format(da);
				babydata.setDate(sdf.parse(st));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			babydata.setHeight(cursor.getFloat(cursor.getColumnIndex("height")));
			babydata.setWeight(cursor.getFloat(cursor.getColumnIndex("weight")));
			babydata.setBmi(cursor.getFloat(cursor.getColumnIndex("bmi")));
			babydata.setHeight_weight(cursor.getFloat(cursor.getColumnIndex("height_weight")));
			babydata.setHead(cursor.getFloat(cursor.getColumnIndex("head")));
			babydata.setSeat(cursor.getFloat(cursor.getColumnIndex("seat")));
			babydata.setBust(cursor.getFloat(cursor.getColumnIndex("bust")));
			
			list.add(babydata);
		}
		cursor.close();
		cursor = null;
		return list;
	}
	public List<Baby> query_baby(String sql,
			String[] selectionArgs) {
		List<Baby> list = new ArrayList<Baby>();
		Cursor cursor = data.rawQuery(sql, selectionArgs);
		 
		while (cursor.moveToNext()) {
			Baby baby = new Baby();
			baby.setBabyname(cursor.getString(cursor.getColumnIndex("babyname")));
			baby.setSex(cursor.getInt(cursor.getColumnIndex("sex")));
			baby.setParity(cursor.getInt(cursor.getColumnIndex("parity")));
			
			String str = cursor.getString(cursor.getColumnIndex("birthday")); 
			SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date da = format.parse(str);
				String st = sdf.format(da);
				baby.setBirthday(sdf.parse(st));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			baby.setHeight(cursor.getFloat(cursor.getColumnIndex("height")));
			baby.setWeight(cursor.getFloat(cursor.getColumnIndex("weight")));
			baby.setMarks(cursor.getString(cursor.getColumnIndex("mark")));
			
			list.add(baby);
		}
		cursor.close();
		cursor = null;
		return list;
	}
}
