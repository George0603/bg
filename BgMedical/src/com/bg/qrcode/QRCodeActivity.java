package com.bg.qrcode;

import com.bg.bgmedical.R;
import com.bg.utils.StatusBarUtils;
import com.zxing.activity.CaptureActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class QRCodeActivity extends Activity {

	private Button scan;
	private TextView result;
	private int qrCode = 0;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		StatusBarUtils.setWindowStatusBarColor(this, R.color.titlebar);
		setContentView(R.layout.qrcode_activity);

		scan = (Button) this.findViewById(R.id.scan);
		result = (TextView) this.findViewById(R.id.result);

		scan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(QRCodeActivity.this,
						CaptureActivity.class);
				startActivityForResult(intent, qrCode);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) { // 此处就是用result来区分,是谁返回的数据
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result"); // 这就获取了扫描的内容了

			char[] mydata = scanResult.toCharArray();
			String name = "" + mydata[0] + mydata[1] + mydata[2] + mydata[3];
			if ("FE3A".equals(name)) {
				String number = "" + mydata[4] + mydata[5];
				String scandata = "" + mydata[6] + mydata[7] + mydata[8]
						+ mydata[9] + mydata[10];
				String date = "20" + mydata[11] + mydata[12] + "年" + mydata[13]
						+ mydata[14] + "月" + mydata[15] + mydata[16] + "日"
						+ mydata[17] + mydata[18] + mydata[19] + mydata[20];
				result.setText("设备编号:" + number
						+ "\n" + "数据" + scandata + "\n" + "日期：" + date);
			} else {
				result.setText("未扫到可用信息");
			}
		}
	}
}
