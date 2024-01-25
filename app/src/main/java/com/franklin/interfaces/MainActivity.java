package com.franklin.interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.franklin.interfaces.activity.ui.login.Login;
import com.franklin.interfaces.activity.ui.register.Register;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(l-> abrirLogin());
        Button btnRegister = findViewById(R.id.btnRegistrar);
        btnRegister.setOnClickListener(l-> abrirRegistro());
    }
    public void abrirLogin(){
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }
    public void abrirRegistro(){
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }
}