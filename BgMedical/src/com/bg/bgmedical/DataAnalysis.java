package com.bg.bgmedical;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bg.model.Baby;
import com.bg.model.BabyData;
import com.bg.sqlite.Controldata;
import com.bg.utils.StatusBarUtils;
import com.google.zxing.WriterException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class DataAnalysis extends BaseActivity implements OnClickListener {
	private Button confirm;
	private Button back;
	private EditText test;
	private TextView babyname;
	
	private TextView bmi;
	private TextView height_weight;
	
	private TableRow tablerow5;
	private TableRow tablerow6;
	private Controldata controldata;
	private int INSERT_BABY = 0;

	private Calendar cal;
	private int myyear;
	private int mymonth;
	private int myday;
	private String curdate;
	
	public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
	public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
	private String mDeviceName;
	private String mDeviceAddress;
	private ServiceConnection mServiceConnection = null;
	private BroadcastReceiver mGattUpdateReceiver = null;
	private BluetoothLeService mBluetoothLeService;
	private boolean mConnected = false;
	private BabyData babydata;
	private SimpleDateFormat sdf;


	private int[] datas = { R.id.height, R.id.weight, R.id.head, R.id.seat, R.id.bust };
	private EditText[] text = new EditText[datas.length];

//	private Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			if (msg.what == 1) {
//				String mydata = (String) msg.obj;
//				String da = getData(mydata);
//				result.setText(mydata + "\n" + da);
//			}
//		};
//	};

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarUtils.setWindowStatusBarColor(this, R.color.titlebar);
		setContentView(R.layout.dataanalysis_activity);
		
		controldata = new Controldata(this);
		babydata = new BabyData();
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		cal = Calendar.getInstance();
		myyear = cal.get(Calendar.YEAR);
		mymonth = cal.get(Calendar.MONTH) + 1;
		myday = cal.get(Calendar.DAY_OF_MONTH);
		curdate = myyear + "-" + mymonth + "-" + myday;
		
		// BMI= 体重(kg)/（身高*身高(m)）
		confirm = (Button) this.findViewById(R.id.confirm);
		back = (Button) this.findViewById(R.id.back);
		test = (EditText) this.findViewById(R.id.test);
		babyname = (TextView) this.findViewById(R.id.babyname);
		tablerow5 = (TableRow) this.findViewById(R.id.tablerow5);
		tablerow6 = (TableRow) this.findViewById(R.id.tablerow6);
		bmi = (TextView) this.findViewById(R.id.bmi);
		height_weight = (TextView) this.findViewById(R.id.height_weight);
		
		searchBaby();
		
		for (int i = 0; i < datas.length; i++) {
			text[i] = (EditText) this.findViewById(datas[i]);
		}

		SharedPreferences share = getSharedPreferences(getResources()
				.getString(R.string.settings), MODE_PRIVATE);
		Map<String, ?> map = new HashMap<String, String>();
		map = share.getAll();
		if (map.size() == 0 || map.get("standard").equals("who")) {
			Toast.makeText(this, "当前标准为who标准", 0).show();
			tablerow5.setVisibility(View.GONE);
			tablerow6.setVisibility(View.GONE);

		} else {
			Toast.makeText(this, "当前标准为9城市标准", 0).show();
			tablerow5.setVisibility(View.VISIBLE);
			tablerow6.setVisibility(View.VISIBLE);
		}

		Intent intent = getIntent();
		char[] mydata = intent.getCharArrayExtra("data");
		if (mydata != null) {
			String scandata = "" + mydata[6] + mydata[7] + mydata[8]
					+ mydata[9] + mydata[10];
			String date = "20" + mydata[11] + mydata[12] + "年" + mydata[13]
					+ mydata[14] + "月" + mydata[15] + mydata[16] + "日"
					+ mydata[17] + mydata[18] + ":" + mydata[19] + mydata[20];
			babyname.setText("设备编号:11 \n数据" + scandata + "\n日期：" + date);
		}

		mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
		if (mDeviceAddress != null) {
			mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);

			mServiceConnection = new ServiceConnection() {
				@Override
				public void onServiceConnected(ComponentName componentName,
						IBinder service) {
					mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
							.getService();
					if (mBluetoothLeService.initialize()) {
						mBluetoothLeService.connect(mDeviceAddress);
					} else {
						Toast.makeText(DataAnalysis.this, "获取设备失败", 0).show();
					}

					// Automatically connects to the device upon successful
					// start-up initialization.
					// mBluetoothLeService.connect(mDeviceAddress);
				}

				@Override
				public void onServiceDisconnected(ComponentName componentName) {
					mBluetoothLeService = null;
					// Log.e(TAG, "onServiceDisconnected Bluetooth");
				}
			};

			// Handles various events fired by the Service.
			// ACTION_GATT_CONNECTED: connected to a GATT server.
			// ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
			// ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
			// ACTION_DATA_AVAILABLE: received data from the device. This can be
			// a result of read
			// or notification operations.
			mGattUpdateReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					final String action = intent.getAction();
					if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) { // 连接成功
						// Log.e(TAG, "Only gatt, just wait");
					} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
							.equals(action)) { // 断开连接
						mConnected = false;
						// invalidateOptionsMenu();
						// btnSend.setEnabled(false);
						// clearUI();
					} else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
							.equals(action)) // 可以开始干活了
					{
						mConnected = true;
						// mDataField.setText("");
						ShowDialog();
						// btnSend.setEnabled(true);
						// Log.e(TAG, "In what we need");
						// invalidateOptionsMenu();
					} else if (BluetoothLeService.ACTION_DATA_AVAILABLE
							.equals(action)) { // 收到数据
						// Log.e(TAG, "RECV DATA");
						String data = intent
								.getStringExtra(BluetoothLeService.EXTRA_DATA);
						if (data != null) {

//							Message message = Message.obtain();
//							Bundle bundle = new Bundle();
//							message.obj = data;
//							message.what = 1;
//							handler.sendMessage(message);
							
//							String da = getData(data);
//							result.setText(da);
							getData(data);	
						}
					}
				}
			};

			Intent gattServiceIntent = new Intent(this,
					BluetoothLeService.class);
			// Log.d(TAG, "Try to bindService=" +
			bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
			registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
		}

		confirm.setOnClickListener(this);
		back.setOnClickListener(this);
		bmi.setOnClickListener(this);
		height_weight.setOnClickListener(this);
		test.setOnClickListener(this);
	}

	// FA A1 07 01 E3 02 C5 00 00 FF
	// *1：体重 = (体重（高位）*256+体重（低位）)*10 体重（高位） 单位：g （
	// *2：身长 = 身长（高位）*256+身长（低位） 身长（高位）单位：mm（*1）
	// *3：坐高 = 坐高（高位）*256+坐高（低位） 坐高（高位）单位：mm（*1）
	private void getData(String data) {
		String result = null;
		String[] str = data.split(" ");

		int weight_h = Integer.parseInt(str[3], 16);
		int weight_l = Integer.parseInt(str[4], 16);
		int height_h = Integer.parseInt(str[5], 16);
		int height_l = Integer.parseInt(str[6], 16);
		int head_h = Integer.parseInt(str[7], 16);
		int head_l = Integer.parseInt(str[8], 16);

		text[0].setText(((weight_h * 256 + weight_l) * 10)/1000.0f +"");
		text[1].setText((height_h * 256 + height_l)/1000.0f+"");
		text[2].setText((head_h * 256 + head_l)/100.f+"");
//		result = "体重为：" + weight / 1000.0 + "kg\n身高为：" + height / 10.0
//				+ "cm\n坐高为：" + head / 10.0 + "cm";
//		return result;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mServiceConnection != null) {
			unregisterReceiver(mGattUpdateReceiver);
			unbindService(mServiceConnection);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mBluetoothLeService != null) {
			mBluetoothLeService.close();
			mBluetoothLeService = null;
		}
	}

	@Override
	public void onClick(View v) {
		String height = text[0].getText().toString();
		String weight = text[1].getText().toString();
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.confirm:
			if (height.length() == 0|| weight.length() == 0) {
				Toast.makeText(this, "身高或体重不能为空", 0).show();
			}else{
				for (int i = 0; i < datas.length; i++) {
					text[i].setFocusable(false);
					}
				float he = Float.parseFloat(height);
				float we = Float.parseFloat(weight);		
					bmi.setText(we/(he*he)+"");
					height_weight.setText(he/we+"");
//		datas = { R.id.height, R.id.weight, R.id.head, R.id.seat, R.id.bust };
//controldata.insert_data(babyname, date, height, weight, bmi, height_weight, head, seat, bust)
					if (test.getText().toString().length() != 0) {
						curdate = test.getText().toString();
					}
					babydata = getBabyData(text);
					
					boolean insert_succeed = controldata.insert_data(babydata);
					if (insert_succeed) {
						confirm.setVisibility(View.GONE);
						Toast.makeText(this, "数据已存储", 0).show();
					}else {
						Toast.makeText(this, "存储失败！", 0).show();
					}
				}
			break;
		case android.R.id.home:
			if (mConnected) {
				mBluetoothLeService.disconnect();
				mConnected = false;
			}
			break;
		case R.id.bmi:
		case R.id.height_weight:
			if (height.length() == 0|| weight.length() == 0) {
				Toast.makeText(this, "身高或体重不能为空", 0).show();
			}else{
				float he = Float.parseFloat(height);
				float we = Float.parseFloat(weight);
				if (v.getId() == R.id.bmi) {
					bmi.setText(we/(he*he)+"");
				}else{
					height_weight.setText(he/we+"");
				}
			}
			break;
		case R.id.test:
			new DatePickerDialog(this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
						test.setText(year + "-" + (monthOfYear + 1) + "-"
								+ dayOfMonth);	
				}
			}, myyear, cal.get(Calendar.MONTH), myday).show();
			break;
		default:
			break;
		}
	}

	private void ShowDialog() {
		Toast.makeText(this, "连接成功，现在可以正常通信！", Toast.LENGTH_SHORT).show();
	}

	private static IntentFilter makeGattUpdateIntentFilter() { // 注册接收的事件
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		intentFilter.addAction(BluetoothDevice.ACTION_UUID);
		return intentFilter;
	}
	
	private void searchBaby(){
		 List<String> baby = controldata.querybabyname();
		 if (baby.size() == 0) {
			Toast.makeText(this, "当前无儿童信息，请添加！", 0).show();
			Intent in = new Intent(DataAnalysis.this,ChildInfordetail.class);
			startActivityForResult(in, INSERT_BABY);
		}else if (baby.size() == 1) {
			babyname.setText(baby.get(0));
		}else {
			final String[] mybaby = new String[baby.size()];
			for (int i = 0; i < mybaby.length; i++) {
				mybaby[i] = baby.get(i);
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("请选择儿童:");
			builder.setItems(mybaby, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					babyname.setText(mybaby[which]);
				}
			});
			builder.setCancelable(false);
			builder.create().show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == INSERT_BABY && resultCode == RESULT_OK) {
			String str = data.getStringExtra("babyname"); 
			babyname.setText("当前儿童为 ：" + str);
		}
	}
	
	private BabyData getBabyData(EditText[] input){
		
		BabyData mybabydata = new BabyData();
		mybabydata.setBabyname(babyname.getText().toString());
		try {
			mybabydata.setDate(sdf.parse(curdate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String hei = input[0].getText().toString();
		String wei = input[1].getText().toString();
		String hea = input[2].getText().toString();
		String sea = input[2].getText().toString();
		String bus = input[2].getText().toString();
		
		if (hei.length() == 0) {
			mybabydata.setHeight(0);
		}else {
			mybabydata.setHeight(Float.parseFloat(hei));
		}
		if (wei.length() == 0) {
			mybabydata.setWeight(0);
		}else {
			mybabydata.setWeight(Float.parseFloat(wei));
		}
		if (hea.length() == 0) {
			mybabydata.setHead(0);
		}else {
			mybabydata.setHead(Float.parseFloat(hea));
		}
		if (sea.length() == 0) {
			mybabydata.setSeat(0);
		}else {
			mybabydata.setSeat(Float.parseFloat(hea));
		}
		if (bus.length() == 0) {
			mybabydata.setBust(0);
		}else {
			mybabydata.setBust(Float.parseFloat(hea));
		}
		
		mybabydata.setBmi(Float.parseFloat(bmi.getText().toString()));
		mybabydata.setHeight_weight(Float.parseFloat(height_weight.getText().toString()));
		
		return mybabydata;
	}
}
