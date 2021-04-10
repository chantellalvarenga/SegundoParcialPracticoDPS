package com.example.chalonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chalonapp.data.model.Tratamiento;
import com.example.chalonapp.ui.login.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

public class activity_selecion extends AppCompatActivity {

    EditText idtxt;
    ImageView img;
    TextView txtBienvenidaUser;
    ListView listviewTratamientos;
    List<Tratamiento> listaTratamientos;
    String nombres = "";
    String apellidos = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecion);

        //Declaramos las variables a utilizar
        //img=findViewById(R.id.imageView1);
        txtBienvenidaUser = findViewById(R.id.txtBienvenidaUser);
        listviewTratamientos=findViewById(R.id.listView1);
        Bundle bundle = getIntent().getExtras();

        //Recibimos los datos del usuario loggeado
        nombres = bundle.getString("nombres");
        apellidos = bundle.getString("apellidos");

        //Verificar en la base de datos y obtener el id del usuario recibido desde Firebase
        int id = 0;
        id = VerificarUser(nombres, apellidos);

        //Bienvenida al Usuario
        txtBienvenidaUser.setText("Bienvenido: " + nombres + " " + apellidos);

        //Si no existe en BD lo crea
       if(id == 0)
       {
           InsertUser(id,nombres,apellidos);
       }

       CustomAdapter adapter = new CustomAdapter(this, GetData(), id);
        listviewTratamientos.setAdapter(adapter);
    }

    private List<Tratamiento> GetData() {
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);
        SQLiteDatabase database = admin.getReadableDatabase();
        ArrayList<Tratamiento> listItem = new ArrayList<>();

        Cursor fila = database.rawQuery("select id, nombre, precio, img_url from tratamientos ",null);

        if(fila.moveToFirst())
        {
            do {
                //Llenando las listas
                listItem.add(new Tratamiento(fila.getInt(0),fila.getString(1),fila.getDouble(2),fila.getString(3)));

            } while(fila.moveToNext());
        }
        else
        {
            Toast.makeText(this,"¡No hay tratamientos disponibles en este momento!", Toast.LENGTH_SHORT).show();
        }
        return  listItem;
    }


    public int VerificarUser(String _nombres, String _apellidos)
    {
      int respuesta = 0;

        //Conexión a la base de datos
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);
        SQLiteDatabase database = admin.getReadableDatabase();

        Cursor fila = database.rawQuery("select id from clientes where nombres='"+_nombres.toString()+"' AND apellidos='"+_apellidos.toString()+"'",null);

        if(fila.moveToFirst())
        {
            respuesta = fila.getInt(0);
        }

        return respuesta;
    }

    //Inserta usuario logeado desde Firebase si no existe en la base de datos sqlLite
    //Recibe como parametros los nombres y apellidos
    public void InsertUser(Integer id, String nombres, String apellidos)
    {
        int last_id = 0;
        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(this,"chalon_database",null,1);

        SQLiteDatabase database = admin.getReadableDatabase();

        Cursor fila0 = database.rawQuery("SELECT MAX(id) FROM clientes",null);

        if(fila0.moveToFirst())
        {
            last_id = fila0.getInt(0);
        }

        int _id = last_id + 1;
        final String Insert_cliente = "INSERT INTO clientes VALUES( " + _id + ", '" + nombres + "', '" + apellidos + "')";
        database.execSQL(Insert_cliente);
        //Actualizar la activity para mostrar los datos del insert actualizados
       this.recreate();
    }
}