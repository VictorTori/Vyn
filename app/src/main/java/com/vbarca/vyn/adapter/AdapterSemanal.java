package com.vbarca.vyn.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vbarca.vyn.R;
import com.vbarca.vyn.model.Calendario;
import com.vbarca.vyn.model.Comida;
import com.vbarca.vyn.model.Item;
import com.vbarca.vyn.model.Lista;
import com.vbarca.vyn.model.MenuSemanal;

import java.util.ArrayList;

public class AdapterSemanal extends BaseAdapter {

    private Context context;
    private ArrayList<Comida> listaItems;
    private LayoutInflater inflater;
    private boolean eleccion;

    public AdapterSemanal(Activity context, ArrayList<Comida> listaItems, boolean eleccion) {
        this.context = context;
        this.listaItems = listaItems;
        inflater = LayoutInflater.from(context);
        this.eleccion = eleccion;
    }

    static class ViewHolder {
        TextView dia;
        TextView comida;
        TextView alimento;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AdapterSemanal.ViewHolder holder = null;

        // Si la View es null se crea de nuevo
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lista_semanal, null);

            holder = new AdapterSemanal.ViewHolder();
            holder.dia = (TextView) convertView.findViewById(R.id.tvDia);
            holder.comida = (TextView) convertView.findViewById(R.id.tvComida);
            holder.alimento = (TextView) convertView.findViewById(R.id.tvAlimento);

            convertView.setTag(holder);
        }
        /*
         * En caso de que la View no sea null se reutilizará con los
         * nuevos valores
         */
        else {
            holder = (AdapterSemanal.ViewHolder) convertView.getTag();
        }
        Comida item = listaItems.get(position);

        holder.dia.setText(item.getDia());
        if(eleccion) {
            holder.comida.setText("Comida: ");
            holder.alimento.setText(item.getComida());
        }
        else {
            holder.comida.setText("Cena: ");
            holder.alimento.setText(item.getCena());
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return listaItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listaItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}
