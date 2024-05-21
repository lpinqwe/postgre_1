package com.example.postgre_1;

import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

public class dataWriterAndManager extends AppCompatActivity {
    String saveData = "";
    JSONObject my_msg;
    JSONArray my_data;
    //String name="v0.0\n"+ Settings.Secure.ANDROID_ID;
    String name;
    String app_version;
    public dataWriterAndManager() {
        this.my_msg = new JSONObject();
        this.my_data = new JSONArray();
        try {
            this.my_msg.put("username", this.name);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

//    public dataWriterAndManager(String username) {
//        this.my_msg = new JSONObject();
//        this.my_data = new JSONArray();
//        try {
//            this.my_msg.put("username", username);
//            this.my_data.put(this.my_msg);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

    public void addJsonData(String type, float[] value, long time) {
        JSONObject temp = new JSONObject();
        try {
            Log.i("JSON info",type);
            temp.put("name",  type);
            temp.put("value", Arrays.toString(value));
            temp.put("time", time);
            this.my_data.put(temp);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public long msgCurrentTime() {
        return System.currentTimeMillis();
    }

    public String getData() {
        return this.saveData;
    }

    public JSONObject getJsonData(){
        try {
            this.my_msg.put("data",this.my_data.toString());
            return this.my_msg;
            //return this.my_data;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void clearData() {
        saveData = "";
        Log.i("info",this.my_data.toString());
    }


}
