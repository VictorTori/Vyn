package com.vbarca.vyn.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

public class Fotos extends Item implements Serializable {

    String uid;
    String sala;
    List<Foto> fotos;

    public Fotos(String uid, String sala, List<Foto> fotos){
        this.uid = uid;
        this.sala = sala;
        this.fotos = fotos;
    }

    public Fotos(){
    }


    public Fotos(String titulo) { super(titulo); }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }
}
