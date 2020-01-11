package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.androidtarefa2.R;



public class AdminPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        //recojo los valores enviados desde la página anterior y los añado
        String nome = getIntent().getExtras().getString("nome");
        String apelidos = getIntent().getExtras().getString("apelidos");
        TextView datosCliente = findViewById(R.id.datosCliente);
        datosCliente.setText(nome + " " + apelidos);
    }

    //Ver pedidos en trámite
    public void verPedidosTramite(View view){
        Intent pedidos = new Intent(this, AdminPedidosTramite.class);
        pedidos.putExtra("usuario",getIntent().getExtras().getString("usuario"));
        startActivity(pedidos);
    }

    //Ver pedido aceptados
    public void verPedidosAceptados(View view){
        Intent pedidos = new Intent(this, AdminPedidosAceptados.class);
        pedidos.putExtra("usuario",getIntent().getExtras().getString("usuario"));
        startActivity(pedidos);
    }

    //Ver pedidos rexeitados
    public void verPedidosRexeitados(View view){
        Intent pedidos = new Intent(this, AdminPedidosRexeitados.class);
        pedidos.putExtra("usuario",getIntent().getExtras().getString("usuario"));
        startActivity(pedidos);
    }
}
