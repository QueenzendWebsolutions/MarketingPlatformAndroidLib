package com.queenzend.marketingplatformandroid.WebServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Sayali on 18/06/2016.
 */
public class HttpULRConnect1 {
    static String result;


    public static String getData(String uri) {
        result = exeuteHttp(uri);
        return result;
    }

    private static String exeuteHttp(String uri) {
        String responseString = "";

        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.connect();
            int responseCode = conn.getResponseCode();
            System.out.println("Inside HTTP_OK " + conn.getResponseCode());

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    responseString += line;
                }
            } else {
                responseString = "";
            }

            System.out.println("Inside responseString " + responseString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseString;
    }
}


