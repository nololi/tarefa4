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

import adaptadores_recycler_view_pedidos_admitidos.Lista_pedidos_admitidos;
import persistencia.BaseDatos;

public class HistoricoCompras extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_compras);

        Toolbar toolbar = findViewById(R.id.layout_toolbar_back);//appBar
        setSupportActionBar(toolbar);


        Lista_pedidos_admitidos recycleAdapter = new Lista_pedidos_admitidos();
        //iniciar lista a mostrar
        consultaPedidosAceptados(recycleAdapter,getIntent().getExtras().get("usuario").toString());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.rvwRecycleView_Pedidos_Aceptados_Del_Cliente);

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
            //no hemos dado sesiones, así que le paso los datos para no repetir la consulta
            intent.putExtra("usuario", getIntent().getExtras().getString("usuario"));
            intent.putExtra("nome",getIntent().getExtras().getString("nome"));
            intent.putExtra("apelidos",getIntent().getExtras().getString("apelidos"));
            intent.putExtra("rutaImaxe",getIntent().getExtras().getString("rutaImaxe"));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                //obtención de valores de la consulta
                producto = cursor.getString(0);
                cantidade = cursor.getString(1);
                direccion = cursor.getString(2);
                cidade = cursor.getString(3);
                codigo_postal = cursor.getString(4);
                //añadir valores a la lista
                recycleAdapter.añadirvalores("Producto:  " +producto +
                        " \nCantidade:  " + cantidade + " \nDirección:  " +direccion +
                        "\nCidade:   " + cidade + " \nCódigo Postal : " + codigo_postal);
            } while (cursor.moveToNext());
        }


    }
}
