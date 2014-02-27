package com.example.citywalks;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
public class JSONParser {
	
	public static ArrayList<Coordinate> getCoordinate(String data) throws JSONException  {
		ArrayList<Coordinate> coList = new ArrayList<Coordinate>();
		Coordinate co = new Coordinate(0,0,0);

		JSONObject jObj = new JSONObject(data);
		JSONArray jArr = jObj.getJSONArray("coordinates");

		for(int i = 0; i < jArr.length(); i ++)
		{
			co.setCoordinateId(getInt("cid", jArr.getJSONObject(i)));
			co.setLatitude(getFloat("latitude", jArr.getJSONObject(i)));
			co.setLongitude(getFloat("longitude", jArr.getJSONObject(i)));			
			coList.add(co);
		}		
		return coList;
	}

	private static float  getFloat(String tagName, JSONObject jObj) throws JSONException {
		return (float) jObj.getDouble(tagName);
	}

	private static int  getInt(String tagName, JSONObject jObj) throws JSONException {
		return jObj.getInt(tagName);
	}

}