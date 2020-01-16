package com.example.androidtarefa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import adaptadores_recycler_view_ver_pedidos_en_curso.Lista_pedidos_tramite;
import persistencia.BaseDatos;

public class PedidosTramite extends AppCompatActivity {

    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_tramite);

        Toolbar toolbar = findViewById(R.id.layout_toolbar_back);//appBar
        setSupportActionBar(toolbar);

        usuario = getIntent().getExtras().getString("usuario");

        Lista_pedidos_tramite recycleAdapter = new Lista_pedidos_tramite();
        recycleAdapter.resetearLista();//resetear valores
        consultaPedidosTramite(recycleAdapter); //inicio la lista a mostrar

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.rvwRecycleView_Pedidos_Cliente);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recycleAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_back,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.atras){//botón atrás
            System.out.println("atrás");
            Intent intent = new Intent(getApplicationContext(), Cliente1Panel.class);
            intent.putExtra("usuario", getIntent().getExtras().getString("usuario"));
            intent.putExtra("nome",getIntent().getExtras().getString("nome"));
            intent.putExtra("apelidos",getIntent().getExtras().getString("apelidos"));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void consultaPedidosTramite(Lista_pedidos_tramite recycleAdapter) {//EN_TRAMITE
        String producto;
        String cantidade;
        String direccion;
        String cidade;
        String codigo_postal;

        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select producto,cantidade,direccion,cidade,codigo_postal" +
                " from COMPRAS where usuario='" + usuario + "' " +
                "AND estado_pedido =" + BaseDatos.EN_TRAMITE + "", null);

        if (cursor.moveToFirst()) {
            do {
                //obtención valores de la consulta
                producto = cursor.getString(0);
                cantidade = cursor.getString(1);
                direccion = cursor.getString(2);
                cidade = cursor.getString(3);
                codigo_postal = cursor.getString(4);
                //añadir valores a la lista
                recycleAdapter.añadirvalores("Producto:  " + producto +
                        "\nCantidade:   " + cantidade + " \nDirección :   " + direccion +
                        " \nCidade : " +cidade + " \nCódigo Postal :  " +codigo_postal);
            } while (cursor.moveToNext());
        }


    }
}
