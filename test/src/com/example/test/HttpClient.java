package com.example.test;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import android.util.Log;

public class HttpClient {

	public String getPOSTAJAX(String getURL, String getPOSTParameters) {

		// get Impersonation ID
		String getResponseline = "";
		String getPOSTResponse = null;

		try {
			// Open HttpURLConnection
			URL url = new URL(getURL);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();

			urlConn.setRequestProperty("Accept",
					"application/json,text/plain,*/*");
			urlConn.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
			urlConn.setRequestProperty("Connection", "keep-alive");
			urlConn.setRequestProperty("Content-Type",
					"application/json; charset=utf-8");
			// setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded; charset=UTF-8");

			urlConn.setRequestProperty(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.94 Safari/537.36");
			urlConn.setRequestMethod("POST");
			urlConn.setRequestProperty("Content-Length",
					String.valueOf(getPOSTParameters.getBytes().length));
			urlConn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

			// send the POSt Request
			urlConn.setDoOutput(true);
			urlConn.setDoInput(true);
			DataOutputStream writeRequest = new DataOutputStream(
					urlConn.getOutputStream());
			writeRequest.write(getPOSTParameters.getBytes("UTF-8"));
			writeRequest.flush();
			writeRequest.close();

			// To get the Response Codes
			int responseCode = urlConn.getResponseCode();
			Log.d("HttpClient2", "\nSending 'POST' request to URL::" + url);
			Log.d("HttpClient2", "Post parameters ::" + getPOSTParameters);
			Log.d("HttpClient2", "Response Code ::" + responseCode);

			// parse the response
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream(), Charset.forName("UTF-8")));
			while ((getResponseline = reader.readLine()) != null) {
				builder.append(getResponseline);
				builder.append("\n");
			}
			reader.close();
			getPOSTResponse = builder.toString();
			// logger.debug("\n "+getPOSTResponse);
			/*
			 * final String COOKIES_HEADER = "Set-Cookie";
			 * 
			 * java.net.CookieManager msCookieManager = new
			 * java.net.CookieManager();
			 * 
			 * Map<String, List<String>> headerFields =
			 * urlConn.getHeaderFields(); List<String> cookiesHeader =
			 * headerFields.get(COOKIES_HEADER);
			 * 
			 * if(cookiesHeader != null) { for (String cookie : cookiesHeader) {
			 * msCookieManager
			 * .getCookieStore().add(null,HttpCookie.parse(cookie).get(0)); } }
			 * if(msCookieManager.getCookieStore().getCookies().size() > 0) {
			 * Log.d("HttpClient2", TextUtils.join(",",
			 * msCookieManager.getCookieStore().getCookies()));
			 * //connection.setRequestProperty("Cookie", //TextUtils.join(",",
			 * msCookieManager.getCookieStore().getCookies())); }
			 */
			// Disconnect the connection
			urlConn.disconnect();

		} catch (IOException ioe) {
			Log.e("HttpClient2", "\n" + ioe);
		}
		return getPOSTResponse;
	}

	// imitate chrome
	public void setRequestProperty(HttpURLConnection con) {
		con.setRequestProperty("Accept", "application/json; charset=utf-8"); // ajax
																				// application/json;
																				// charset=utf-8
		/*
		 * con.setRequestProperty("Accept-Encoding", "deflate");
		 * con.setRequestProperty("Accept-Language",
		 * "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
		 * con.setRequestProperty("Cache-Control", "no-cache");
		 * con.setRequestProperty("Connection", "keep-alive");
		 * con.setRequestProperty("DNT", "1"); con.setRequestProperty("Pragma",
		 * "no-cache"); con.setRequestProperty( "User-Agent",
		 * "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.111 Safari/537.36"
		 * );
		 */
	}

	public String getData(String l_url) {
		HttpURLConnection con = null;
		InputStream is = null;

		try {
			con = (HttpURLConnection) (new URL(l_url)).openConnection();
			con.setRequestMethod("GET");

			setRequestProperty(con);
			con.setDoInput(true);
			con.setDoOutput(false);
			con.setConnectTimeout(60000);
			con.connect();
			int stt = con.getResponseCode();
			Log.d("HttpClient", "html getResponseCode " + stt + " " + l_url);
			StringBuffer buffer = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line + "\r\n");
				if (line.indexOf("500") >= 1)
					Log.i("HttpClient", line);
			}
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
