package com.vbarca.vyn.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vbarca.vyn.R;

public class RegisterOrLogin extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_or_login);

        Button btEntrar = findViewById(R.id.btEntrar);
        Button btRegistrarLog = findViewById(R.id.btRegistrarLog);
        btEntrar.setOnClickListener(this);
        btRegistrarLog.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btEntrar){
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.btRegistrarLog){
            Intent intent = new Intent(this,Register.class);
            startActivity(intent);
        }
    }
}
