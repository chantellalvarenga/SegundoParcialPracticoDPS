package com.example.chalonapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqlLiteOpenHelperAdmin extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "chalon_database";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_CLIENTES = "clientes";
    private static final String TABLE_TRATAMIENTOS = "tratamientos";
    private static final String TABLE_CITAS = "citas";

    // Columnas tabla clientes
    private static final String KEY_CLIENTE_ID = "id";
    private static final String KEY_CLIENTE_NOMBRES = "nombres";
    private static final String KEY_CLIENTE_APELLIDOS = "apellidos";


    // Columnas tabla tratamientos
    private static final String KEY_TRATAMIENTOS_ID = "id";
    private static final String KEY_TRATAMIENTOS_NOMBRE = "nombre";
    private static final String KEY_TRATAMIENTOS_PRECIO = "precio";
    private static final String KEY_TRATAMIENTOS_IMG = "img_url";

    // Columnas tabla citas
    private static final String KEY_CITAS_ID = "id";
    private static final String KEY_CITAS_ID_CLIENTE = "id_cliente";
    private static final String KEY_CITAS_ID_TRATAMIENTO = "id_tratamiento";
    private static final String KEY_CITAS_FECHA="fecha";
    private static final String KEY_CITAS_ESTADO="estado";

    public SqlLiteOpenHelperAdmin(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CLIENTES_TABLE = "CREATE TABLE " + TABLE_CLIENTES +
                "(" +
                KEY_CLIENTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                KEY_CLIENTE_NOMBRES + " TEXT,"+
                KEY_CLIENTE_APELLIDOS +" TEXT"+
                ")";
        String CREATE_TRATAMIENTOS_TABLE = "CREATE TABLE " + TABLE_TRATAMIENTOS +
                "(" +
                KEY_TRATAMIENTOS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                KEY_TRATAMIENTOS_NOMBRE + " TEXT,"+
                KEY_TRATAMIENTOS_PRECIO +" REAL,"+
                KEY_TRATAMIENTOS_IMG+" TEXT"+
                ")";
        String CREATE_CITAS_TABLE = "CREATE TABLE " + TABLE_CITAS +
                "(" +
                KEY_CITAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
                KEY_CITAS_ID_CLIENTE+" INTEGER,"+
                KEY_CITAS_ID_TRATAMIENTO+" INTEGER,"+
                KEY_CITAS_ESTADO+" TEXT,"+
                KEY_CITAS_FECHA+" TEXT,"+
                "FOREIGN KEY("+KEY_CITAS_ID_CLIENTE+") references "+TABLE_CLIENTES+""+"("+KEY_CLIENTE_ID+"),"+
                "FOREIGN KEY("+KEY_CITAS_ID_TRATAMIENTO+") references "+TABLE_TRATAMIENTOS+""+"("+KEY_TRATAMIENTOS_ID+")"+
                ")";

        db.execSQL(CREATE_CLIENTES_TABLE);
        db.execSQL(CREATE_TRATAMIENTOS_TABLE);
        db.execSQL(CREATE_CITAS_TABLE);

        //Tratamiento 1
        final String Insert_Data1="INSERT INTO tratamientos VALUES(1,'Color completo',30.0,'https://www.esteticamp.com/imgs/grandes/color.jpg')";
        db.execSQL(Insert_Data1);
        //Tratamiento 2
        final String Insert_Data2="INSERT INTO tratamientos VALUES(2,'Mechas',25.0,'https://estaticos.marie-claire.es/uploads/images/gallery/5f4380475cafe803dc7e24a5/mechas-foilayage_0.jpg')";
        db.execSQL(Insert_Data2);
        //Tratamiento 3
        final String Insert_Data3="INSERT INTO tratamientos VALUES(3,'Decoloracion',20.0,'https://ath2.unileverservices.com/wp-content/uploads/sites/11/2019/05/decoloracion-de-pelo-2.jpg')";
        db.execSQL(Insert_Data3);
        //Tratamiento 4
        final String Insert_Data4="INSERT INTO tratamientos VALUES(4,'Hidratacion',15.0,'https://149363200.v2.pressablecdn.com/wp-content/uploads/2016/10/Depositphotos_6140245_m-2015-e1481917306675.jpg')";
        db.execSQL(Insert_Data4);
        //Tratamiento 5
        final String Insert_Data5="INSERT INTO tratamientos VALUES(5,'Botox capilar',22.0,'https://www.onmujer.com/wp-content/uploads/2018/11/botox-capilar.jpg')";
        db.execSQL(Insert_Data5);
        //Tratamiento 6
        final String Insert_Data6="INSERT INTO tratamientos VALUES(6,'Tratamiento de barros',17.0,'https://img.freepik.com/foto-gratis/mujer-hermosa-joven-mascara-cara-barro-negro-terapeutico-tratamiento-spa_231208-1553.jpg?size=626&ext=jpg')";
        db.execSQL(Insert_Data6);
        //Tratamiento 7
        final String Insert_Data7="INSERT INTO tratamientos VALUES(7,'Alisado de keratina',25.0,'https://sonaesierracms-v2.cdnpservers.net/wp-content/uploads/sites/28/2019/01/keratina-alisado-tratamiento.jpg')";
        db.execSQL(Insert_Data7);
        //Tratamiento 8
        final String Insert_Data8="INSERT INTO tratamientos VALUES(8,'Tratamiento de keratina',20.0,'https://media.ahora.com.ar/adjuntos/226/imagenes/001/583/0001583408.jpg')";
        db.execSQL(Insert_Data8);
        //Tratamiento 9
        final String Insert_Data9="INSERT INTO tratamientos VALUES(9,'Tratamiento facial',18.0,'https://www.estrelladigital.es/media/estrelladigital/images/2018/10/23/2018102312182333323.jpg')";
        db.execSQL(Insert_Data9);

        //Tratamiento 10
        final String Insert_Data10="INSERT INTO tratamientos VALUES(10,'Tratamiento corporal',18.0,'https://www.elementsspacr.com/wp-content/uploads/2017/06/banner-servicios-corporales.jpg')";
        db.execSQL(Insert_Data10);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITAS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRATAMIENTOS);
            onCreate(db);
        }
    }
}
