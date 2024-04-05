package com.example.postgre_1;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import java.io.IOException;

public class NetworkConnection {
    //private String android_id = Secure.getString(getContext().getContentResolver(), Secure.ANDROID_ID);
    String URL = "http://192.168.1.163:5000";
    JSONObject postData;

    public void sndFunc(JSONObject data, Context context) throws IOException {
        //String postUrl = "http://192.168.99.106:80";//todo use system environment
        //String postUrl = "http://192.168.1.197:5000";
        String postUrl = this.URL;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        this.postData = data;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(getApplicationContext(), "Response: "+response, Toast.LENGTH_LONG).show();
                Log.i("WIFI_VOLLEY", "Response: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERRROR", String.valueOf(error));
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
