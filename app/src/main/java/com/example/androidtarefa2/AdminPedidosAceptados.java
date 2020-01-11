package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import admin_adaptadores_recycler_view_ver_Pedidos_En_Tramite.Admin_Lista_pedidos_Tramite;
import admin_adaptadores_recycler_view_ver_pedidos_aceptados.Admin_Lista_Pedidos_Aceptados;
import persistencia.BaseDatos;

public class AdminPedidosAceptados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__pedidos__aceptados);
        //rvwRecycleView_Pedidos_Cliente_Aceptados

        Admin_Lista_Pedidos_Aceptados recycleAdapter = new Admin_Lista_Pedidos_Aceptados();
        //iniciar lista a mostrar
        consultaPedidosTramite(recycleAdapter);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.rvwRecycleView_Pedidos_Cliente_Aceptados);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleAdapter);
    }


    private void consultaPedidosTramite(Admin_Lista_Pedidos_Aceptados recycleAdapter) {//EN_TRAMITE
        String producto;
        String cantidade;
        String direccion;
        String cidade;
        String codigo_postal;
        int id;


        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select producto,cantidade,direccion,cidade,codigo_postal,_id from COMPRAS WHERE " +
                " estado_pedido =" + BaseDatos.ACEPTADO + "", null);

        if (cursor.moveToFirst()) {
            do {
                //almacenar valores
                producto = cursor.getString(0);
                cantidade = cursor.getString(1);
                direccion = cursor.getString(2);
                cidade = cursor.getString(3);
                codigo_postal = cursor.getString(4);
                id = cursor.getInt(5);
                System.out.println("Pedidos aceptados");
                System.out.println(producto + " " + cantidade + direccion + cidade + codigo_postal + "  " + cursor.getInt(5));
                recycleAdapter.añadirvalores("_id" + id +" \nPedido:" +producto + " " + cantidade + " " +direccion +  " " + cidade + " " + codigo_postal);//añadir valores a la lista
            } while (cursor.moveToNext());
        }


    }
}
