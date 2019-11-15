package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class HacerPedido extends AppCompatActivity {

    private ArrayList<String> electronica_productos = new ArrayList<String>();
    private ArrayAdapter<String> electronica;

    private ArrayList<String> informatica_productos = new ArrayList<String>();
    private ArrayAdapter<String> informatica;

    private ArrayList<String> mobiles_productos = new ArrayList<String>();
    private ArrayAdapter<String> mobiles;

    private Spinner categoriaSeleccion;
    private Spinner productosSeleccion;
    private Spinner cantidadeSeleccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hacer_pedido);

        //asigno los elementos gráficos
        categoriaSeleccion = findViewById(R.id.Categoria_seleccion);
        productosSeleccion = findViewById(R.id.Producto_seleccion);
        cantidadeSeleccion = findViewById(R.id.Cantidad_seleccion);
        iniciarProductos();//inicio las listas de los productos
        cargarProductos(); //cargo los productos según categoría


    }

    //Método para pasar los valores de los campos seleccionados a la siguiente pantalla
    public void pulsarSeguinte(View view) {
        Intent direccionPedido = new Intent(this, DireccionPedido.class);

        direccionPedido.putExtra("categoria", categoriaSeleccion.getSelectedItem().toString());
        direccionPedido.putExtra("producto", productosSeleccion.getSelectedItem().toString());
        direccionPedido.putExtra("cantidade", cantidadeSeleccion.getSelectedItem().toString());

        startActivity(direccionPedido);
    }


    //Agrego los productos de las diferentes categorías a su arrayAdapter
    private void iniciarProductos() {
        //Electrónica
        electronica_productos.add("Televisión");
        electronica_productos.add("DVD");
        //ArrayAdapter para cargarlo en el spinner si selecciona Electrónica
        electronica = new ArrayAdapter<String>(HacerPedido.this, android.R.layout.simple_spinner_dropdown_item, electronica_productos);
        electronica.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Informática
        informatica_productos.add("Pc de sobremesa");
        informatica_productos.add("Portátil");
        informatica_productos.add("Monitor");
        //ArrayAdapter para cargarlo en el spinner si selecciona Informática
        informatica = new ArrayAdapter<String>(HacerPedido.this, android.R.layout.simple_spinner_dropdown_item, informatica_productos);
        informatica.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Móbiles
        mobiles_productos.add("Pixel");
        mobiles_productos.add("Galaxy");
        mobiles_productos.add("Iphone");
        mobiles_productos.add("Xiaomi");
        //ArrayAdapter para cargarlo en el spinner si selecciona Informática
        mobiles = new ArrayAdapter<String>(HacerPedido.this, android.R.layout.simple_spinner_dropdown_item, mobiles_productos);
        mobiles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    }

    //Cargo los valores de productos según categoría
    private void cargarProductos() {
        categoriaSeleccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                switch (pos) {
                    case 0:
                        productosSeleccion.setAdapter(informatica);
                        break;
                    case 1:
                        productosSeleccion.setAdapter(electronica);
                        break;
                    case 2:
                        productosSeleccion.setAdapter(mobiles);
                        break;

                }

            }


            @Override

            public void onNothingSelected(AdapterView<?> arg0) {

                // TODO Auto-generated method stub


            }

        });

    }
}
