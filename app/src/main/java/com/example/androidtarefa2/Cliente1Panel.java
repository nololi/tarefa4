package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Cliente1Panel extends AppCompatActivity {
    private String nome;
    private String apelidos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente1_panel);

            //recojo los valores enviados desde la página anterior y los añado
            nome = getIntent().getExtras().getString("nome");
            apelidos = getIntent().getExtras().getString("apelidos");
            TextView datosCliente = findViewById(R.id.datosCliente);
            datosCliente.setText(nome + " " + apelidos);


    }

    //Hacer nuevos pedidos
    public void hacerPedidos(View view){
        Intent nuevoPedido = new Intent(this, HacerPedido.class);
        nuevoPedido.putExtra("usuario",getIntent().getExtras().getString("usuario"));
        nuevoPedido.putExtra("nome",nome);
        nuevoPedido.putExtra("apelidos",apelidos);
        startActivity(nuevoPedido);
    }

    //Ver pedidos
    public void verPedidos(View view){
        Intent pedidos = new Intent(this, PedidosTramite.class);
        pedidos.putExtra("usuario",getIntent().getExtras().getString("usuario"));
        startActivity(pedidos);
    }

    //Ver compras realizadas
    public void mostrarHistóricoCompras(View view){
        Intent historicoCompras = new Intent(this,HistoricoCompras.class);
        startActivity(historicoCompras);
    }

    //Salir a pantalla principal
    public void salir(View view){
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
