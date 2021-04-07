package com.example.chalonapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chalonapp.data.model.Tratamiento;
import com.example.chalonapp.ui.login.CustomAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class activity_selecion extends AppCompatActivity {

    EditText idtxt;
    ImageView img;
    TextView txt;
    ListView listviewTratamientos;
    List<Tratamiento> listaTratamientos;
    String nombres="";
    String apellidos="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecion);
        img=findViewById(R.id.imageView1);
        txt=findViewById(R.id.TxtView1);
        listviewTratamientos=findViewById(R.id.listView1);
        Bundle bundle = getIntent().getExtras();

        nombres=bundle.getString("nombres");
        apellidos=bundle.getString("apellidos");

        int id=0;

        id=VerificarUser(nombres,apellidos);
       txt.setText("Usuario: "+nombres+" "+apellidos+"Respuesta "+String.valueOf(VerificarUser(nombres,apellidos)));

       if(id==0)
       {
          InsertUser(id,nombres,apellidos);
       }
       else
       {

       }


       CustomAdapter adapter=new CustomAdapter(this,GetData(),id);
        listviewTratamientos.setAdapter(adapter);
    }

    private List<Tratamiento> GetData() {
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);

        SQLiteDatabase database =admin.getReadableDatabase();

        ArrayList<Tratamiento> listItem=new ArrayList<>();



        Cursor fila=database.rawQuery("select id,nombre,precio,img_url from tratamientos ",null);

        if(fila.moveToFirst())
        {

            do {
                // Passing values
                //llenando las listas
                listItem.add(new Tratamiento(fila.getInt(0),fila.getString(1),fila.getDouble(2),fila.getString(3)));
                // Do something Here with values
            } while(fila.moveToNext());
        }
        else
        {
            Toast.makeText(this,"no se encontro datos", Toast.LENGTH_SHORT).show();

        }
        return  listItem;
    }


    public int VerificarUser(String _nombres,String _apellidos)
    {
      int respuesta=0;

      //Lineas necesarias para abrir la base de datos
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);

        SQLiteDatabase database =admin.getReadableDatabase();


        Cursor fila=database.rawQuery("select id from clientes where nombres='"+_nombres.toString()+"' AND apellidos='"+_apellidos.toString()+"'",null);

        if(fila.moveToFirst())
        {
            respuesta=fila.getInt(0);
        }



        return respuesta;
    }

//Este metodo sirve para insertar el usuario logeado si no esta dentro de la base de datos se agrega
    //solo se pasan de parametros los nombres y apellidos
    public void InsertUser(Integer id,String nombres,String apellidos)
    {
        int last_id=0;
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);

        SQLiteDatabase database =admin.getReadableDatabase();

        Cursor fila0=database.rawQuery("SELECT MAX(id) FROM clientes",null);

        if(fila0.moveToFirst())
        {
            last_id=fila0.getInt(0);
        }

        int _id=last_id+1;
        final String Insert_cliente="INSERT INTO clientes VALUES("+_id+",'"+nombres+"','"+apellidos+"')";
        database.execSQL(Insert_cliente);

       this.recreate();
    }
}