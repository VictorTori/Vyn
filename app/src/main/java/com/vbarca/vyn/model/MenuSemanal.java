package com.vbarca.vyn.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuSemanal extends Item implements Serializable {

    ArrayList<Comida> semana;
    String uid;
    String sala;

    public MenuSemanal(){}

    public MenuSemanal(String titulo) {
        super(titulo);
    }

    public ArrayList<Comida> getSemana() {
        return semana;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public void setSemana(ArrayList<Comida> semana) {
        this.semana = semana;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MenuSemanal menu = (MenuSemanal) o;
        return menu.uid ==uid;
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
