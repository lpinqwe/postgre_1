package com.example.postgre_1;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

public class dataWriterAndManager {
    private final Object lock = new Object(); // Объект блокировки
    private JSONObject my_msg;
    private JSONArray my_data;
    public String name = "temporary";

    public dataWriterAndManager(String name) {
        this.name = name;
        this.my_msg = new JSONObject();
        this.my_data = new JSONArray();
        try {
            this.my_msg.put("username", this.name);
            Log.d("msgName", my_msg.toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void addJsonData(String event_name, float[] value, long time) {
        synchronized (lock) { // Синхронизация на уровне блока
            JSONObject temp = new JSONObject();
            try {
               // Log.d("JSON info", event_name);
                temp.put("name", event_name);
                temp.put("value", Arrays.toString(value));
                temp.put("time", time);
                this.my_data.put(temp);
               // Log.d("JSON data_inserted", temp.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public long msgCurrentTime() {
        return System.currentTimeMillis();
    }

    public JSONObject getJsonData() {
        synchronized (lock) { // Синхронизация на уровне блока
            try {
                this.my_msg.put("data", this.my_data.toString());
                return this.my_msg;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void clearData() {
        synchronized (lock) { // Синхронизация на уровне блока
            my_msg.remove("data");
            my_data = new JSONArray(); // Очистка данных
        }
    }
}
