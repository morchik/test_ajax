package com.example.test;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class json_par {
	public static String test_a(String data) {
		try {

			JSONObject jObj = new JSONObject(data);
			if (!jObj.has("d"))
				return "error";
			else {
				String msg = jObj.getString("Message");
				String suc = jObj.getString("Success");
				if (!suc.equalsIgnoreCase("true")) {
					return "true";
				} else {
					Log.v("json_par", data);
					return msg;
				}
			}
		} catch (JSONException e) {
			android.util.Log.e("json_par", e.toString() + " " + data);
			return "error parse "+e.toString();
		} catch (Exception e) {
			android.util.Log.e("json_par error ", e.toString() + " " + data);
			return "error "+e.toString();
		}
	}

	public static String test_s(String data) {
		try {

			JSONObject jObj = new JSONObject(data);
			if (!jObj.has("d"))
				return "error";
			else {
				String err = jObj.getString("ErrorCode");
				String rmsg = jObj.getString("ResponseMessage");
				if (!err.equalsIgnoreCase("0")) {
					return rmsg;
				} else {
					Log.v("json_par", rmsg);
					return "true";
				}
			}
		} catch (JSONException e) {
			android.util.Log.e("json_par", e.toString() + " " + data);
			return "error parse "+e.toString();
		} catch (Exception e) {
			android.util.Log.e("json_par error ", e.toString() + " " + data);
			return "error "+e.toString();
		}
	}
}
