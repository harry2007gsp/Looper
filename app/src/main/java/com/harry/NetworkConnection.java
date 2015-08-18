package com.harry;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Harry on 8/15/15.
 */
public class NetworkConnection {
    ArrayList<Model> list = new ArrayList<>();
    public TestHandler testHandler, testHandler2 ;

    public void handlerThreadMethod() {
        HandlerThread handlerThread = new HandlerThread("HandlerThread");
        handlerThread.start();

// Create a handler attached to the HandlerThread's Looper
        testHandler2 = new TestHandler() {
            @Override
            public void handleMessage(Message msg) {
                Log.d("test", "HANDLER THREAD");
            }
        };
    }

    public void fetchData(final MainActivity.MyHandler myHandler) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("test", "first run");
//                Looper.prepare();
//                testHandler = new TestHandler();
//
//
//                NetworkConnection networkConnection = new NetworkConnection();
//                String string = networkConnection.downloadWithURLConnecton();
//                list = networkConnection.parseData(string);
//                for (Model m : list) {
//                    Log.d("test", m.getPlace());
//                }
//                Runnable myRunnable = new MainActivity.MyRunnable(list.get(0).getPlace());
//                myHandler.post(myRunnable);
//                Looper.loop();
//
//            }
//
//        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "first run");
                Looper.prepare();
                testHandler = new TestHandler();

                NetworkConnection networkConnection = new NetworkConnection();
                String string = networkConnection.downloadWithURLConnecton();
                list = networkConnection.parseData(string);
//                for (Model m : list) {
//                    Log.d("test", m.getPlace());
//                }
                Runnable myRunnable = new MainActivity.MyRunnable(list.get(0).getPlace());
                myHandler.post(myRunnable);
                Looper.loop();

            }

        }).start();
    }

//will give String
    public String downloadWithURLConnecton() {
        String url = "http://api.geonames.org/postalCodeLookupJSON?postalcode=6600&country=AT&username=demo";
        InputStream inputStream = null;
        StringBuilder b = new StringBuilder();


        try {
            URL url1 = new URL(url);
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
                httpURLConnection.setRequestMethod("GET");
                inputStream = httpURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while ((line = br.readLine()) != null) {
                    b.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return b.toString();
    }

//will give list of Model
    public ArrayList<Model> parseData(String string) {
        ArrayList<Model> modelList = new ArrayList<>();
        Log.d("test", "got in parse data");
        Log.d("test", string);
        try {
            JSONObject object = new JSONObject(string);
            JSONArray array = object.optJSONArray("postalcodes");
            for (int i = 0; i < array.length(); i++) {
                JSONObject arrayObject = array.optJSONObject(i);
                String country = arrayObject.optString("countryCode");
                String place = arrayObject.optString("placeName");
                double longitude = arrayObject.optDouble("lng");
                double lattitude = arrayObject.optDouble("lat");
                Model model = new Model(country, place, longitude, lattitude);
                modelList.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modelList;
    }

    static class TestHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d("test", "test handler");
        }
    }
}
