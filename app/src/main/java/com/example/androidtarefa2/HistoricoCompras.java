package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import adaptadores_recycler_view_pedidos_admitidos.Lista_pedidos_admitidos;
import admin_adaptadores_recycler_view_ver_pedidos_aceptados.Admin_Lista_Pedidos_Aceptados;
import persistencia.BaseDatos;

public class HistoricoCompras extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_compras);

        Lista_pedidos_admitidos recycleAdapter = new Lista_pedidos_admitidos();
        //iniciar lista a mostrar
        consultaPedidosAceptados(recycleAdapter,getIntent().getExtras().get("usuario").toString());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.rvwRecycleView_Pedidos_Aceptados_Del_Cliente);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleAdapter);
    }

    //Consulta pedidos del usuario
    private void consultaPedidosAceptados(Lista_pedidos_admitidos recycleAdapter,String usuario) {
        String producto;
        String cantidade;
        String direccion;
        String cidade;
        String codigo_postal;
        int id;

        recycleAdapter.resetearLista();//resetear valores

        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select producto,cantidade,direccion,cidade,codigo_postal from COMPRAS WHERE " +
                " usuario ='" + usuario + "' AND estado_pedido=" + BaseDatos.ACEPTADO +"", null);

        if (cursor.moveToFirst()) {
            do {
                //almacenar valores
                producto = cursor.getString(0);
                cantidade = cursor.getString(1);
                direccion = cursor.getString(2);
                cidade = cursor.getString(3);
                codigo_postal = cursor.getString(4);
               // id = cursor.getInt(5);
                System.out.println("Pedidos aceptados usuario");
                System.out.println(producto + " " + cantidade + direccion + cidade + codigo_postal );
                recycleAdapter.añadirvalores("Pedido:" +producto + " " + cantidade + " " +direccion +  " " + cidade + " " + codigo_postal);//añadir valores a la lista
            } while (cursor.moveToNext());
        }


    }
}
