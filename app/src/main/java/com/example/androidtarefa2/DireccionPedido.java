package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import persistencia.BaseDatos;

public class DireccionPedido extends AppCompatActivity {
    //de la pantalla anterior
    private String categoria;
    private String producto;
    private String cantidade;
    private String usuario;

    //del formulario
    private String cidade;
    private String direccion;
    private String codigoPostal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_pedido);
        inicializarValores();
    }

    //método para inicializar valores enviados desde la pantalla anterior
    private void inicializarValores(){
        categoria = getIntent().getExtras().getString("categoria");
        producto = getIntent().getExtras().getString("producto");
        cantidade = getIntent().getExtras().getString("cantidade");
        usuario = getIntent().getExtras().getString("usuario");
    }

    //(Click) Método para guardar el pedido + dirección y volver a la pantalla del cliente
    public void guardarPedido(View view) {
        EditText campoDireccion = findViewById(R.id.campoDireccion);
        direccion = campoDireccion.getText().toString();
        EditText campoCiudad = findViewById(R.id.campoCiudad);
        cidade = campoCiudad.getText().toString();
        EditText campoCodigoPostal = findViewById(R.id.campoCodigoPostal);
        codigoPostal = campoCodigoPostal.getText().toString();

        //si alguno de los valores está vacío (no valida si código postal es numérico), mostrar Toast avisando
        if(direccion.isEmpty()  || cidade.isEmpty() || codigoPostal.isEmpty()){
            Toast.makeText(this,"Complete los datos dirección, cidade ou código postal", Toast.LENGTH_LONG).show();
            return;
        }



        //Pregunto si se quiere guardar los datos a través de un diálogo
        AlertDialog.Builder venta = new AlertDialog.Builder(this);
        venta.setIcon(android.R.drawable.ic_dialog_info);
        venta.setTitle("Resumo da compra");
        venta.setMessage(crearTexto());//texto para mostrar en el dialog, no necesito guardarlo en variable
        venta.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_LONG).show();
                String insert = "INSERT INTO COMPRAS (categoria, producto, usuario, cantidade, direccion, cidade, codigo_postal," +
                        "estado_pedido) VALUES('" + categoria + "','" + producto + "','" + usuario + "','"
                        + cantidade + "','"
                        + direccion + "','" + cidade + "','" + codigoPostal + "'," + BaseDatos.EN_TRAMITE + ")";
                System.out.println(insert);
                BaseDatos.operacionsBD.execSQL(insert);

                //TODO revisar otros modos comprobación guardados
                Cursor cursor = BaseDatos.operacionsBD.rawQuery("select * from COMPRAS", null);
                System.out.println("count" + cursor.getCount());

                volverPanel(usuario);

            }

        });

        venta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                volverPanel(usuario);
            }

        });

        venta.create(); //lo hago directamente, no necesito una referencia al objeto AlertDialog
        venta.show();

    }

    //método para volver al panel de Cliente y borrar actividad actual
    private void volverPanel(String usuario){
        Intent intent = new Intent(getApplicationContext(), Cliente1Panel.class);
        intent.putExtra("usuario", usuario);
        intent.putExtra("nome", getIntent().getExtras().getString("nome"));
        intent.putExtra("apelidos", getIntent().getExtras().getString("apelidos"));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    //método para crear el texto del dialog
    private StringBuilder crearTexto(){
        StringBuilder textoResumo = new StringBuilder();
        textoResumo.append("\tResumo da compra : \n Categoría : " + categoria + "\n");
        textoResumo.append("Producto : " + producto + "\n");
        textoResumo.append("Cantidade : " + cantidade + "\n");
        textoResumo.append("Dirección : " + direccion + "\n");
        textoResumo.append("Cidade : " + cidade + "\n");
        textoResumo.append("Código Postal : " + codigoPostal + "\n");
        return textoResumo;
    }
}
