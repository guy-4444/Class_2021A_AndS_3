package com.classy.class_2021a_ands_3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyServerCall {

    public interface CallBack_Data {
        void dataReady(String data);
    }

    public static void downloadData(String API, CallBack_Data callBack_data) {
        new Thread(new Runnable() {
            public void run() {
                String data = "";

                try {
                    URL url = new URL(API); //My text file location
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(60000);
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String str;
                    while ((str = in.readLine()) != null) {
                        data += str;
                    }
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (callBack_data != null) {
                    callBack_data.dataReady(data);
                }
            }
        }).start();
    }

}
