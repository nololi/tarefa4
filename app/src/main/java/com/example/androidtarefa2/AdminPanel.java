package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AdminPanel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        //recojo los valores enviados desde la página anterior y los añado
        String nome = getIntent().getExtras().getString("nome");
        String apelidos = getIntent().getExtras().getString("apelidos");
        TextView datosCliente = (TextView)findViewById(R.id.datosCliente);
        datosCliente.setText(nome + " " + apelidos);
    }
}
