package com.vbarca.vyn.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vbarca.vyn.R;
import com.vbarca.vyn.app.VynApp;
import com.vbarca.vyn.model.Usuario;

import java.io.Serializable;
import java.util.UUID;

public class Register extends Activity implements View.OnClickListener{

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button btRegistrar;
    Button btCancelar;
    EditText etUser;
    EditText etPass;
    EditText etPass2;
    EditText etEmail;
    Boolean correcto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btRegistrar = findViewById(R.id.btRegistrar);
        btCancelar = findViewById(R.id.btCancelar);
        etEmail = findViewById(R.id.etEmail);
        etUser = findViewById(R.id.etNombre);
        etPass = findViewById(R.id.etClave);
        etPass2 = findViewById(R.id.etConfClave);

        btCancelar.setOnClickListener(this);
        btRegistrar.setOnClickListener(this);
        correcto = true;


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btRegistrar){
            FirebaseApp.initializeApp(this);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();

            String uid = UUID.randomUUID().toString();
            String user = etUser.getText().toString();
            String pass = etPass.getText().toString();
            String pass2 = etPass2.getText().toString();
            String email = etEmail.getText().toString();
            String uidSala = UUID.randomUUID().toString();

            if(!pass.equals(pass2) || pass.equals("")){
                etPass.setText("");
                etPass2.setText("");
                Toast.makeText(this, "La contraseña tiene que ser la misma y no puede estar en blanco", Toast.LENGTH_SHORT).show();
                return;
            }
            if(user.equals("")){
                Toast.makeText(this, "Usuario no puede estar en blanco", Toast.LENGTH_SHORT).show();
                return;
            }
            if(!email.contains("@")){
                Toast.makeText(this, "Email tiene que ser un correo valido", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario usuario = new Usuario(pass,user,email,uid,uidSala);
            databaseReference.child("Usuario").child(usuario.getUid()).setValue(usuario);



            Intent intent = new Intent(this, RegisterOrLogin.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btCancelar){
            limpiarCampos();
        }
    }

    private void limpiarCampos() {

    }
}
