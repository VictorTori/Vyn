package com.vbarca.vyn.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.vbarca.vyn.R;
import com.vbarca.vyn.model.Base;
import com.vbarca.vyn.model.Lista;

import java.util.ArrayList;
import java.util.List;

public class VistaLista extends Activity implements View.OnClickListener{

    AlertDialog dialog;
    List<String> lista;
    ListView listView;
    Lista listaClass;
    Button btAnadir;
    Button btAnadirLista;
    EditText etTarea;
    ArrayAdapter<String> adapter;
    Base base;
    String titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_lista);

        base = new Base(this);

        // Recogemos la lista

        Intent intent = getIntent();
        listaClass = (Lista) intent.getSerializableExtra("lista");
        titulo = intent.getStringExtra("titulo");
        if(listaClass.getComponentes() == null)
            listaClass.setComponentes(new ArrayList<String>());
        lista = listaClass.getComponentes();

        ////////////////////////////////////////////////

        listView = findViewById(R.id.listaListas);
        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, lista);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
        adapter.notifyDataSetChanged();

        btAnadir = findViewById(R.id.btAnadirCalendar);
        btAnadir.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btAnadirCalendar){
            crearDialog();
        }
        if(view.getId() == R.id.btAnadir_lista){
            String texto = etTarea.getText().toString();
            listaClass.getComponentes().add(texto);
            listaClass.setTitulo(titulo);
            base.getDatabaseReference().child("Lista").child(listaClass.getUid()).setValue(listaClass);
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        }

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_contextual_borrar, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int itemSeleccionado = info.position;

        switch (item.getItemId()) {
            case R.id.action_eliminar:
                lista.remove(itemSeleccionado);
                listaClass.setTitulo(titulo);
                base.getDatabaseReference().child("Lista").child(listaClass.getUid()).setValue(listaClass);
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,VynApp.class);
        startActivity(intent);
    }
}
