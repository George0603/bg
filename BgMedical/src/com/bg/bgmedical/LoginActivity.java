package com.bg.bgmedical;

import java.util.HashMap;
import java.util.Map;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bg.bluetooth.DeviceScanActivity;
import com.bg.bluetooth.DeviceScanActivity1;
import com.bg.utils.StatusBarUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

	private EditText username;
	private EditText password;
	private Button login;
	private Button reset;
	private LocationClient mLocationClient; // baidu 地图

	@SuppressLint({ "ResourceAsColor", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarUtils.setWindowStatusBarColor(this, R.color.titlebar);
		setContentView(R.layout.login_activity);

		mLocationClient = new LocationClient(this.getApplicationContext()); // baidu
																			// 地图
		mLocationClient.registerLocationListener(new MyLocationListener()); // baidu
																			// 地图
		InitLocation(); // baidu 地图
		mLocationClient.start(); // baidu 地图

		username = (EditText) this.findViewById(R.id.username);
		password = (EditText) this.findViewById(R.id.password);
		login = (Button) this.findViewById(R.id.login);
		reset = (Button) this.findViewById(R.id.register);

		login.setOnClickListener(this);
		reset.setOnClickListener(this);

	}

	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.login:
			String myname = username.getText().toString().trim();
			String mypassword = password.getText().toString().trim();
			if (myname == null || myname.isEmpty()) {
				Toast.makeText(this, "用户名为空，不能登录！", Toast.LENGTH_SHORT).show();
			} else if (mypassword == null || mypassword.isEmpty()) {
				Toast.makeText(this, "密码为空，不能登录！", Toast.LENGTH_SHORT).show();
			} else {
				SharedPreferences share = getSharedPreferences(
						LoginActivity.this.getString(R.string.login),
						MODE_PRIVATE);
				Map<String, ?> map = new HashMap<String, String>();
				map = share.getAll();
				if (share != null && map.size() != 0) {
					if ((map.get("telephone").equals(myname) && map.get(
							"password").equals(mypassword))) {
						intent.setClass(this, MainActivity.class);
						overridePendingTransition(R.anim.push_left_out,
								R.anim.push_left_out);
						startActivity(intent);
						finish();
					} else {
						Toast.makeText(this, "用户名或密码错误，不能登录！",
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(this, "用户名或密码错误，不能登录！", Toast.LENGTH_SHORT)
							.show();
				}
			}
			break;
		case R.id.register:
			intent.setClass(this, RegisterActivity.class);
			overridePendingTransition(R.anim.push_left_out,
					R.anim.push_left_out);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mLocationClient.stop();
		super.onStop();
	}

	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		// option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
		int span = 1000;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	/**
	 * 实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// Receive Location
			// StringBuffer sb = new StringBuffer(256);
			// sb.append("time : ");
			// sb.append(location.getTime());
			// sb.append("\nerror code : ");
			// sb.append(location.getLocType());
			// sb.append("\nlatitude : ");
			// sb.append(location.getLatitude());
			// sb.append("\nlontitude : ");
			// sb.append(location.getLongitude());
			// sb.append("\nradius : ");
			// sb.append(location.getRadius());
			// if (location.getLocType() == BDLocation.TypeGpsLocation) {
			// sb.append("\nspeed : ");
			// sb.append(location.getSpeed());
			// sb.append("\nsatellite : ");
			// sb.append(location.getSatelliteNumber());
			// sb.append("\ndirection : ");
			// sb.append("\naddr : ");
			// sb.append(location.getAddrStr());
			// sb.append(location.getDirection());
			// } else if (location.getLocType() ==
			// BDLocation.TypeNetWorkLocation) {
			// sb.append("\naddr : ");
			// sb.append(location.getAddrStr());
			// // 运营商信息
			// sb.append("\noperationers : ");
			// sb.append(location.getOperators());
			// }

			if (location.getAddrStr() != null) {
				mLocationClient.stop();
				Toast.makeText(LoginActivity.this,
						"当前位置：" + location.getAddrStr(), 0).show();
			}
		}
	}

}
