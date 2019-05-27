package com.vbarca.vyn.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.vbarca.vyn.R;
import com.vbarca.vyn.adapter.AdapterFotos;
import com.vbarca.vyn.model.Base;
import com.vbarca.vyn.model.Foto;
import com.vbarca.vyn.model.Fotos;
import com.vbarca.vyn.util.Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class VistaFotos extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView lvFotos;
    List<Foto> fotos;
    AdapterFotos adapterFotos;
    Bitmap imagen;
    Base base;
    Fotos fotoClass;
    String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_fotos);

        lvFotos = findViewById(R.id.lvFotos);
        base = new Base(this);

        // Recogemos las fotos

        Intent intent = getIntent();
        fotoClass = (Fotos) intent.getSerializableExtra("fotos");
        titulo = intent.getStringExtra("titulo");
        fotos = fotoClass.getFotos();
        if(fotos == null){
            fotos = new ArrayList<>();
        } else {

        }

        ////////////////////////////////////////////////

        adapterFotos = new AdapterFotos(this,fotos);
        lvFotos.setAdapter(adapterFotos);
        lvFotos.setOnItemClickListener(this);
        adapterFotos.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_anadir_fotos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/");
                startActivityForResult(intent.createChooser(intent,"Seleccione un Aplicacion"),10);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,VynApp.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            Uri path = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                bitmap = bitmap.createScaledBitmap(bitmap,150,150,true);
            } catch (IOException e) {

            }
            byte[] byteFoto = Util.getBytes(bitmap);
            String fotoString = Base64.encodeToString(byteFoto,Base64.DEFAULT);
            byteFoto = Base64.decode(fotoString,Base64.DEFAULT);
            bitmap = Util.getBitmap(byteFoto);

            Foto foto = new Foto();
            foto.setImagen(fotoString);

            fotos.add(foto);
            fotoClass.setFotos(fotos);
            fotoClass.setTitulo(titulo);
            base.getDatabaseReference().child("Fotos").child(fotoClass.getUid()).setValue(fotoClass);
            adapterFotos.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Desea borrar esta imagen").setPositiveButton("Si",  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        fotos.remove(fotos.get(i));
                        fotoClass.setFotos(fotos);
                        fotoClass.setTitulo(titulo);
                        base.getDatabaseReference().child("Fotos").child(fotoClass.getUid()).setValue(fotoClass);
                        adapterFotos.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .show();
    }
}
