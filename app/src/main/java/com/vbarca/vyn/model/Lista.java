package com.vbarca.vyn.model;



import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Lista extends Item implements Serializable {

    String uid;
    List<String> componentes;
    String sala;

    public Lista(String titulo) {
        super(titulo);
    }

    public Lista(){}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<String> componentes) {
        this.componentes = componentes;
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

        Lista lista = (Lista) o;
        return lista.uid == uid;
    }

    @Override
    public int hashCode() {
        return uid.hashCode();
    }

    @Override
    public String toString(){
        return titulo;
    }
}
