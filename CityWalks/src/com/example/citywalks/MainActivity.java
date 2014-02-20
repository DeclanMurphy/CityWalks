package com.example.citywalks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	ArrayList<Coordinate> coList = new ArrayList<Coordinate>();
	private static String url = "http://192.168.1.101/cwdb/get_all_coordinates.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

		final HttpClient httpclient = new DefaultHttpClient();
		final HttpPost httppost = new HttpPost(url);
		final TextView textView = (TextView) findViewById(R.id.tv1);
		Button btnDisplay = (Button) findViewById(R.id.btnDisplay);
		
		
		btnDisplay.setOnClickListener(new OnClickListener() {
        	@Override
			public void onClick(View arg0) {

        		try {

        			HttpResponse response = httpclient.execute(httppost);
        			String jsonResult = inputStreamToString(
        					response.getEntity().getContent()).toString();
        			JSONObject object = new JSONObject(jsonResult);

        			Coordinate c = new Coordinate(0, 0, 0);

        			int id = Integer.parseInt(object.getString("id"));
        			double longitude = Double
        					.parseDouble(object.getString("longitude"));
        			double latitude = Double.parseDouble(object.getString("latitude"));
        			textView.setText(id + ": " + longitude + " - " + latitude);

        		} catch (JSONException e) {
        			e.printStackTrace();
        		} catch (ClientProtocolException e) {
        			e.printStackTrace();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
				
			}
        });
 
	}

	private StringBuilder inputStreamToString(InputStream is) {
		String rLine = "";
		StringBuilder answer = new StringBuilder();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		try {
			while ((rLine = rd.readLine()) != null) {
				answer.append(rLine);
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}
		return answer;
	}
}