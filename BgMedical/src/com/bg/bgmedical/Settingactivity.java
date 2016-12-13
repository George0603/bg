package com.bg.bgmedical;

import com.bg.utils.StatusBarUtils;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Settingactivity extends BaseActivity {

	private TextView standard;
	private RadioGroup radioGroup;
	private LinearLayout show;
	private String str = "standard";

	private RadioButton who;
	private RadioButton nine_city;
	private SharedPreferences share;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarUtils.setWindowStatusBarColor(this, R.color.titlebar);
		setContentView(R.layout.setting_activity);

		share = getSharedPreferences(getResources()
				.getString(R.string.settings), MODE_PRIVATE);
		standard = (TextView) this.findViewById(R.id.standard);
		radioGroup = (RadioGroup) this.findViewById(R.id.radioGroup);
		show = (LinearLayout) this.findViewById(R.id.show);
		who = (RadioButton) this.findViewById(R.id.who);
		nine_city = (RadioButton) this.findViewById(R.id.nine_city);

		if (share.getString(str, null) != null) {
			if (share.getString(str, "").equals("who")) {
				who.setChecked(true);
			} else {
				nine_city.setChecked(true);
			}
		}

		standard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (show.getVisibility() == View.GONE) {
					show.setVisibility(View.VISIBLE);
				} else {
					show.setVisibility(View.GONE);
				}
			}
		});

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				Editor edit = share.edit();

				if (checkedId == R.id.who) {
					edit.putString(str, "who");
				} else {
					edit.putString(str, "nine_city");
				}

				Boolean flag = edit.commit();
				if (flag) {
					Toast.makeText(Settingactivity.this, "设置成功！", 0).show();
				}
			}
		});

	}
}
