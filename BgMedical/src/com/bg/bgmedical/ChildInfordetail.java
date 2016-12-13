package com.bg.bgmedical;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import com.bg.model.Baby;
import com.bg.sqlite.Controldata;
import com.bg.utils.StatusBarUtils;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class ChildInfordetail extends BaseActivity implements OnClickListener {

	private Button back;

	private Calendar cal;
	private Button update;
	private Button delete;
	private boolean if_insert;
	private boolean if_update = true;
	private String babyname;

	private Controldata controldata;

	private int[] inputs = { R.id.name, R.id.parity, R.id.height, R.id.weight };
	private int[] radioButton = { R.id.boy, R.id.girl };
	private EditText[] input = new EditText[inputs.length];
	private EditText birthday;
	private RadioGroup sex;
	private RadioButton radio;

	private int myyear;
	private int mymonth;
	private int myday;
	private Baby baby;
	private SimpleDateFormat sdf;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarUtils.setWindowStatusBarColor(this, R.color.titlebar);
		setContentView(R.layout.childinfordetail_activity);

		controldata = new Controldata(this);
		baby = new Baby();
		sdf = new SimpleDateFormat("yyyy-MM-dd");
			
		cal = Calendar.getInstance();
		myyear = cal.get(Calendar.YEAR);
		mymonth = cal.get(Calendar.MONTH) + 1;
		myday = cal.get(Calendar.DAY_OF_MONTH);

		birthday = (EditText) this.findViewById(R.id.birthday);
		sex = (RadioGroup) this.findViewById(R.id.sex);
		update = (Button) this.findViewById(R.id.update);
		back = (Button) this.findViewById(R.id.back);
		delete = (Button) this.findViewById(R.id.delete);

		for (int i = 0; i < inputs.length; i++) {
			input[i] = (EditText) this.findViewById(inputs[i]);
		}

		back.setOnClickListener(this);
		update.setOnClickListener(this);
		delete.setOnClickListener(this);

		Intent intent = getIntent();
		babyname = intent.getStringExtra("babyname");
		if (babyname == null) {
			if_insert = true;
			delete.setVisibility(View.GONE);
			update.setText("添加");
			birthday.setText(myyear + "-" + mymonth + "-" + myday);
			birthday.setOnClickListener(this);

		} else {
			if_insert = false;
			for (int i = 0; i < input.length; i++) {
				input[i].setFocusable(false);
			}
			baby = controldata.query_baby(babyname).get(0);
			
			input[0].setText(baby.getBabyname());
			input[1].setText(baby.getParity()+"");
			input[2].setText(baby.getHeight()+"");
			input[3].setText(baby.getWeight()+"");
			sex.check(radioButton[baby.getSex()]);
			birthday.setText(sdf.format(baby.getBirthday()));
			radio = (RadioButton) findViewById(radioButton[Math.abs(baby.getSex() - 1)]);
			radio.setClickable(false);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.update:

			if (if_insert) {
				if (matchname(input[0].getText().toString())) {
					baby = getbaby(input);
					
					boolean insert_succeed = controldata.insert_baby(baby);

					if (insert_succeed) {
						Intent in = new Intent();
						in.putExtra("babyname", baby.getBabyname());
						setResult(RESULT_OK, in);
					}
					controlsucceed(insert_succeed, "添加");
				}
			} else {
				if (if_update) {
					for (int i = input.length - 1; i > -1; i--) {
						input[i].setFocusable(true);
						input[i].setFocusableInTouchMode(true);
						input[i].requestFocus();
						input[i].findFocus();
					}
					radio.setClickable(true);
					birthday.setOnClickListener(this);
					if_update = false;
					update.setText("确定");
				} else {
					if (matchname(input[0].getText().toString())) {
						baby = getbaby(input);
						
						boolean update_succeed = controldata.update_baby(baby, babyname);
						controlsucceed(update_succeed, "修改");
					}
				}
			}

			break;

		case R.id.delete:

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示：");
			builder.setMessage("确定要删除吗？");

			builder.setNegativeButton("取消", null);
			builder.setPositiveButton("确定", new Dialog.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Boolean delete_succeed = controldata.delete_baby(babyname);
					controlsucceed(delete_succeed, "删除");
				}
			});
			builder.create().show();
			break;
		case R.id.birthday:
			new DatePickerDialog(this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					String date1 = year + "-" + (monthOfYear + 1) + "-"+ dayOfMonth;
					String date2 = myyear + "-" + mymonth + "-" + myday;
					Date seldate = null;
					Date curdate = null;
					try {
						seldate = sdf.parse(date1);
						curdate = sdf.parse(date2);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					 
					if (seldate.after(curdate)) {
						Toast.makeText(ChildInfordetail.this, "选择的日期大于当前日期！", 0).show();
					}else {
						birthday.setText(year + "-" + (monthOfYear + 1) + "-"
								+ dayOfMonth);	
					}
					
				}
			}, myyear, cal.get(Calendar.MONTH), myday).show();
			break;

		case R.id.sex:

			break;
		default:
			break;
		}
	}

	private boolean check(EditText name, String text) {

		boolean ch = true;
		String str = name.getText().toString().trim();
		if (str == null || str.isEmpty()) {
			name.setHint(text);
			name.setHintTextColor(Color.RED);
			ch = false;
		}
		return ch;
	}

	private void controlsucceed(boolean flag, String str) {
		if (flag) {
			Toast.makeText(this, str + "成功！", 0).show();
			finish();
		} else {
			Toast.makeText(this, str + "失败！", 0).show();
		}
	}

	private int selectsex() {
		int selectsex = 0; // 默认为男生
		if (sex.getCheckedRadioButtonId() == R.id.girl) {
			selectsex = 1;
		}
		return selectsex;
	}

	private boolean matchname(String str) {
		boolean mat = false;
		if (str.matches("^[\\u4e00-\\u9fa5_a-zA-Z0-9-]{1,15}$")) {  // 昵称格式：限15个字符，支持中英文、数字、减号或下划线
			mat = true;
		} else {
			input[0].setText("");
			input[0].setHint("用户名不正确");
			input[0].setHintTextColor(Color.RED);
		}
		return mat;
	}
	
	private Baby getbaby(EditText[] input){
		Baby mybaby = new Baby();
		
		mybaby.setBabyname(input[0].getText().toString());
		mybaby.setSex(selectsex());
		 
		try {
			mybaby.setBirthday(sdf.parse(birthday.getText().toString()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String par = input[1].getText().toString();
		String hei = input[2].getText().toString();
		String wei = input[3].getText().toString();
		if (par.length() == 0) {
			mybaby.setParity(1);
		}else {
			mybaby.setParity(Integer.parseInt(par));
		}
		if (hei.length() == 0) {
			mybaby.setHeight(0);
		}else {
			mybaby.setHeight(Float.parseFloat(hei));
		}
		if (wei.length() == 0) {
			mybaby.setWeight(0);
		}else {
			mybaby.setWeight(Float.parseFloat(wei));
		}
		mybaby.setMarks("");
		return mybaby;
	}
}
