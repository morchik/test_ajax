package com.example.test;

import android.os.AsyncTask;
import android.util.Log;

class HttpTask extends AsyncTask<String, Integer, String> {
	public String[] in_params; 
	
	@Override
	protected String doInBackground(String... params) {
		in_params = params;
		Log.i("HttpClient", " -> " + params[0] + " -> " + params[1]);
		String data = ((new HttpClient()).getPOSTAJAX(params[0], params[1]));
		Log.v("HttpClient", params[0]+ " json result " + data);
		return data;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		FullscreenActivity.finish_all_tasks(in_params);
	}
}
