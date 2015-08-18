package com.sinowel.netflow.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Gethtml {
	public static void main(String[] args) {

		System.out.println(getHtmlConentByUrl("http://m.ele.me/hongbao?hardware_id=0ba0428d682e10239c38b36330f9055d&sn=c82722ced7d7eaa794d7a0d198a72594&device_id=5cdacfd8-ec63-34ac-be91-1ce7f836318e&track_id=1423012258%7C_ad70db0a-ac0a-11e4-8e96-b82a72dd4aa4%7Cbd49b8ded37fecc55e8a5dedea4b0a43&from=timeline&isappinstalled=0&code=0016682459fb5bf9aa7b209b3057b9fe&state=1423447790576"));
	}

	public static String getHtmlConentByUrl(String ssourl) {
		try {
			URL url = new URL(ssourl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setInstanceFollowRedirects(false);
			con.setUseCaches(false);
			con.setAllowUserInteraction(false);
			con.connect();
			StringBuffer sb = new StringBuffer();
			String line = "";
			BufferedReader URLinput = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			while ((line = URLinput.readLine()) != null) {
				sb.append(line);
			}
			con.disconnect();
			return sb.toString().toLowerCase();
		} catch (Exception e) {

			return null;
		}
	}

}