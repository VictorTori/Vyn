package com.vbarca.vyn.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vbarca.vyn.R;
import com.vbarca.vyn.app.VynApp;
import com.vbarca.vyn.model.Usuario;

public class Login extends Activity implements View.OnClickListener{

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    SharedPreferences usuarioAlmacenado;
    SharedPreferences.Editor editor;

    boolean correcto;
    EditText user;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioAlmacenado = getSharedPreferences("controlUsuario", Context.MODE_PRIVATE);
        editor = usuarioAlmacenado.edit();

        Button btLogear = findViewById(R.id.btLogear);
        btLogear.setOnClickListener(this);

        user = findViewById(R.id.etUsuario);
        password = findViewById(R.id.etClaveLogin);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btLogear){

            FirebaseApp.initializeApp(this);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference();


            databaseReference.child("Usuario").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    correcto = false;
                    Usuario usuarioPrueba;
                    for(DataSnapshot usuario : dataSnapshot.getChildren()){
                        usuarioPrueba = usuario.getValue(Usuario.class);

                        if(usuarioPrueba.getUser().equals(user.getText().toString()) && usuarioPrueba.getPassword().equals(password.getText().toString())){
                            correcto = true;

                            editor.putString("uid",usuarioPrueba.getUid());
                            editor.putString("user",usuarioPrueba.getUser());
                            editor.putString("pass",usuarioPrueba.getPassword());
                            editor.putString("email",usuarioPrueba.getEmail());
                            editor.putString("uidsala",usuarioPrueba.getUidSala());
                            editor.commit();

                            Intent intent = new Intent(Login.this, VynApp.class);
                            startActivity(intent);
                        }
                    }
                    if(!correcto) {
                        Toast.makeText(Login.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                        user.setText("");
                        password.setText("");
                    }
                    databaseReference.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
