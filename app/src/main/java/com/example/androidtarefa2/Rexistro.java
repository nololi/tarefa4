package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import persistencia.BaseDatos;

public class Rexistro extends AppCompatActivity {
    private int esAdmin=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rexistro);
    }

    /*Al pulsar botón rexistro*/
    public void rexistrar(View view) {
        //noso nome, apelidos, email, usuario, contrasinal e ter que elixir se o usuario é un cliente ou un administrador.
        TextView nomeText = findViewById(R.id.nomeText);
        String nome= nomeText.getText().toString();

        TextView apelidosText = findViewById(R.id.apelidosText);
        String apelidos = apelidosText.getText().toString();

        TextView emailText = findViewById(R.id.emailText);
        String email = emailText.getText().toString();

        TextView usuarioText = findViewById(R.id.usuarioText);
        String usuario = usuarioText.getText().toString();

        TextView contrasinalText = findViewById(R.id.contrasinalText);
        String contrasinal = contrasinalText.getText().toString(); //no codificado


        //realizo una consulta contra la bd con ese usuario si existe
         if(consultarUsuario(usuario)){//si el usuario existe
             Toast.makeText(getApplicationContext(), "El usuario " + usuario+" ya existe", Toast.LENGTH_LONG).show();
             return;
         }

        //Si he llegado aquí no existe -> guardo
        String insert = "INSERT INTO USUARIOS (nome,apelidos,email, usuario, contrasinal,es_admin)" +
                " VALUES ('"+nome+"','"+apelidos+"','"+email+"','"+usuario+"','"+contrasinal+"',"+esAdmin+") ";
        BaseDatos.operacionsBD.execSQL(insert);

        //vuelvo a consultar el usuario ¿se ha guardado?
        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select * from USUARIOS where usuario='"+usuario+"'", null);
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "Usuario no guardado", Toast.LENGTH_LONG).show();
        }

         //resetear campos
       // nomeText.setText("");
        //apelidosText.setText("");
        //emailText.setText("");
        //contrasinalText.setText("");
        //usuarioText.setText("");
        //findViewById(R.id.tipo_cliente).setSelected(true);
       //  System.out.println(insert);
    }

    public boolean consultarUsuario(String usuario){
       Cursor cursor = BaseDatos.operacionsBD.rawQuery("select * from USUARIOS where usuario='"+usuario+"'", null);
        return cursor.getCount() != 0; //devuelve true o false según si se ha encontrado o no
    }


    public void controlSeleccionTipoUsuario(View view) {
       //realizo la comparación por id y asigno  el valor
        switch (view.getId()) {
            case R.id.tipo_admin:
                esAdmin=1;
                break;
            case R.id.tipo_cliente:
                esAdmin=0;
                break;

        }
    }



}
