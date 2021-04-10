package com.example.chalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.chalonapp.data.model.Cliente;
import com.example.chalonapp.data.model.Tratamiento;

public class activity_reserva extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);

        //Declaración de variables a utilizar
        textView=findViewById(R.id.textView_reserva);

        //Obtenemos datos del usuario y del tratamiento seleccionado
        Bundle bundle = getIntent().getExtras();
          int id_cliente;
          int id_tratamiento;
          String nombre_cliente="";
          String apellidos_cliente="";
          String nombre_tratamiento="";
          double precio_tratamiento=0;

          //Datos para realizar una reservación
           id_cliente=bundle.getInt("id_cliente");
           id_tratamiento=bundle.getInt("id_tratamiento");

           nombre_cliente=bundle.getString("nombres_cliente");
           apellidos_cliente=bundle.getString("apellidos_cliente");

           nombre_tratamiento=bundle.getString("nombre_tratamiento");
           precio_tratamiento=bundle.getDouble("precio_tratamiento");


           String mensaje = "\n cliente: " + apellidos_cliente + "," + nombre_cliente +
                            "\n Tratamiento: " + nombre_tratamiento +
                            "\n Precio: " + String.valueOf(precio_tratamiento);

           textView.setText(mensaje);
    }

}