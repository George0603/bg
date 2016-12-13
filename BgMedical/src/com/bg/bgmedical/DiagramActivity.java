package com.bg.bgmedical;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.achartengine.GraphicalView;

import com.bg.model.Baby;
import com.bg.model.BabyData;
import com.bg.sqlite.Controldata;
import com.bg.utils.Constant;
import com.bg.utils.GetStandard;
import com.bg.utils.StatusBarUtils;
import com.bg.view.MyChart;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class DiagramActivity extends BaseActivity implements
		OnCheckedChangeListener {

	private Button who;
	private Button city;
	private Button back;
	private Controldata controldata;
	private int INSERT_BABY = 0;
	private TextView babyname;
	private int sex; // 0 为男生 1 为女生
	private Date birthday;
	private List<BabyData> data;
	private Baby mybaby;
	private LinearLayout content;
	private RadioGroup radioGroup;
	private RadioButton height;
	private boolean flag; // 是否为9城市标准
	private String[] titles = new String[] { "SD2neg", "SD1neg", "SD1", "SD2" }; // 标题
	private MyChart myChart = new MyChart();

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			// Calendar c = Calendar.getInstance();
			// int[] month = new i[data.size()];
			// for (int i = 0; i < month.length; i++) {
			// month[i] = (data.get(i).getDate().getTime() - birthday
			// .getTime()) / (1000 * 60 * 60 * 24 * 30);
			// }
			switch (msg.what) {
			case 0:

				break;
			case 1:

				break;
			case 2:
				String str = (String) msg.obj;
				mybaby = controldata.query_baby(str).get(0);
				sex = mybaby.getSex();
				birthday = mybaby.getBirthday();
				data = controldata.query_data(mybaby.getBabyname());
				height.setChecked(true);
				break;
			default:
				break;
			}
		};
	};

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarUtils.setWindowStatusBarColor(this, R.color.titlebar);
		setContentView(R.layout.diagram_activity);

		controldata = new Controldata(this);
		babyname = (TextView) this.findViewById(R.id.babyname);
		searchBaby();

		back = (Button) this.findViewById(R.id.back);
		content = (LinearLayout) this.findViewById(R.id.content);
		radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);
		height = (RadioButton) this.findViewById(R.id.height);

		SharedPreferences share = getSharedPreferences(getResources()
				.getString(R.string.settings), MODE_PRIVATE);
		if (share.getString("standard", "").equals("nine_city")) {
			flag = true;
		}

		radioGroup.setOnCheckedChangeListener(this);

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}

	private void searchBaby() {
		List<String> baby = controldata.querybabyname();
		if (baby.size() == 0) {
			Toast.makeText(this, "当前无儿童信息，请添加！", 0).show();
			finish();
		} else if (baby.size() == 1) {
			babyname.setText(baby.get(0));
			Message message = Message.obtain();
			message.obj = baby.get(0);
			message.what = 2;
			handler.sendMessage(message);
		} else {
			final String[] selbaby = new String[baby.size()];
			for (int i = 0; i < selbaby.length; i++) {
				selbaby[i] = baby.get(i);
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("请选择儿童:");
			builder.setItems(selbaby, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					babyname.setText(selbaby[which]);
					Message message = Message.obtain();
					message.obj = selbaby[which];
					message.what = 2;
					handler.sendMessage(message);
				}
			});

			builder.setCancelable(false);
			builder.create().show();
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		String[] names = new String[3];
		List<double[]> values = new ArrayList<double[]>();
		double[] x_y_max = new double[2];
		String stand = (flag == true ? "9城市标准_" : "who标准_")
				+ (sex == 0 ? "男生" : "女生");
		Boolean month = false; // 是否为72个月

		content.removeAllViews();
		names[1] = "月份";
		switch (checkedId) {
		case R.id.height:
			names[0] = stand + "身高";
			names[2] = "身高/cm";
			values = GetStandard.get_standard(flag, sex, "height");
			break;

		case R.id.weight:
			names[0] = stand + "体重";
			names[2] = "体重/cm";
			values = GetStandard.get_standard(flag, sex, "weight");
			break;

		case R.id.head:
			if (!flag) {
				month = true;
			}
			names[0] = stand + "头围";
			names[2] = "头围/cm";
			values = GetStandard.get_standard(flag, sex, "head");
			break;

		case R.id.bmi:
			names[0] = stand + "BMI";
			names[2] = "BMI/cm";
			values = GetStandard.get_standard(flag, sex, "bmi");

			break;
		default:
			break;
		}
		x_y_max = values.get(values.size() - 1);
		values.remove(values.size() - 1);

		GraphicalView grap;
		if (flag) {
			grap = myChart.executeView_9city(this, titles, names, values,
					x_y_max);
		} else {
			grap = myChart.executeView_who(this, titles, names, values,
					x_y_max, month);
		}
		content.addView(grap);
	}
}
