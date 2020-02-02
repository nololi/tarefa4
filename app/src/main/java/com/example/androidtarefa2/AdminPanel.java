package com.example.androidtarefa2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class AdminPanel extends AppCompatActivity {
    private String nome;
    private String apelidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Toolbar toolbar = findViewById(R.id.admin_layout_toolbar);
        setSupportActionBar(toolbar);

        //recojo los valores enviados desde la página anterior y los añado
         nome = getIntent().getExtras().getString("nome");
         apelidos = getIntent().getExtras().getString("apelidos");

        String rutaImaxe = getIntent().getExtras().getString("rutaImaxe");//TODO comprobar cuando vuelvo de otra pantalla
        Bitmap bitmap = BitmapFactory.decodeFile(rutaImaxe);
        ImageView icono = (ImageView) findViewById(R.id.fotoUsuario);
        icono.setImageBitmap(bitmap);

        TextView datosCliente = findViewById(R.id.datosCliente);
        datosCliente.setText(nome + " " + apelidos);
    }

    //sobreescribo método onCreateOptionsMenu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin_items,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //sobrescribo método onOptionsItemSelected, y redirijo a cada opción
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String usuario = getIntent().getExtras().getString("usuario");
        switch(item.getItemId()){
            case R.id.item_pedidos_aceptados:
                Intent pedidosAceptados = new Intent(this, AdminPedidosAceptados.class);
                pedidosAceptados.putExtra("usuario",usuario);
                pedidosAceptados.putExtra("nome",nome);
                pedidosAceptados.putExtra("apelidos",apelidos);
                startActivity(pedidosAceptados);
                return true;
            case R.id.item_pedidos_rexeitados:
                Intent pedidosRexeitados = new Intent(this, AdminPedidosRexeitados.class);
                pedidosRexeitados.putExtra("usuario",usuario);
                pedidosRexeitados.putExtra("nome",nome);
                pedidosRexeitados.putExtra("apelidos",apelidos);
                startActivity(pedidosRexeitados);
                return true;
            case R.id.item_pedidos_tramite:
                Intent pedidos = new Intent(this, AdminPedidosTramite.class);
                pedidos.putExtra("usuario",usuario);
                pedidos.putExtra("nome",nome);
                pedidos.putExtra("apelidos",apelidos);
                startActivity(pedidos);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Ver pedidos en trámite
    public void verPedidosTramite(View view){
        Intent pedidos = new Intent(this, AdminPedidosTramite.class);
        pedidos.putExtra("usuario",getIntent().getExtras().getString("usuario"));
        pedidos.putExtra("nome",nome);
        pedidos.putExtra("apelidos",apelidos);
        startActivity(pedidos);
    }

    //Ver pedido aceptados
    public void verPedidosAceptados(View view){
        Intent pedidos = new Intent(this, AdminPedidosAceptados.class);
        pedidos.putExtra("usuario",getIntent().getExtras().getString("usuario"));
        pedidos.putExtra("nome",nome);
        pedidos.putExtra("apelidos",apelidos);
        startActivity(pedidos);
    }

    //Ver pedidos rexeitados
    public void verPedidosRexeitados(View view){
        Intent pedidos = new Intent(this, AdminPedidosRexeitados.class);
        pedidos.putExtra("usuario",getIntent().getExtras().getString("usuario"));
        pedidos.putExtra("nome",nome);
        pedidos.putExtra("apelidos",apelidos);
        startActivity(pedidos);
    }

    //Salir a pantalla principal
    public void salir(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
