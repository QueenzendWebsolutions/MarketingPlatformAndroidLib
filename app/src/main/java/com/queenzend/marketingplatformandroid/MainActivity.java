package com.queenzend.marketingplatformandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button btn_savedeviceId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_savedeviceId = (Button) findViewById(R.id.savedeviceId);
        btn_savedeviceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDeviceIdforTesting();
            }
        });
    }
    public void saveDeviceIdforTesting() {

        class UserLogin extends AsyncTask<String, Void, String> {
            String loginUrl = "http://k2key.in/marketing_plateform_CI/UserController/saveDeviceInfo";
            String server_response;
            ProgressDialog prgDialog = new ProgressDialog(MainActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                prgDialog.setMessage("Please wait...");
                prgDialog.show();
            }

            @Override
            protected String doInBackground(String... strings) {
                URL url;
                HttpURLConnection urlConnection;
                try {
                    url = new URL(loginUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);

                    OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());

                    try {

                        JSONObject object = new JSONObject();
                        object.put("device_id", "jayashree11");

                        wr.write(object.toString());
                        //Toast.makeText(getApplicationContext(), "Object"+object.toString(), Toast.LENGTH_LONG).show();
                        Log.d("JSON INPUT", object.toString());
                        wr.flush();
                        wr.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                        // Toast.makeText(getApplicationContext(), "Error 1:"+e.toString(), Toast.LENGTH_LONG).show();

                    }
                    urlConnection.connect();

                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        server_response = readStream(urlConnection.getInputStream());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                prgDialog.hide();
                Log.d("Response", "" + server_response);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(server_response.toString());
                    if (jsonObject.has("res_code")) {
                        if ((jsonObject.getString("res_code").contains("2"))) {

                            Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
                            System.out.println("Invalid Login");

                        } else  if ((jsonObject.getString("res_code").contains("1"))) {

                            System.out.println("Inside onSuccess response     " + jsonObject.getString("res_data"));

                            Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();

                            System.out.println("successfully Login......");
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
                            System.out.println("Invalid Login");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            private String readStream(InputStream inputStream) {
                BufferedReader reader = null;
                StringBuffer response = new StringBuffer();
                try {
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return response.toString();
            }
        }
        UserLogin ul = new UserLogin();
        ul.execute();
    }
}