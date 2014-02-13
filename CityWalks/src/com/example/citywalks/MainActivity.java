package com.example.citywalks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends Activity {
	
    Button btnDisplay;
    
    // Progress Dialog
    private ProgressDialog pDialog;
 
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    
    ArrayList<HashMap<String, String>> coList;
 
    // url to get all products list
    private static String url_all_products = "http://192.168.1.102/cwdb/get_all_coordinates.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_COORDINATES = "coordinates";
    private static final String TAG_CID = "cid";
    private static final String TAG_LAT = "Latitude";
    private static final String TAG_LONG = "Longitude";
 
    // products JSONArray
    JSONArray coordinates = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Hashmap for ListView
        coList = new ArrayList<HashMap<String, String>>();
 
        // Loading products in Background Thread
        //new LoadAllProducts().execute();
 
        // Get listview
        //ListView lv = getListView();
        
        btnDisplay = (Button) findViewById(R.id.btnDisplay);        
        btnDisplay.setOnClickListener(new View.OnClickListener() {
        	 
            @Override
            public void onClick(View view) {
            	
            	
            	getCOs();
            	
            	
 
            }
        });        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }   
    
    /**
     * getting All products from url
     * */
    public void getCOs() {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);

        // Check your log cat for JSON reponse
        Log.d("All Products: ", json.toString());

        try {
            // Checking for SUCCESS TAG
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                // products found
                // Getting Array of Products
                coordinates = json.getJSONArray(TAG_COORDINATES);

                // looping through All Products
                for (int i = 0; i < coordinates.length(); i++) {
                    JSONObject c = coordinates.getJSONObject(i);

                    // Storing each json item in variable
                    String id = c.getString(TAG_CID);
                    String latitude = c.getString(TAG_LAT);
                    String longitude = c.getString(TAG_LONG);
                    String aaa = id + " " + latitude + " " +  longitude;
                    // creating new HashMap
                    HashMap<String, String> map = new HashMap<String, String>();

                    // adding each child node to HashMap key => value
                    map.put(TAG_CID, id);
                    map.put(TAG_LAT, latitude);
                    map.put(TAG_LONG, longitude);
                    ((TextView)findViewById (R.id.tv1)).setText (aaa);
                   
                    // adding HashList to ArrayList
                    coList.add(map);
                }
            } else {
                // no products found
                // Launch Add New product Activity               
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //return null;
    }

    /**
     * After completing background task Dismiss the progress dialog
     * **/
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after getting all products
        //pDialog.dismiss();
        // updating UI from Background Thread
        runOnUiThread(new Runnable() {
            public void run() {
                /**
                 * Updating parsed JSON data into ListView
                 * */
                ListAdapter adapter = new SimpleAdapter(
                		MainActivity.this, coList,
                        R.layout.list_item, new String[] { TAG_CID,TAG_LAT,TAG_LONG},
                        new int[] { R.id.cid, R.id.name });
                // updating listview
                //setListAdapter(adapter);
            }
        });

    }
    
}
