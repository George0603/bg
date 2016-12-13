package com.bg.bgmedical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.bg.utils.Constant;
import com.bg.utils.StatusBarUtils;
import com.zxing.activity.CaptureActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("InflateParams")
public class MainActivity extends BaseActivity implements OnItemClickListener {

	private BluetoothAdapter mBluetoothAdapter;
	private LeDeviceListAdapter mLeDeviceListAdapter;
	private ProgressBar progressBar;
	private Button button;
	private boolean mScanning = true;
	private static final long SCAN_PERIOD = 3000;
	private Button qrcode;
	private int QR_CODE = 0;
	private ViewFlipper mViewFlipper;
	private int[] res = { R.drawable.ppt1, R.drawable.ppt2, R.drawable.ppt3 };

	private GridView mGridView;
	private int[] icon = { R.drawable.gridview0, R.drawable.gridview0,
			R.drawable.gridview0, R.drawable.gridview0, R.drawable.gridview0,
			R.drawable.gridview0, R.drawable.gridview0, R.drawable.gridview0 };
	private String[] icontext = { "生长发育", "血糖仪", "血糖仪", "血糖仪", "血糖仪", "血糖仪",
			"血糖仪", "血糖仪" };
	private static final int REQUEST_ENABLE_BT = 1;
	private List<BaseActivity> pages = new ArrayList<BaseActivity>();

	private SimpleAdapter simple;
	private boolean if_supported;
	private TextView result;

	@SuppressLint({ "ResourceAsColor", "NewApi" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarUtils.setWindowStatusBarColor(this, R.color.titlebar);
		setContentView(R.layout.main_activity);

		mViewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
		mGridView = (GridView) this.findViewById(R.id.gridView);

		button = (Button) this.findViewById(R.id.button);
		progressBar = (ProgressBar) this.findViewById(R.id.progressBar);
		qrcode = (Button) this.findViewById(R.id.qrcode);
		result = (TextView) this.findViewById(R.id.result);

		mLeDeviceListAdapter = new LeDeviceListAdapter();

		if_supported = getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE);
		if (if_supported) {
			// 初始化 Bluetooth adapter,
			// 通过蓝牙管理器得到一个参考蓝牙适配器(API必须在以上android4.3或以上版本)
			final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
			mBluetoothAdapter = bluetoothManager.getAdapter();
			// 检查设备上是否支持蓝牙
			if_supported = mBluetoothAdapter != null ? true : false;
			if (!Constant.firstIn) {
				// 如果蓝牙已开启第一次进入，自动搜索
				Constant.firstIn = true;
				isavailable(if_supported);
			}
		} else {
			Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT)
					.show();
		}

		// if (getPackageManager().hasSystemFeature(
		// PackageManager.FEATURE_BLUETOOTH_LE)) {
		// // 初始化 Bluetooth adapter,
		// // 通过蓝牙管理器得到一个参考蓝牙适配器(API必须在以上android4.3或以上版本)
		// final BluetoothManager bluetoothManager = (BluetoothManager)
		// getSystemService(Context.BLUETOOTH_SERVICE);
		// mBluetoothAdapter = bluetoothManager.getAdapter();
		// // 检查设备上是否支持蓝牙
		// if (mBluetoothAdapter != null ) {
		// if (!mBluetoothAdapter.isEnabled()) { //设备是否可用，不可用则开启
		// Intent enableBtIntent = new Intent(
		// BluetoothAdapter.ACTION_REQUEST_ENABLE);
		// startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		// }else {
		// if (!Constant.firstIn) { //如果蓝牙已开启第一次进入，自动搜索
		// scanLeDevice(true);
		// }
		// }
		//
		// }
		// } else {
		// Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT)
		// .show();
		// }

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

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!mScanning) {
					isavailable(if_supported);
				} else {
					scanLeDevice(false);
				}
			}
		});
		qrcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Intent qr = new Intent(this,QRCodeActivity.class);
				// Intent qr = new Intent(this, BarCodeTestActivity.class);
				// startActivity(qr);
				Intent intent = new Intent(MainActivity.this,
						CaptureActivity.class);
				startActivityForResult(intent, QR_CODE);

			}
		});
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
		ChildGrowthActivity child = new ChildGrowthActivity();
		pages.add(child);
		pages.add(child);
		pages.add(child);
		pages.add(child);
		pages.add(child);
		pages.add(child);
		pages.add(child);
		pages.add(child);
		return pages;
	}

	@Override
	protected void onPause() {
		super.onPause();
		scanLeDevice(false);
		// mLeDeviceListAdapter.clear();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mBluetoothAdapter.disable();
	}

	/**
	 * 捕获手机物理菜单键
	 */
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {// && event.getAction() ==
												// KeyEvent.ACTION_DOWN
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				myExit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void myExit() {
		Intent intent = new Intent();
		intent.setAction("ExitApp");
		this.sendBroadcast(intent);
		super.finish();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		Intent intent = new Intent(this, pages.get(position).getClass());
		startActivity(intent);
	}

	@SuppressLint("NewApi")
	private void scanLeDevice(final boolean enable) {
		if (enable) {
			// Stops scanning after a pre-defined scan period.
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					if (mScanning) {
						mScanning = false;
						mBluetoothAdapter.stopLeScan(mLeScanCallback);
						invalidateBbutton(mScanning);
						mHandler.sendEmptyMessage(2);
					}
				}
			}, SCAN_PERIOD);

			mScanning = true;
			// F000E0FF-0451-4000-B000-000000000000
			mLeDeviceListAdapter.clear();
			mHandler.sendEmptyMessage(1);
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		} else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
		invalidateBbutton(mScanning);
	}

	private void invalidateBbutton(Boolean scan) {
		if (!mScanning) {
			button.setText("搜索蓝牙");
			progressBar.setVisibility(View.GONE);
		} else {
			button.setText("停止搜索");
			progressBar.setVisibility(View.VISIBLE);
		}
	}

	@SuppressLint("NewApi")
	private void isavailable(boolean available) { // isbutton 是否是按钮进入
		if (available) { // 设备是否可用，不可用则开启
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} else {
			scanLeDevice(true);
		}

	}

	// Hander
	public final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1: // Notify change
				mLeDeviceListAdapter.notifyDataSetChanged();
				break;
			case 2: // Notify change

				if (mLeDeviceListAdapter.mLeDevices.size() == 0) {
					Toast toast = Toast.makeText(MainActivity.this, "当前无可用设备",
							Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					mydialog = myDialog();
					mydialog.show();
				}
				break;
			}
		}
	};
	private AlertDialog mydialog = null;

	@SuppressLint("NewApi")
	private AlertDialog myDialog() {
		AlertDialog.Builder dia = new AlertDialog.Builder(MainActivity.this);
		AlertDialog dialog = null;
		dia.setTitle("发现可用设备是否连接？");
		View view = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.bluetooth_dialog, null);

		ListView list_item = (ListView) view.findViewById(R.id.listView);
		list_item.setAdapter(mLeDeviceListAdapter);
		list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BluetoothDevice device = mLeDeviceListAdapter
						.getDevice(position);
				if (device == null) {
					return;
				} else {
					Intent intent_act = new Intent();
					String name = device.getName();
					switch (name) {
					case "HMSoft":

						intent_act.setClass(MainActivity.this,
								DataAnalysis.class);

						intent_act.putExtra(
								DataAnalysis.EXTRAS_DEVICE_NAME,
								device.getName());
						intent_act.putExtra(
								DataAnalysis.EXTRAS_DEVICE_ADDRESS,
								device.getAddress());
						startActivity(intent_act);
						break;
					default:
						Toast.makeText(MainActivity.this, "该设备不可用",
								Toast.LENGTH_SHORT).show();
						break;
					}

					if (mScanning) {
						mBluetoothAdapter.stopLeScan(mLeScanCallback);
						mScanning = false;
					}
					mydialog.cancel();

				}
			}
		});
		// dia.setPositiveButton("确定", new DialogInterface.OnClickListener() {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// });
		dia.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (dialog != null) {
					dialog.cancel();
					scanLeDevice(false);
				}
			}
		});
		dia.setView(view);
		dialog = dia.create();
		dialog.setCancelable(false);
		// dialog.show();
		return dialog;
	}

	@SuppressLint("NewApi")
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi,
				final byte[] scanRecord) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mLeDeviceListAdapter.addDevice(device);
					mHandler.sendEmptyMessage(1);
				}
			});
		}
	};

	private class LeDeviceListAdapter extends BaseAdapter {
		private ArrayList<BluetoothDevice> mLeDevices;
		private LayoutInflater mInflator;

		public LeDeviceListAdapter() {
			super();
			mLeDevices = new ArrayList<BluetoothDevice>();
			mInflator = MainActivity.this.getLayoutInflater();
		}

		public void addDevice(BluetoothDevice device) {
			if (!mLeDevices.contains(device)) {
				mLeDevices.add(device);
			}
		}

		public BluetoothDevice getDevice(int position) {
			return mLeDevices.get(position);
		}

		public void clear() {
			mLeDevices.clear();
		}

		@Override
		public int getCount() {
			return mLeDevices.size();
		}

		@Override
		public Object getItem(int i) {
			return mLeDevices.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			ViewHolder viewHolder;
			// General ListView optimization code.
			if (view == null) {
				view = mInflator.inflate(R.layout.listitem_device, null);
				viewHolder = new ViewHolder();
				viewHolder.deviceAddress = (TextView) view
						.findViewById(R.id.device_address);
				viewHolder.deviceName = (TextView) view
						.findViewById(R.id.device_name);
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			BluetoothDevice device = mLeDevices.get(i);
			final String deviceName = device.getName();
			if (deviceName != null && deviceName.length() > 0)
				viewHolder.deviceName.setText(deviceName);
			else
				viewHolder.deviceName.setText(R.string.unknown_device);

			viewHolder.deviceAddress.setText(device.getAddress());

			return view;
		}
	}

	static class ViewHolder {
		TextView deviceName;
		TextView deviceAddress;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_ENABLE_BT
				&& resultCode == Activity.RESULT_OK) {
			scanLeDevice(true);// 自动搜索
		} else if (requestCode == QR_CODE && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result"); // 这就获取了扫描的内容了
			char[] mydata = null;
			if (scanResult != null) {
				mydata = scanResult.toCharArray();
			}
			if (mydata.length > 6) {
				String name = "" + mydata[0] + mydata[1] + mydata[2]
						+ mydata[3];
				String number = "" + mydata[4] + mydata[5];
				if ("FE3A".equals(name)) {
					switch (number) {
					case "11":
						String scandata = "" + mydata[6] + mydata[7]
								+ mydata[8] + mydata[9] + mydata[10];
						String date = "20" + mydata[11] + mydata[12] + "年"
								+ mydata[13] + mydata[14] + "月" + mydata[15]
								+ mydata[16] + "日" + mydata[17] + mydata[18]
								+ ":" + mydata[19] + mydata[20];
						result.setText("设备编号:11 \n数据" + scandata + "\n日期："
								+ date);
						Intent myint = new Intent();
						myint.putExtra("data", mydata);
						myint.setClass(MainActivity.this, DataAnalysis.class);
						startActivity(myint);
						break;
					case "41":
						String weight = "" + mydata[6] + mydata[7] + mydata[8]
								+ mydata[9] + mydata[10] + mydata[11];
						String height = "" + mydata[12] + mydata[13]
								+ mydata[14] + mydata[15];
						String seat = "" + mydata[16] + mydata[17] + mydata[18];
						String head = "" + mydata[19] + mydata[20] + mydata[21];
						String bust = "" + mydata[22] + mydata[23] + mydata[24];
						String dat = "20" + mydata[25] + mydata[26] + "年"
								+ mydata[27] + mydata[28] + "月" + mydata[29]
								+ mydata[30] + "日" + mydata[31] + mydata[32]
								+ ":" + mydata[33] + mydata[34];
						String mark = "" + mydata[35];
						result.setText("设备编号:41\n" + "体重：" + weight + "\n身高："
								+ height + "\n坐高：" + seat + "\n头围：" + head
								+ "\n胸围：" + bust + "\n日期：" + dat);
						break;
					default:
						result.setText("设备信息不可用");
						break;
					}

				} else {
					result.setText("未扫到可用信息");
				}
			} else {
				result.setText("未扫到可用信息");
			}
		}

	}

}
