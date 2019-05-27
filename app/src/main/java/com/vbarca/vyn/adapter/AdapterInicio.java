package com.vbarca.vyn.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vbarca.vyn.R;
import com.vbarca.vyn.model.Calendario;
import com.vbarca.vyn.model.Fotos;
import com.vbarca.vyn.model.Item;
import com.vbarca.vyn.model.Lista;
import com.vbarca.vyn.model.MenuSemanal;

import java.util.ArrayList;

public class AdapterInicio extends BaseAdapter {

    private Context context;
    private ArrayList<Item> listaItems;
    private LayoutInflater inflater;

    public AdapterInicio(Activity context, ArrayList<Item> listaItems) {
        this.context = context;
        this.listaItems = listaItems;
        inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        ImageView foto;
        TextView titulo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        // Si la View es null se crea de nuevo
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lista_items, null);

            holder = new ViewHolder();
            holder.foto = (ImageView) convertView.findViewById(R.id.imagenAdapter);
            holder.titulo = (TextView) convertView.findViewById(R.id.textoImagen);

            convertView.setTag(holder);
        }
        /*
         * En caso de que la View no sea null se reutilizará con los
         * nuevos valores
         */
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = listaItems.get(position);
        if(item instanceof Lista) {
            holder.foto.setImageResource(R.drawable.ic_lista_negro);
        } else if (item instanceof MenuSemanal) {
            holder.foto.setImageResource(R.drawable.ic_menu_comida_negro);
        } else if (item instanceof Calendario){
            holder.foto.setImageResource(R.drawable.ic_calendario_negro);
        } else if (item instanceof Fotos){
            holder.foto.setImageResource(R.drawable.ic_fotos);
        }

        holder.titulo.setText(item.getTitulo());

        return convertView;
    }

    @Override
    public int getCount() {
        return listaItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listaItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
