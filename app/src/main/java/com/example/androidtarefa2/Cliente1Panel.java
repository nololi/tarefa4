package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Cliente1Panel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente1_panel);
    }
    //Hacer nuevos pedidos
    public void hacerPedidos(View view){
        Intent nuevoPedido = new Intent(this, HacerPedido.class);
        startActivity(nuevoPedido);
    }

    //Ver pedidos
    public void verPedidos(View view){
        Intent pedidos = new Intent(this, PedidosTramite.class);
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
