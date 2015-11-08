package com.example.test;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import me.noip.adimur.smstele2kz.R;

import com.example.test.util.SystemUiHider;
import com.example.test.util.rsa;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
@SuppressLint({ "ClickableViewAccessibility", "SimpleDateFormat" })
public class FullscreenActivity extends Activity {

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem mi = menu.add(0, 1, 0, R.string.settings);
		mi.setIntent(new Intent(this, PrefActivity.class));
		return super.onCreateOptionsMenu(menu);
	}

	public static AlertDialog alert = null;
	public static HttpTask task, task2;
	public static FullscreenActivity last_activity = null;
	private String sy_phone, sy_pass;
	Boolean b_debug;
	private SharedPreferences sp;
	private EditText edNumber, edMessage;
	private TextView tvStatus, tvDebug;

	protected void onResume() {
		b_debug = sp.getBoolean("chb_debug", false);
		sy_phone = sp.getString("y_phone", "");
		sy_pass = sp.getString("y_pass", "");
		String s_temp = getString(R.string.text_status);
		String text = s_temp.replace("7072282999", sy_phone);
		tvStatus.setText(text);
		if (edNumber.getEditableText().toString().equalsIgnoreCase("")) {
			String ssend_phone = sp.getString("s_phone", "");
			edNumber.setText(ssend_phone);
		}
		super.onResume();
		try {
			SensorManager mgr = (SensorManager) getSystemService(SENSOR_SERVICE);
			List<Sensor> sensors = mgr.getSensorList(Sensor.TYPE_ALL);
			for (Sensor sensor : sensors) {
				Log.v("", "" + sensor.getName());

			}
		} catch (Exception e) {
			Log.e("", e.toString());
		}
	}

	public boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		last_activity = this;
		setContentView(R.layout.activity_fullscreen);
		sp = PreferenceManager.getDefaultSharedPreferences(this);

		edNumber = (EditText) findViewById(R.id.edNumber);
		edMessage = (EditText) findViewById(R.id.edMessage);

		tvStatus = (TextView) findViewById(R.id.tvStatus);
		tvDebug = (TextView) findViewById(R.id.tvDebug);
		tvDebug.setMovementMethod(new ScrollingMovementMethod());

	}

	public void alert_dlg() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				FullscreenActivity.this);
		builder.setMessage("data sending...")
			.setIcon(R.drawable.ic_launcher)
			.setPositiveButton("", null);
		alert = builder.create();
		alert.show();
	}

	public static void finish_all_tasks(String... params) {
		if (params[0].indexOf("SendSms") > 1) {
			if (alert != null)
				alert.cancel();
			if (last_activity != null)
				last_activity.finish_task();
		};
	}

	public void finish_task() {
		try {
			// SystemClock.sleep(100);
			String success = json_par.test_a(task.get());
			if (!success.equalsIgnoreCase("true")) {
				// Toast.makeText(this, getString(R.string.text_err_login),
				// Toast.LENGTH_SHORT).show();
				tvDebug.setText(getString(R.string.text_err_login) + "\n"
						+ tvDebug.getText().toString());
			} else {
				tvDebug.setText(getString(R.string.text_pass_corct) + "\n"
						+ tvDebug.getText().toString());
				String send = json_par.test_s(task2.get());
				if (!send.equalsIgnoreCase("true")) {
					// Toast.makeText(this, send + " " + task.get(),
					// Toast.LENGTH_LONG).show();
					tvDebug.setText(send + "\n" + tvDebug.getText().toString());
				} else {
					tvDebug.setText(getString(R.string.text_suc_send) + "\n"
							+ tvDebug.getText().toString());
					String amn = json_par.get_AmountSmsLeft(task2.get());
					tvDebug.setText(getString(R.string.text_sms_left) + " "
							+ amn + "\n"
							+ edMessage.getEditableText().toString() + "\n"
							+ tvDebug.getText().toString());
				}
			}
			if (b_debug) {
				tvDebug.setText(task.get() + "\n"
						+ tvDebug.getText().toString());
				tvDebug.setText(task2.get() + "\n"
						+ tvDebug.getText().toString());
			}
			String Dtime = new SimpleDateFormat("yyyy.MM.dd   HH:mm:ss z")
					.format(new Date());
			tvDebug.setText("\n" + Dtime 
					+ " " +getString(R.string.text_finish_send)+ "\n"
					+ edMessage.getEditableText().toString() + "\n"
					+ edNumber.getEditableText().toString() + "\n"
					+ tvDebug.getText().toString());
		} catch (InterruptedException | ExecutionException e) {
			Log.e("click", e.toString());
			e.printStackTrace();
		}
	}

	public void click_set(View view) {
		startActivity(new Intent(this, PrefActivity.class));
	}

	@SuppressWarnings("deprecation")
	public void click(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edMessage.getWindowToken(), 0);
		if (sy_phone.equalsIgnoreCase("") || sy_pass.equalsIgnoreCase("")) {
			// Toast.makeText(this, getString(R.string.text_err_sett),
			// Toast.LENGTH_SHORT).show();
			tvDebug.setText(getString(R.string.text_err_sett) + "\n"
					+ tvDebug.getText().toString());
		} else if ((sy_phone.length() != 10)
				|| (edNumber.getEditableText().toString().length() != 10)) {
			// Toast.makeText(this, getString(R.string.text_err_num10),
			// Toast.LENGTH_SHORT).show();
			tvDebug.setText(getString(R.string.text_err_num10) + "\n"
					+ tvDebug.getText().toString());
		} else if (edMessage.getEditableText().toString().length() < 1) {
			// Toast.makeText(this, getString(R.string.text_err_msg0),
			// Toast.LENGTH_SHORT).show();
			tvDebug.setText(getString(R.string.text_err_msg0) + "\n"
					+ tvDebug.getText().toString());
		} else if (isOnline()) {
			alert_dlg();
			// test ajax
			CookieManager cookieManager = new CookieManager();
			CookieHandler.setDefault(cookieManager);

			task = new HttpTask();
			task.execute(new String[] {
					"http://www.almaty.tele2.kz/WebServices/authenticate.asmx/Authenticate",
					"{\"number\": \"" + sy_phone + "\",  \"password\": \""
							+ sy_pass + "\"}" });

			task2 = new HttpTask();
			task2.execute(new String[] {
					"http://www.almaty.tele2.kz/WebServices/smsService.asmx/SendSms",
					"{\"msisdn\": \"" + edNumber.getEditableText().toString()
							+ "\",  \"message\": \""
							+ edMessage.getEditableText().toString() + "\"}" });
		} else {
			// Toast.makeText(this, getString(R.string.text_err_online),
			// Toast.LENGTH_SHORT).show();
			tvDebug.setText(getString(R.string.text_err_online) + "\n"
					+ tvDebug.getText().toString());
		}
		String Dtime = new SimpleDateFormat("yyyy.MM.dd   HH:mm:ss z")
				.format(new Date());
		tvDebug.setText("\n" + Dtime 
				+ " " +getString(R.string.text_start_send)+ "\n"
				+ tvDebug.getText().toString());
		try {
			rsa.main();
		} catch (Exception e) {
			tvDebug.setText((new Date()).toGMTString() + "\n" + e.toString()
					+ "\n" + tvDebug.getText().toString());
		}
	}
}
