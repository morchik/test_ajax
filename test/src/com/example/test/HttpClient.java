package com.example.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpClient {
	
	// imitate chrome
	public void setRequestProperty(HttpURLConnection con) {
			con.setRequestProperty("Accept", "application/json; charset=utf-8"); // ajax application/json; charset=utf-8
			con.setRequestProperty("Accept-Encoding", "deflate");
			con.setRequestProperty("Accept-Language",
					"ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Connection", "keep-alive");
			con.setRequestProperty("DNT", "1");
			con.setRequestProperty("Pragma", "no-cache");
			con.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36");
		}

	public String getData(String l_url) {
		HttpURLConnection con = null;
		InputStream is = null;

		try {
			con = (HttpURLConnection) (new URL(l_url))
					.openConnection();
			con.setRequestMethod("POST");
			
			setRequestProperty(con);
			con.setDoInput(true);
			con.setDoOutput(false);
			con.setConnectTimeout(60000);
			con.connect();	
			int stt = con.getResponseCode();
			Log.d("HttpClient", "html getResponseCode " + stt+" "+l_url);
			StringBuffer buffer = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null)
				buffer.append(line + "\r\n");

			is.close();
			con.disconnect();
			return buffer.toString();
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
			}
			try {
				con.disconnect();
			} catch (Throwable t) {
			}
		}
		return null;
	}
}
