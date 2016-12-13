package com.bg.bgmedical;

import com.bg.utils.StatusBarUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {

	private EditText tel;
	private EditText password;
	private EditText passure;
	private TextView ifsame;
	private Button register;
	private String telphone;
	private String pass;
	private String passu;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarUtils.setWindowStatusBarColor(this, R.color.titlebar);
		setContentView(R.layout.register_activiyt);

		tel = (EditText) this.findViewById(R.id.tel);
		password = (EditText) this.findViewById(R.id.password);
		passure = (EditText) this.findViewById(R.id.passure);
		ifsame = (TextView) this.findViewById(R.id.ifsame);
		register = (Button) this.findViewById(R.id.register);

		passure.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {

				if (hasFocus) {
					ifsame.setVisibility(View.INVISIBLE);
				} else {
					pass = password.getText().toString().trim();
					passu = passure.getText().toString().trim();

					if (pass.length() != 0) {
						if (!pass.equals(passu)) {
							ifsame.setVisibility(View.VISIBLE);
						}
					} else {
						Toast.makeText(RegisterActivity.this, "密码不能为空", 0)
								.show();
					}
				}

			}
		});

		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				telphone = tel.getText().toString().trim();
				pass = password.getText().toString().trim();
				passu = passure.getText().toString().trim();

				if (telphone == null || telphone.isEmpty()) {
					tel.setHint("电话号码不能为空！");
					tel.setHintTextColor(Color.RED);
					return;
				}
				if (pass == null || pass.isEmpty()) {
					password.setHint("密码不能为空！");
					password.setHintTextColor(Color.RED);
					return;
				}
				if (passu == null || passu.isEmpty()) {
					passure.setHint("确认密码不能为空！");
					passure.setHintTextColor(Color.RED);
					return;
				}
				SharedPreferences share = getSharedPreferences(
						RegisterActivity.this.getString(R.string.login),
						MODE_PRIVATE);
				Editor edit = share.edit();
				edit.putString("telephone", telphone);
				edit.putString("password", pass);
				Boolean flag = edit.commit();
				if (flag) {
					Toast.makeText(RegisterActivity.this, "注册成功", 1).show();
					Intent intent = new Intent(RegisterActivity.this,
							LoginActivity.class);
					startActivity(intent);
					finish();
				}
			}
		});

	}
	
	private boolean matchname(String str) {
		boolean mat = false;
		if (str.matches("^[a-zA-z][a-zA-Z0-9_]{2,9}$")) { // 用户名由 3-10位的字母下划线和数字组成
			mat = true;
		} else {
//			input[0].setText("");
//			input[0].setHint("用户名输入不正确");
//			input[0].setHintTextColor(Color.RED);
		}
		return mat;
	}
}
