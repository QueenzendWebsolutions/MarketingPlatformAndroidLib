package com.queenzend.marketingplatformandroid.DaoClasses;

import java.io.Serializable;

public class RegistrationDao implements Serializable {

    String name, contact, mail_id, password;

    private static RegistrationDao instance = null;
    public static RegistrationDao getInstance() {
        if (instance == null) {
            instance = new RegistrationDao();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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
