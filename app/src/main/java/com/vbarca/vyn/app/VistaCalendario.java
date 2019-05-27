package com.vbarca.vyn.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vbarca.vyn.R;
import com.vbarca.vyn.model.Base;
import com.vbarca.vyn.model.Calendario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class VistaCalendario extends Activity implements View.OnClickListener{

    EditText etCalendar;
    EditText etHora;
    EditText etEvento;

    Button btCalendar;
    Button btHora;
    Button btAnadir;

    ListView lvCalendar;

    List<String> eventos;

    Base base;

    Calendario calendario;
    String titulo;
    String fecha;
    String horas;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_calendario);

        base = new Base(this);

        // Recogemos el calendario

        Intent intent = getIntent();
        calendario = (Calendario) intent.getSerializableExtra("calendario");
        if(calendario.getEvento() == null)
            calendario.setEvento(new ArrayList<String>());

        ////////////////////////////////////////////////

        titulo = intent.getStringExtra("titulo");
        eventos = calendario.getEvento();

        // Inicializamos

        etCalendar = findViewById(R.id.etCalendar);
        etHora = findViewById(R.id.etHora);
        etEvento = findViewById(R.id.etEvento);

        btCalendar = findViewById(R.id.btCalendario);
        btHora = findViewById(R.id.btHora);
        btAnadir = findViewById(R.id.btAnadirCalendar);
        btCalendar.setOnClickListener(this);
        btHora.setOnClickListener(this);
        btAnadir.setOnClickListener(this);

        lvCalendar = findViewById(R.id.lvCalendar);
        eventos = calendario.getEvento();

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, eventos);
        lvCalendar.setAdapter(adapter);
        registerForContextMenu(lvCalendar);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btCalendario){
            final Calendar calendar = Calendar.getInstance();
            int dia = calendar.get(Calendar.DAY_OF_MONTH);
            int mes = calendar.get(Calendar.MONTH);
            int anyo = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                    fecha = (dayOfMonth + "/" + monthOfYear + "/" + year);
                    etCalendar.setText(fecha);
                    }
                }
                ,dia,mes,anyo);
	            datePickerDialog.show();
            }
        if(v.getId() == R.id.btHora){
            final Calendar calendar = Calendar.getInstance();
            int hora= calendar.get(Calendar.HOUR_OF_DAY);
            int minutos = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    horas = (hourOfDay + ":" + minute);
                    etHora.setText(horas);
                    }
                }
	            ,hora,minutos,false);
	            timePickerDialog.show();
            }
        if(v.getId() == R.id.btAnadirCalendar){
            if(etHora.getText().toString().equals("") || etCalendar.getText().toString().equals("") || etEvento.getText().toString().equals("")){
                Toast.makeText(this, "Todos los campos deben estar rellenos", Toast.LENGTH_SHORT).show();
                return;
            }

            calendario.getEvento().add(fecha + " - " + horas + " | " +etEvento.getText().toString());
            calendario.setTitulo(titulo);
            base.getDatabaseReference().child("Calendario").child(calendario.getUid()).setValue(calendario);
            adapter.notifyDataSetChanged();

            etEvento.setText("");
            etHora.setText("");
            etCalendar.setText("");
        }
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
                eventos.remove(itemSeleccionado);
                calendario.setTitulo(titulo);
                base.getDatabaseReference().child("Calendario").child(calendario.getUid()).setValue(calendario);
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
