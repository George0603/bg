package com.bg.bgmedical;

import java.util.ArrayList;
import java.util.List;

import com.bg.sqlite.Controldata;
import com.bg.utils.StatusBarUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ChildInformation extends BaseActivity implements
		OnItemClickListener, OnClickListener {

	private Button back;
	private Button add;
	private ListView listview;
	private Controldata controldata;
	private TextView nodata;

	// private String[] strs = { "大宝 (默认)", "二宝", "小宝" };
	private ArrayAdapter<String> adapter;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarUtils.setWindowStatusBarColor(this, R.color.titlebar);
		setContentView(R.layout.childdelete_activity);

		controldata = new Controldata(this);

		back = (Button) this.findViewById(R.id.back);
		add = (Button) this.findViewById(R.id.add);
		nodata = (TextView) this.findViewById(R.id.nodata);
		listview = (ListView) this.findViewById(R.id.listView);

		back.setOnClickListener(this);
		add.setOnClickListener(this);
		listview.setOnItemClickListener(this);
	}

	private List<String> getData() {
		List<String> baby = new ArrayList<String>();
		baby = controldata.querybabyname();
		return baby;
	}

	@Override
	protected void onResume() {
		super.onResume();

		List<String> list = getData();
		adapter = new ArrayAdapter<String>(this,
				R.layout.childinformation_item, R.id.name, list);
		listview.setAdapter(adapter);
		
		if (list.size() == 0) {
			nodata.setVisibility(View.VISIBLE);
		} else {
			nodata.setVisibility(View.GONE);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent inte = new Intent(this, ChildInfordetail.class);
		TextView babyname = (TextView) view.findViewById(R.id.name);
		inte.putExtra("babyname", babyname.getText().toString());
		startActivity(inte);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.add:
			Intent intent = new Intent(ChildInformation.this,
					ChildInfordetail.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

}
