package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DireccionPedido extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String valor = getIntent().getExtras().getString("categoria");

        setContentView(R.layout.activity_direccion_pedido);
    }

    //Método para guardar el pedido + dirección y volver a la pantalla del cliente
    public void guardarPedido(View view){
        Context context = getApplicationContext();
        StringBuilder textoResumo = new StringBuilder();
        textoResumo.append("\tResumo da compra : \n Categoría : " + getIntent().getExtras().getString("categoria") +"\n");
        textoResumo.append("Producto : " + getIntent().getExtras().getString("producto") +"\n");
        textoResumo.append("Cantidade : " + getIntent().getExtras().getString("cantidade") +"\n");
        EditText direccion = findViewById(R.id.campoDireccion);
        textoResumo.append("Dirección : " +direccion.getText().toString() +"\n");
        EditText cidade = findViewById(R.id.campoCiudad);
        textoResumo.append("Cidade : " +cidade.getText().toString() +"\n");
        EditText codigoPostal = findViewById(R.id.campoCodigoPostal);
        textoResumo.append("Código Postal : " +codigoPostal.getText().toString() +"\n");

        //muestro la selección en un toast
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, textoResumo, duration);
        toast.show();

        //Elimino la toast y guardo en la base de datos



        //borro actividad actual y vuelvo al panel cliente
        Intent intent = new Intent(this, Cliente1Panel.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
