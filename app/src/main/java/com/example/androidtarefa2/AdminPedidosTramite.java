package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import admin_adaptadores_recycler_view_ver_Pedidos_En_Tramite.Admin_Lista_pedidos_Tramite;
import persistencia.BaseDatos;

public class AdminPedidosTramite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pedidos_tramite);

        Admin_Lista_pedidos_Tramite recycleAdapter = new Admin_Lista_pedidos_Tramite();
        consultaPedidosTramite(recycleAdapter); //inicio la lista a mostrar

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.rvwRecycleView_Pedidos_Cliente_En_Tramite);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recycleAdapter);
    }

    private void consultaPedidosTramite(Admin_Lista_pedidos_Tramite recycleAdapter) {//EN_TRAMITE
        String producto;
        String cantidade;
        String direccion;
        String cidade;
        String codigo_postal;

        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select producto,cantidade,direccion,cidade,codigo_postal from COMPRAS WHERE " +
                " estado_pedido =" + BaseDatos.EN_TRAMITE + "", null);

        if (cursor.moveToFirst()) {
            do {
                //almacenar valores
                producto = cursor.getString(0);
                cantidade = cursor.getString(1);
                direccion = cursor.getString(2);
                cidade = cursor.getString(3);
                codigo_postal = cursor.getString(4);
                System.out.println(producto + " " + cantidade + direccion + cidade + codigo_postal);
                recycleAdapter.añadirvalores(producto + " " + cantidade + " " +direccion +  " " + cidade + " " + codigo_postal);//añadir valores a la lista
            } while (cursor.moveToNext());
        }


    }
}
