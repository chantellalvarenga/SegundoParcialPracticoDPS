package com.example.chalonapp.ui.login;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chalonapp.R;
import com.example.chalonapp.SqlLiteOpenHelperAdmin;
import com.example.chalonapp.activity_selecion;
import com.example.chalonapp.data.model.Tratamiento;
import com.example.chalonapp.activity_reserva;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Tratamiento> list;
   int _id_cliente=0;

    public CustomAdapter(Context context, List<Tratamiento> list,int id_cliente) {
        this.context = context;
        this.list = list;
        _id_cliente=id_cliente;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imageTratamiento;
        TextView TxtNombre;
        TextView TxtPrecio;
        Button btn_reserva;

        Tratamiento item=list.get(i);

        if(view==null)
            view=LayoutInflater.from(context).inflate(R.layout.item_list_view,null);

            imageTratamiento=view.findViewById(R.id.imageViewTratamiento);
            TxtNombre=view.findViewById(R.id.txtNombre_tratamiento);
            TxtPrecio=view.findViewById(R.id.text_precio_tratamiento);
            btn_reserva=view.findViewById(R.id.btnReservar);
            Glide.with(view)
                    .load(item.getImg_url().toString())
                    .into(imageTratamiento);

            TxtNombre.setText(item.getNombre());
            TxtPrecio.setText(String.valueOf(item.getPrecio()));


btn_reserva.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String nombres_cliente="";
        String apellidos_cliente="";

        SqlLiteOpenHelperAdmin admin = new SqlLiteOpenHelperAdmin(context,"chalon_database",null,1);

        SQLiteDatabase database =admin.getReadableDatabase();


        Cursor fila=database.rawQuery("select nombres,apellidos from clientes where id="+_id_cliente+"",null);

        if(fila.moveToFirst())
        {
            nombres_cliente=fila.getString(0).toString();
            apellidos_cliente=fila.getString(1).toString();
        }


        Intent reserva=new Intent(context,activity_reserva.class);
        reserva.putExtra("id_cliente",_id_cliente);
        reserva.putExtra("nombres_cliente",nombres_cliente);
        reserva.putExtra("apellidos_cliente",apellidos_cliente);
        reserva.putExtra("id_tratamiento",item.getId());
        reserva.putExtra("nombre_tratamiento",item.getNombre());
        reserva.putExtra("precio_tratamiento",item.getPrecio());

        context.startActivity(reserva);
    }
});
        return view;
    }
}
