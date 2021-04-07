package com.example.chalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    public void IrSelecciones(View view)
    {
        Intent seleccion=new Intent(this,activity_selecion.class);
        seleccion.putExtra("nombres","Josue");
        seleccion.putExtra("apellidos","Chacon");
        startActivity(seleccion);
    }
}