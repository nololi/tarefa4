package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

import adaptadores_recycler_view_ver_pedidos_en_curso.Lista_pedidos_tramite;
import persistencia.BaseDatos;

public class PedidosTramite extends AppCompatActivity {

    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_tramite);


        usuario = getIntent().getExtras().getString("usuario");

        Lista_pedidos_tramite recycleAdapter = new Lista_pedidos_tramite();
        recycleAdapter.resetearLista();//resetear valores
        consultaPedidosTramite(recycleAdapter); //inicio la lista a mostrar

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.rvwRecycleView_Pedidos_Cliente);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recycleAdapter);
    }


    private void consultaPedidosTramite(Lista_pedidos_tramite recycleAdapter) {//EN_TRAMITE
        String producto;
        String cantidade;
        String direccion;
        String cidade;
        String codigo_postal;

        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select producto,cantidade,direccion,cidade,codigo_postal from COMPRAS where usuario='" + usuario + "' " +
                "AND estado_pedido =" + BaseDatos.EN_TRAMITE + "", null);

        if (cursor.moveToFirst()) {
            do {
                //almacenar valores
                producto = cursor.getString(0);
                cantidade = cursor.getString(1);
                direccion = cursor.getString(2);
                cidade = cursor.getString(3);
                codigo_postal = cursor.getString(4);
                //añadir valores a la lista
                recycleAdapter.añadirvalores(producto + " " + cantidade + " " + direccion + " " +cidade + " " +codigo_postal);
            } while (cursor.moveToNext());
        }


    }
}
