package com.example.postgre_1;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FileManager1 extends AppCompatActivity {
    String saveData = "";
    JSONObject my_msg;
    JSONArray my_data;

    public FileManager1() {
        this.my_msg = new JSONObject();
        this.my_data = new JSONArray();
        try {
            this.my_msg.put("username", "user");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public FileManager1(String username) {
        this.my_msg = new JSONObject();
        this.my_data = new JSONArray();
        try {
            this.my_msg.put("username", username);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public void addData(String dataToSaveAndSend) {
        saveData = saveData + dataToSaveAndSend;
    }

    public void addJsonData(String type, String value, String time) {
        JSONObject temp = new JSONObject();
        try {
            Log.i("JSON info",type);
            temp.put("name", type);
            temp.put("value", value);
            temp.put("time", time);
            this.my_data.put(temp);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String msgCurrentTime() {
        return String.valueOf((System.currentTimeMillis()));
    }

    public String getData() {
        return this.saveData;
    }

    public JSONObject getJsonData(){
        try {
            this.my_msg.put("data",this.my_data.toString());
            return this.my_msg;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void clearData() {
        saveData = "";
        Log.i("info",this.my_data.toString());
    }


}
