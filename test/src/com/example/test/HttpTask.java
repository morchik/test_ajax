package com.example.test;

import android.os.AsyncTask;
import android.util.Log;

class HttpTask extends AsyncTask<String, Integer, String> {
	@Override
	protected String doInBackground(String... params) {
		Log.i("HttpClient", " -> " + params[0] + " -> " + params[1]);
		//
		String data = ((new HttpClient()).getPOSTAJAX(params[0], params[1]));
		Log.v("HttpClient", " result " + data);
		return data;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

	}
}
