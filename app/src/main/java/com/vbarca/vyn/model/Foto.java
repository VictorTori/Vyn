package com.vbarca.vyn.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Foto implements Serializable {

    String imagen;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
