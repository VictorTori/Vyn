package com.vbarca.vyn.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vbarca.vyn.R;
import com.vbarca.vyn.model.Calendario;
import com.vbarca.vyn.model.Foto;
import com.vbarca.vyn.model.Fotos;
import com.vbarca.vyn.model.Item;
import com.vbarca.vyn.model.Lista;
import com.vbarca.vyn.model.MenuSemanal;
import com.vbarca.vyn.util.Util;

import java.util.ArrayList;
import java.util.List;

public class AdapterFotos extends BaseAdapter {

    private Context context;
    private List<Foto> listaItems;
    private LayoutInflater inflater;

    public AdapterFotos(Activity context, List<Foto> listaItems) {
        this.context = context;
        this.listaItems = listaItems;
        inflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        ImageView foto;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        AdapterFotos.ViewHolder holder = null;

        // Si la View es null se crea de nuevo
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lista_fotos, null);

            holder = new AdapterFotos.ViewHolder();
            holder.foto = (ImageView) convertView.findViewById(R.id.imagenFotos);

            convertView.setTag(holder);
        }
        /*
         * En caso de que la View no sea null se reutilizará con los
         * nuevos valores
         */
        else {
            holder = (AdapterFotos.ViewHolder) convertView.getTag();
        }

        Foto item = listaItems.get(position);

        String fotoString = item.getImagen();
        byte[] fotoBytes = Base64.decode(fotoString,Base64.DEFAULT);
        Bitmap bitmap = Util.getBitmap(fotoBytes);

        holder.foto.setImageBitmap(bitmap);

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
