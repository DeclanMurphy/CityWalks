package com.example.citywalks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import android.util.Log;

public class CoordinateHttpClient {

	private static String BASE_URL = "http://192.168.1.100/cwdb/get_all_coordinates.php";

	public String getCoordinateData() {
		try {
			URL oracle = new URL(BASE_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while (  (line = in.readLine()) != null )
				buffer.append(line + "\r\n");

			in.close();
			
			return buffer.toString();
	    }
		catch(Throwable t) {
			Log.e("TAG", "Error1", t);			
		}
		return null;
	}
}
