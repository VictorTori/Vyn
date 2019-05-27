package com.vbarca.vyn.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String uid;
    private String user;
    private String email;
    private String password;
    private String uidSala;

    public Usuario(){}

    public Usuario(String pass,String user,String email,String uid,String uidSala){
        this.user = user;
        this.email = email;
        this.password = pass;
        this.uid = uid;
        this.uidSala = uidSala;
    }

    public String getUidSala() {
        return uidSala;
    }

    public void setUidSala(String uidSala) {
        this.uidSala = uidSala;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
