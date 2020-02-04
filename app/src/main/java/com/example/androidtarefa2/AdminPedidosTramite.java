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
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import admin_adaptadores_recycler_view_ver_Pedidos_En_Tramite.Admin_Lista_pedidos_Tramite;
import persistencia.BaseDatos;

public class AdminPedidosTramite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pedidos_tramite);

        Toolbar toolbar = findViewById(R.id.admin_layout_toolbar_back);
        setSupportActionBar(toolbar);

        Admin_Lista_pedidos_Tramite recycleAdapter = new Admin_Lista_pedidos_Tramite();
        consultaPedidosTramite(recycleAdapter); //inicio la lista a mostrar

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerView recyclerView = findViewById(R.id.rvwRecycleView_Pedidos_Cliente_En_Tramite);

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
        if(item.getItemId()==R.id.atras){
            System.out.println("atrás");
            Intent intent = new Intent(getApplicationContext(), AdminPanel.class);
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

    private void consultaPedidosTramite(Admin_Lista_pedidos_Tramite recycleAdapter) {//EN_TRAMITE
        String producto;
        String cantidade;
        String direccion;
        String cidade;
        String codigo_postal;
        String usuario;
        int id;

        recycleAdapter.resetearLista();//resetear valores

        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select producto,cantidade,direccion,cidade,codigo_postal,_id,usuario from COMPRAS WHERE " +
                " estado_pedido =" + BaseDatos.EN_TRAMITE + "", null);

        if (cursor.moveToFirst()) {
            do {
                JSONObject data = new JSONObject(); //por simplicidad usaré un JsonObject
                try { //recoger valores consulta
                    data.put("producto", cursor.getString(0));
                    data.put("cantidade", cursor.getString(1));
                    data.put("direccion", cursor.getString(2));
                    data.put("cidade", cursor.getString(3));
                    data.put("codigo_postal", cursor.getString(4));
                    data.put("id", cursor.getInt(5));
                    data.put("usuario", cursor.getString(6));
                    System.out.println("data " + data);
                    recycleAdapter.añadirvalores(data);//añadir valores a la lista
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }


    }

}
