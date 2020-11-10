package com.queenzend.marketingplatformandroid.DaoClasses;

import java.io.Serializable;

public class LoginDao implements Serializable {
    String mail_id, password;

    private static LoginDao instance = null;
    public static LoginDao getInstance() {
        if (instance == null) {
            instance = new LoginDao();
        }
        return instance;
    }
    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}




