package com.example.citywalks;

import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ArrayList<Coordinate> coList = new  ArrayList<Coordinate>();
	TextView tv;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView tv = (TextView) findViewById(R.id.tv1);
		
		Button b = (Button) findViewById(R.id.btnDisplay);
		
		b.setOnClickListener(new OnClickListener() {			 
			@Override
			public void onClick(View arg0) {
				((TextView) findViewById(R.id.tv1)).setText("Loading coordinates...");
				JSONCoordinateTask task = new JSONCoordinateTask();
				task.execute(new String[]{});
			}
		});
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private class JSONCoordinateTask extends AsyncTask<String, Void, ArrayList<Coordinate>> {

		@Override
		protected ArrayList<Coordinate> doInBackground(String... params) {
			ArrayList<Coordinate> cList = new ArrayList<Coordinate>();
			CoordinateHttpClient a = new CoordinateHttpClient();
			String data =  a.getCoordinateData();
			try {
				cList = JSONParser.getCoordinate(data);
			} catch (JSONException e) {				
				e.printStackTrace();
			}
			
			return cList;
	}

		@Override
		protected void onPostExecute(ArrayList<Coordinate> cl) {		
			super.onPostExecute(cl);
			String cos = "";
			
			for(int i = 0; i < cl.size(); i++)
			{
				cos = cos+ "ID: " +cl.get(i).getCoordinateId()+" La: "+cl.get(i).getLatitude()+" Lo: "+cl.get(i).getLongitude();
			}
			((TextView) findViewById(R.id.tv1)).setText(cos);
		}
  }
}