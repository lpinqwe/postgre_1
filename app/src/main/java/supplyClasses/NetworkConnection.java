package supplyClasses;

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
    // Лучше установить доменное имя или внешний IP-адрес сервера вместо использования локального IP
    //String URL = "http://192.168.1.163:5000"; // Замените "your_domain_or_ip" на ваш домен или IP-адрес
    //String URL = "http://192.168.99.107:5000";
    String URL = new ConfigClass().URL; // Замените "your_domain_or_ip" на ваш домен или IP-адрес

    JSONObject postData;

    public void sndFunc(JSONObject data, Context context) throws IOException {
        String postUrl = this.URL;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        this.postData = data;
        Log.i("info json my postData",this.postData.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
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
