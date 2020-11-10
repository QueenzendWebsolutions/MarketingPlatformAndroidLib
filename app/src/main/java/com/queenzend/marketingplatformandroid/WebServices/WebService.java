package com.queenzend.marketingplatformandroid.WebServices;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.queenzend.marketingplatformandroid.DaoClasses.EventDao;
import com.queenzend.marketingplatformandroid.DaoClasses.LoginDao;
import com.queenzend.marketingplatformandroid.DaoClasses.UploadDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

//import com.google.gson.Gson;

/**
 * Created by Ulhas on 11/25/16.
 */

public class WebService extends AbstractWorkerTask<String, Void, Object> {

    Context context;
    String response = "";
    ProgressDialog dialog;
    URL bURL;
    URL bURL1;
    String service = null;
    String service1 = null;
    String responseResult, URLString, JSONString;
    String responseResult1, URLString1, JSONString1;

    public WebService(TaskNotifier<Object> notifier, Context context, String actionName) {
        super(notifier, context);
        this.context = context;
    }

//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        dialog = new ProgressDialog(context);
//        dialog.setMessage("Please wait");
//        dialog.setCancelable(false);
//        dialog.show();
//    }

    @Override
    protected Object doInBackground(String... params) {

        service = params[0];


        if (service.equalsIgnoreCase("saveDeviceInfo")) {

            URLString = baseUrl;
            //http://k2key.in/marketing_plateform_CI/UserController/userRegistration
            System.out.println("Inside URLString  " + URLString);

            JSONString = getLoginJson();
        }
        service = params[0];


//        if (service.equalsIgnoreCase("event")) {
//
//            URLString = baseUrl1 + saveEvent;
//            System.out.println("Inside URLString1  " + URLString);
//
//            JSONString = getEventJson();
//        }
//        if (service.equalsIgnoreCase("AllEvent")) {
//
//            URLString = baseUrl1 + getAllEvent;
//            System.out.println("Inside URLString1  " + URLString);
//
//            JSONString = getDisplayJson();
//        }
//
//
//        if (service.equalsIgnoreCase("Upload")) {
//
//            URLString = baseUrl2 + uploadDoctorProfileImage;
//            System.out.println("Inside URLString1  " + URLString);
//            JSONString = getUpload();
//        }

        if (isNetworkAvailable()) {
            if (service.equalsIgnoreCase("login")) {
                System.out.println("Inside login  ");
                executePOSTService();

            }
            else if(service.equalsIgnoreCase("event")) {
                System.out.println("Inside event  ");
                executePOSTService();

            }
            else if(service.equalsIgnoreCase("AllEvent")) {
                System.out.println("Inside AllEvent  ");
                executeGETService();

            }

             else if (service.equalsIgnoreCase("Upload")) {
                    System.out.println("Inside Upload  ");
                    executePOSTService();


            } else {

                response = "No Internet Available.";
            }

            return response;
        }

        else{

            response = "No Internet Available.";
        }
        return response;
    }



    @Override
    protected void onPostExecute(Object result) {



        // TODO Auto-generated method stubFail
        super.onPostExecute(result);
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        if (result instanceof String) {
            if (getThrowable() == null) {

                getNotifier().onSuccess(result);

            } else {
                getNotifier().onError(getThrowable());
            }
        } else {
            getNotifier().onError(getThrowable());
        }
    }

    private Object executeGETService() {
        URL url = null;
        try {

            url = new URL(URLString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            String basicAuth = appPrefrence.getData(AppPreferences.Token);
            conn.setReadTimeout(50000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
//            if (url.toString().contains("faq")) {
//
//            } else {
//                conn.setRequestProperty("X-Auth-Token", basicAuth);
//            }

//            conn.setDoInput(false);
//            conn.setDoOutput(false);
            conn.connect();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private String executePOSTService() {
        try {
            bURL = new URL(URLString);
            System.out.println("Inside URLString  " + URLString);
            HttpURLConnection conn = (HttpURLConnection) bURL.openConnection();
            conn.setReadTimeout(50000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod("POST");


            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            os.write(JSONString.getBytes("UTF-8"));
            os.flush();
            os.close();
            os.close();
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.i("Url:-" + bURL, "ResponseCode:-" + responseCode);
            System.out.println("Inside responseCode  " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }



  private String getLoginJson() {
        LoginDao dao = LoginDao.getInstance();
        Gson gson = new Gson();
        String loginJSON = gson.toJson(dao);
        System.out.println("Inside LoginJSON  " + loginJSON);
        return loginJSON;
    }
   private String getEventJson() {
        EventDao resetPassword = EventDao.getInstance();
        Gson gson = new Gson();
        String eventJSON = gson.toJson(resetPassword);
        System.out.println("Inside eventJSON  " + eventJSON);
        return eventJSON;
    }
    private String getDisplayJson() {
        EventDao reset = EventDao.getInstance();
        Gson gson = new Gson();
        String displayEventJSON = gson.toJson(reset);
        System.out.println("Inside DisplayEventJSON  " + displayEventJSON);
        return displayEventJSON;
    }
    private String getUpload() {
        UploadDao Image_url = UploadDao.getInstance();
        Gson gson = new Gson();
        String UploadJSON = gson.toJson(Image_url);
        System.out.println("Inside UploadJSON  " + UploadJSON);
        return UploadJSON;
    }
}