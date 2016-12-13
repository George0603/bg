package com.bg.bgmedical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bg.utils.StatusBarUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

public class ChildGrowthActivity extends BaseActivity implements
		OnItemClickListener {

	private ViewFlipper mViewFlipper;
	private int[] res = { R.drawable.ppt1, R.drawable.ppt2, R.drawable.ppt3 };

	private GridView mGridView;
	private int[] icon = { R.drawable.gridview0, R.drawable.gridview0,
			R.drawable.gridview0, R.drawable.gridview0 };
	private String[] icontext = { "儿童资料", "测量分析", "曲线图", "设置" };

	private List<BaseActivity> pages = new ArrayList<BaseActivity>();

	private SimpleAdapter simple;
	private TextView title_bar;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarUtils.setWindowStatusBarColor(this, R.color.titlebar);
		setContentView(R.layout.main_activity);

		title_bar = (TextView) this.findViewById(R.id.title_bar);
		title_bar.setText("儿童生长发育系统");
		mViewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
		mGridView = (GridView) this.findViewById(R.id.gridView);

		for (int i = 0; i < res.length; i++) {
			mViewFlipper.addView(getImage(res[i]));
		}

		simple = new SimpleAdapter(this, getdata(), R.layout.gridview_item,
				new String[] { "imageView", "textView" }, new int[] {
						R.id.imageView, R.id.textView });
		mGridView.setAdapter(simple);
		mGridView.setOnItemClickListener(this);

		mViewFlipper.setInAnimation(this, R.anim.push_right_in);
		mViewFlipper.setOutAnimation(this, R.anim.push_left_out);
		mViewFlipper.setFlipInterval(3000);
		mViewFlipper.startFlipping();

		getPages();

	}

	private ImageView getImage(int resid) {
		ImageView image = new ImageView(this);
		// image.setImageResource(resid);
		image.setBackgroundResource(resid);
		return image;
	}

	private List<Map<String, Object>> getdata() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (int i = 0; i < icon.length; i++) {
			map = new HashMap<String, Object>();
			map.put("imageView", icon[i]);
			map.put("textView", icontext[i]);
			list.add(map);
		}
		return list;
	}

	private List<BaseActivity> getPages() {
		ChildInformation child = new ChildInformation();
		pages.add(child);
		DataAnalysis dtanaly = new DataAnalysis();
		pages.add(dtanaly);
		DiagramActivity diagram = new DiagramActivity();
		pages.add(diagram);
		Settingactivity setting = new Settingactivity();
		pages.add(setting);
		return pages;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(this, pages.get(position).getClass());
		startActivity(intent);
	}
}
