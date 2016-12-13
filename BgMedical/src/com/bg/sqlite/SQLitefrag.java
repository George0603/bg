package com.bg.sqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bg.bgmedical.R;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SQLitefrag extends Fragment implements View.OnClickListener {

	private EditText username;
	private EditText password;
	private Button save;
	private Button delet;
	private ListView listview;
	private Controldata controldata;
	private List<String> list;
	private  List<Map<String, String>> myList;
	private ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.sqlitefrag, null);
//		controldata = new Controldata(getActivity());
//		list = new ArrayList<String>();
//		username = (EditText) view.findViewById(R.id.username);
//		password = (EditText) view.findViewById(R.id.password);
//		save = (Button) view.findViewById(R.id.save);
//		delet = (Button) view.findViewById(R.id.delet);
//		myList = controldata.selecAll();
//		for (int i = 0; i < myList.size(); i++) {
//			String str = "用戶名:" + myList.get(i).get("username") + " - 密碼:"
//					+ myList.get(i).get("password");
//			list.add(str);
//		}
//		listview = (ListView) view.findViewById(R.id.listView);
//		adapter = new ArrayAdapter<String>(getActivity(),
//				android.R.layout.simple_list_item_1, list);
//		listview.setAdapter(adapter);
//
//		save.setOnClickListener(this);
//		delet.setOnClickListener(this);
//		listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				final int pos = position;
//				final int pid = Integer.parseInt(myList.get(pos).get("pid"));
//				final AlertDialog myDialog = new AlertDialog.Builder(
//						getActivity()).create();
//				View dialogview = LayoutInflater.from(getActivity()).inflate(R.layout.sqlitedialog, null);
//				myDialog.setView(dialogview);
//				myDialog.show();
//				final EditText dialogusername = (EditText) myDialog.getWindow()
//						.findViewById(R.id.username);
//				final EditText dialogpassword = (EditText) myDialog.getWindow()
//						.findViewById(R.id.password);
//				dialogusername.setText(myList.get(pos).get("username"));
//				dialogpassword.setText(myList.get(pos).get("password"));
//				myDialog.getWindow().findViewById(R.id.update)
//						.setOnClickListener(new OnClickListener() {
//
//							@Override
//							public void onClick(View v) {
//								String usernamestr = dialogusername.getText()
//										.toString();
//								String passwordstr = dialogpassword.getText()
//										.toString();
//								if ("".equals(usernamestr)
//										|| "".equals(passwordstr)) {
//									Toast.makeText(getActivity(),
//											"用戶名或密碼不能為空或空格！", 0).show();
//								} else {
//									
//									controldata.update(usernamestr,
//											passwordstr, pid);
//									String str = "用戶吿:" + usernamestr + " - 密碼:"
//											+ passwordstr;
//									list.set(pos, str);
//									myList = controldata.selecAll();
//									myDialog.dismiss();
//								}
//								
//								adapter.notifyDataSetChanged();
//							}
//						});
//				myDialog.getWindow().findViewById(R.id.delet)
//						.setOnClickListener(new OnClickListener() {
//
//							@Override
//							public void onClick(View v) {
//								controldata.delete(pid);
//								list.remove(pos);
//								adapter.notifyDataSetChanged();
//								myDialog.dismiss();
//							}
//						});
//				myDialog.getWindow().findViewById(R.id.cancel)
//						.setOnClickListener(new OnClickListener() {
//
//							@Override
//							public void onClick(View v) {
//								myDialog.dismiss();
//
//							}
//						});
//
//			}
//		});
//
//		return view;
//	}
//
//	@Override
//	public void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//	}
//
//	@Override
//	public void onClick(View v) {
//
//		switch (v.getId()) {
//		case R.id.save:
//			String usernamestr = username.getText().toString();
//			String passwordstr = password.getText().toString();
//			if ("".equals(usernamestr) || "".equals(passwordstr)) {
//				Toast.makeText(getActivity(), "用戶名或密碼不能為空或空格！", 0).show();
//			} else {
//				controldata.insert(usernamestr, passwordstr);
//				String str = "用戶吿:" + usernamestr + " - 密碼:"
//						+ passwordstr;
//				list.add(str);
//				username.setText("");
//				password.setText("");
//				username.requestFocus();
//				myList = controldata.selecAll();
//			}
//
//			break;
//		case R.id.delet:
//			controldata.deleteAll();
//			list.clear();
//			break;
//		default:
//			break;
//		}
//
//		adapter.notifyDataSetChanged();
//	}

}
