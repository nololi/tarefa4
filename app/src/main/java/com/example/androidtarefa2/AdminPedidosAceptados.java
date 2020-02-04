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

import admin_adaptadores_recycler_view_ver_pedidos_aceptados.Admin_Lista_Pedidos_Aceptados;
import persistencia.BaseDatos;

public class AdminPedidosAceptados extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__pedidos__aceptados);

        Toolbar toolbar = findViewById(R.id.admin_layout_toolbar_back); //appBar
        setSupportActionBar(toolbar);

        Admin_Lista_Pedidos_Aceptados recycleAdapter = new Admin_Lista_Pedidos_Aceptados();
        //inicializar  valores lista a mostrar
        consultaPedidosAceptados(recycleAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.rvwRecycleView_Pedidos_Cliente_Aceptados);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recycleAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_back, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.atras) { //botón atrás
            Intent intent = new Intent(getApplicationContext(), AdminPanel.class);
            intent.putExtra("usuario", getIntent().getExtras().getString("usuario"));
            intent.putExtra("nome", getIntent().getExtras().getString("nome"));
            intent.putExtra("apelidos", getIntent().getExtras().getString("apelidos"));
            intent.putExtra("rutaImaxe",getIntent().getExtras().getString("rutaImaxe"));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void consultaPedidosAceptados(Admin_Lista_Pedidos_Aceptados recycleAdapter) {//ACEPTADOS
        String producto;
        String cantidade;
        String direccion;
        String cidade;
        String codigo_postal;
        int id;

        recycleAdapter.resetearLista();//resetear valores

        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select producto,cantidade,direccion,cidade,codigo_postal,_id from COMPRAS WHERE " +
                " estado_pedido =" + BaseDatos.ACEPTADO + "", null);

        if (cursor.moveToFirst()) { //recorro la lista mientras haya valores
            do {
                //almacenar valores recogidos
                producto = cursor.getString(0);
                cantidade = cursor.getString(1);
                direccion = cursor.getString(2);
                cidade = cursor.getString(3);
                codigo_postal = cursor.getString(4);
                id = cursor.getInt(5);
                recycleAdapter.añadirvalores("idPedido:  " + id + " \nProducto:  " +
                        producto + " \nCantidade:  " + cantidade + " \nDirección:   " +
                        direccion + " \nCidade: " + cidade + " \nCP : " + codigo_postal);//añadir valores a la lista
            } while (cursor.moveToNext());
        }


    }
}
