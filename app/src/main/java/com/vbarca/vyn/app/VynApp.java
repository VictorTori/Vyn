package com.vbarca.vyn.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.vbarca.vyn.R;
import com.vbarca.vyn.model.Base;
import com.vbarca.vyn.model.Calendario;
import com.vbarca.vyn.model.Comida;
import com.vbarca.vyn.model.Foto;
import com.vbarca.vyn.model.Fotos;
import com.vbarca.vyn.model.Lista;
import com.vbarca.vyn.model.MenuSemanal;
import com.vbarca.vyn.model.Usuario;
import com.vbarca.vyn.register.RegisterOrLogin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VynApp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, InicioFragment.OnFragmentInteractionListener, PerfilFragment.OnFragmentInteractionListener
        ,ContactoFragment.OnFragmentInteractionListener,AjustesFragment.OnFragmentInteractionListener, View.OnClickListener {

    AlertDialog dialog;
    Base base;
    ListView lvLista;
    ArrayAdapter<Lista> adapter;
    List<Lista> lista;
    SharedPreferences usuarioAlmacenado;
    Usuario usuarioPrincipal;
    SharedPreferences.Editor editor;
    TextView correo;
    TextView usuarioTf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vyn_app);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuarioAlmacenado = getSharedPreferences("controlUsuario", Context.MODE_PRIVATE);
        editor = usuarioAlmacenado.edit();

        String uid = usuarioAlmacenado.getString("uid","");
        String user = usuarioAlmacenado.getString("user","");
        String pass = usuarioAlmacenado.getString("pass","");
        String email = usuarioAlmacenado.getString("email","");
        String uidsala = usuarioAlmacenado.getString("uidsala","");

        if(user.equals("")){
            Intent intent = new Intent(this,RegisterOrLogin.class);
            startActivity(intent);
        } else {
            usuarioPrincipal = new Usuario(pass,user,email,uid,uidsala);
        }

        Fragment miFragmento = new InicioFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_vyn,miFragmento).commit();

        base = new Base(this);

        FloatingActionButton btAnadir = findViewById(R.id.btAnadirCalendar);
        btAnadir.setOnClickListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.vyn_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFragment = null;
        boolean fragmentSeleccionado = false;

        if (id == R.id.inicio) {
            fragmentSeleccionado = true;
            miFragment = new InicioFragment();
        } else if (id == R.id.contacto) {
            fragmentSeleccionado = true;
            miFragment = new ContactoFragment();
        } else if (id == R.id.perfil) {
            fragmentSeleccionado = true;
            miFragment = new PerfilFragment();
        } else if (id == R.id.cerrar_sesion){

            editor.putString("uid","");
            editor.putString("user","");
            editor.putString("pass","");
            editor.putString("email","");
            editor.putString("uidsala","");
            editor.commit();

            Intent intent = new Intent(this, RegisterOrLogin.class);
            startActivity(intent);
        }

        if(fragmentSeleccionado == true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_vyn,miFragment).commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btAnadirCalendar:
                crearDialogo();
                break;
            case R.id.lista:
                String uid = UUID.randomUUID().toString();
                List<String> componentes = new ArrayList<>();
                Lista lista = new Lista("Listado 1");
                lista.setSala(usuarioPrincipal.getUidSala());
                lista.setUid(uid);
                lista.setComponentes(componentes);
                base.getDatabaseReference().child("Lista").child(lista.getUid()).setValue(lista);
                dialog.dismiss();
                break;
            case R.id.calendario:
                String uidC = UUID.randomUUID().toString();
                Calendario calendario = new Calendario("Calendario 2019");
                calendario.setUid(uidC);
                calendario.setSala(usuarioPrincipal.getUidSala());
                base.getDatabaseReference().child("Calendario").child(calendario.getUid()).setValue(calendario);
                dialog.dismiss();
                break;
            case R.id.galeria:
                String uidF = UUID.randomUUID().toString();
                Fotos fotos = new Fotos("Fotos 2019");
                List<Foto> fotosList = new ArrayList<>();
                fotos.setUid(uidF);
                fotos.setFotos(fotosList);
                fotos.setSala(usuarioPrincipal.getUidSala());
                base.getDatabaseReference().child("Fotos").child(fotos.getUid()).setValue(fotos);
                dialog.dismiss();
                break;
            case R.id.menu_comida:
                String id = UUID.randomUUID().toString();
                ArrayList<Comida> menuSemanal = new ArrayList<>();
                String [] nombres = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"};
                for(String dia : nombres){
                    Comida comida = new Comida();
                    comida.setCena(" ");
                    comida.setComida(" ");
                    comida.setDia(dia);
                    menuSemanal.add(comida);
                }
                MenuSemanal menu = new MenuSemanal("Menu Mayo");
                menu.setSemana(menuSemanal);
                menu.setUid(id);
                menu.setSala(usuarioPrincipal.getUidSala());
                base.getDatabaseReference().child("MenuSemanal").child(menu.getUid()).setValue(menu);
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    private void crearDialogo() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(VynApp.this,R.style.CustomDialog);
        View mView = getLayoutInflater().inflate(R.layout.seleccion_actividad,null);
        mBuilder.setView(mView);
        mView.setBackgroundColor(Color.TRANSPARENT);

        Button btLista = mView.findViewById(R.id.lista);
        btLista.setOnClickListener(this);
        Button btCalendario = mView.findViewById(R.id.calendario);
        btCalendario.setOnClickListener(this);
        Button btMenu = mView.findViewById(R.id.menu_comida);
        btMenu.setOnClickListener(this);
        Button btGaleria = mView.findViewById(R.id.galeria);
        btGaleria.setOnClickListener(this);

        dialog = mBuilder.create();
        dialog.show();
    }
}
