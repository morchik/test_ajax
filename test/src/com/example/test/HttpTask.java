package com.example.test;

import android.os.AsyncTask;

class HttpTask extends AsyncTask<String, Integer, String> {
	@Override
	protected String doInBackground(String... params) {
		android.util.Log.i("HttpClient", " -> " + params[0] + " -> " + params[1]+ " -> " + params[2]);
		String data = ((new HttpClient()).getData(params[0]));
		android.util.Log.i("HttpClient", " result " + data);
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
