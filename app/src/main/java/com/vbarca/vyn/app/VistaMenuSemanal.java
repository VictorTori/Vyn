package com.vbarca.vyn.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.vbarca.vyn.R;
import com.vbarca.vyn.adapter.AdapterSemanal;
import com.vbarca.vyn.model.Base;
import com.vbarca.vyn.model.Comida;
import com.vbarca.vyn.model.Lista;
import com.vbarca.vyn.model.MenuSemanal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VistaMenuSemanal extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {

    AlertDialog dialog;
    AdapterSemanal adapter;
    ArrayList<Comida> comida;
    ListView lvMenu;
    ImageView sol;
    ImageView luna;
    MenuSemanal menuClass;
    String titulo;
    boolean eleccion;
    Button btAnadirLista;
    EditText etTarea;
    Comida com;
    Base base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_menu_semanal);

        base = new Base(this);

        sol = findViewById(R.id.btSol);
        luna = findViewById(R.id.btLuna);
        sol.setOnClickListener(this);
        luna.setOnClickListener(this);

        // Recogemos el menu semanal

        Intent intent = getIntent();
        menuClass = (MenuSemanal) intent.getSerializableExtra("menuSemanal");
        titulo = intent.getStringExtra("titulo");
        comida = menuClass.getSemana();

        ////////////////////////////////////////////////

        lvMenu = findViewById(R.id.lvMenu);
        lvMenu.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,VynApp.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btSol){
            eleccion = true;
            adapter = new AdapterSemanal(this,comida,eleccion);
            lvMenu.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        if(view.getId() == R.id.btLuna){
            eleccion = false;
            adapter = new AdapterSemanal(this,comida,eleccion);
            lvMenu.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        if(view.getId() == R.id.btAnadir_lista){
            String texto = etTarea.getText().toString();
            if(eleccion){
                com.setComida(texto);
                menuClass.setTitulo(titulo);
                base.getDatabaseReference().child("MenuSemanal").child(menuClass.getUid()).setValue(menuClass);
            } else {
                com.setCena(texto);
                menuClass.setTitulo(titulo);
                base.getDatabaseReference().child("MenuSemanal").child(menuClass.getUid()).setValue(menuClass);
            }
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        com = comida.get(i);
        crearDialog();

    }

    private void crearDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this,R.style.CustomDialog);
        View mView = getLayoutInflater().inflate(R.layout.lista_anadir,null);
        mBuilder.setView(mView);

        btAnadirLista = mView.findViewById(R.id.btAnadir_lista);
        btAnadirLista.setOnClickListener(this);
        etTarea = mView.findViewById(R.id.etTarea);

        dialog = mBuilder.create();
        dialog.show();
    }
}
