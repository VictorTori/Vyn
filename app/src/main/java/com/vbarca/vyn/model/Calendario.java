package com.vbarca.vyn.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Calendario extends Item implements Serializable {

    String uid;
    String sala;
    List<String> evento;

    public Calendario (){

    }

    public Calendario(String titulo) {
        super(titulo);
    }

    public List<String> getEvento() {
        return evento;
    }

    public void setEvento(List<String> evento) {
        this.evento = evento;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Calendario calendario = (Calendario) o;
        return calendario.uid == uid;
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }
}
