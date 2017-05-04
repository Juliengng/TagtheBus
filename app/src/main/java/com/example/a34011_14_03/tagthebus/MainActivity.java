package com.example.a34011_14_03.tagthebus;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuAdapter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.a34011_14_03.tagthebus.R.id.listView;
import static com.example.a34011_14_03.tagthebus.R.id.parent;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    public ListView lv;

    ArrayList<HashMap<String, String>> BusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BusList = new ArrayList<>();
        lv = (ListView) findViewById(listView);
        lv.setOnItemClickListener(
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(MainActivity.this, Mockup2.class);
                        intent.putExtra("item", id);
                        startActivity(intent);
                    }
                }
        );
        new GetBus().execute();
    }

    private class GetBus extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Json Data is downloading ", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://barcelonaapi.marcpous.com/bus/nearstation/latlon/41.3985182/2.1917991/1.json";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONObject Data = jsonObj.getJSONObject("data");
                    JSONArray Buses = Data.getJSONArray("nearstations");

                    Log.d("debug", Buses.toString());
                    // looping through All buses
                    for (int i = 0; i < Buses.length(); i++) {
                        JSONObject c = Buses.getJSONObject(i);
                        String id = c.getString("id");
                        String street_name = c.getString("street_name");
                        String city = c.getString("city");
                        String utm_x = c.getString("utm_x");
                        String lat = c.getString("lat");
                        String lon = c.getString("lon");
                        String furniture = c.getString("furniture");
                        String buses = c.getString("buses");
                        String distance = c.getString("distance");


                        // tmp hash map for single bus
                        HashMap<String, String> Car = new HashMap<>();

                        // adding each child node to HashMap key => value
                        Car.put("id", id);
                        Car.put("street_name", street_name);
                        Car.put("city", city);
                        Car.put("utm_x", utm_x);
                        Car.put("lat", lat);
                        Car.put("lon", lon);
                        Car.put("furniture", furniture);
                        Car.put("buses", buses);
                        Car.put("distance", distance);

                        // adding bus to bus list
                        BusList.add(Car);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, BusList,
                    R.layout.buslist, new String[]{"city", "street_name"},
                    new int[]{R.id.city, R.id.street_name});
            lv.setAdapter(adapter);}

        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(MainActivity.this, Mockup2.class);
            intent.putExtra("item", id);
            startActivity(intent);
        }
    }
}








