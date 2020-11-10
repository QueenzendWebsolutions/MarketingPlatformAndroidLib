package com.queenzend.marketingplatform;

import android.content.Context;
import android.widget.Toast;

public class ToasterMessage {

    public static void s(Context c, String message){

        Toast.makeText(c,message, Toast.LENGTH_SHORT).show();
    }
    public static void c(Context c,String email){
        Toast.makeText(c,email, Toast.LENGTH_SHORT).show();
    }
    public static void d(Context c,String password){
        Toast.makeText(c,password, Toast.LENGTH_SHORT).show();
    }

}