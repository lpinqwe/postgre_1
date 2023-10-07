
package com.example.postgre_1;


import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClientPart2 {
    // data = read file bytes
    // url ="127.0.0.1"
    // timeout=10
    public  <T> Pair<Integer, T> httpPostCommand1(byte[] data, String url, int timeout) throws IOException {
        Log.i("WIFI","0");
        URL _url = new URL(url);
        Log.i("WIFI","1");
        HttpURLConnection con = (HttpURLConnection) _url.openConnection();
        Log.i("WIFI","2");
        con.setRequestMethod("POST");
        Log.i("WIFI","3");
        con.setRequestProperty("Content-Type", "application/json");
        Log.i("WIFI","4");
        con.setRequestProperty("Accept", "application/json");
        con.setConnectTimeout(timeout);
        con.setDoOutput(true);
        con.setAllowUserInteraction(false);
        con.setDefaultUseCaches(false);
        con.setUseCaches(false);
        try (OutputStream os = con.getOutputStream()) {

            os.write(data, 0, data.length);
        }
        int responseCode = con.getResponseCode();
        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(
                             (100 <= responseCode && responseCode <= 399) ? con.getInputStream()
                                     : con.getErrorStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return new Pair(responseCode,response.toString());
        }
    }
}
