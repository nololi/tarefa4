package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Rexistro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rexistro);
    }

    /*Al pulsar botón rexistro*/
    public void rexistrar(View view) {
        //noso nome, apelidos, email, usuario, contrasinal e ter que elixir se o usuario é un cliente ou un administrador.
       // Context context = getApplicationContext();
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

        //si está seleccionado es admin, sino es usuario
        boolean isAdmin = (findViewById(R.id.tipo_admin)).isSelected();

        //TODO comprobar si existe
        //realizo una consulta contra la bd con ese usuario si existe
         if(consultarUsuario(usuario)){//si el usuario existe
             Toast.makeText(getApplicationContext(), "El usuario " + usuario+" ya existe", Toast.LENGTH_LONG).show();
             return;
         }

        //Si he llegado aquí no existe -> guardo
        String insert = "INSERT INTO USUARIOS (nome,apelidos,email, usuario, contrasinal,es_Admin)" +
                " VALUES ('"+nome+"','"+apelidos+"','"+email+"','"+usuario+"','"+contrasinal+"',"+((isAdmin)? 1 : 0)+") ";
         BaseDatos.operacionsBD.execSQL(insert);


         //resetear campos
        nomeText.setText("");
        apelidosText.setText("");
        emailText.setText("");
        contrasinalText.setText("");
        usuarioText.setText("");
        findViewById(R.id.tipo_cliente).setSelected(true);
       //  System.out.println(insert);
    }

    public boolean consultarUsuario(String usuario){
       // Toast.makeText(getApplicationContext(), "select nome from USUARIOS where nome="+usuario+"", Toast.LENGTH_LONG).show();
       Cursor cursor = BaseDatos.operacionsBD.rawQuery("select nome from USUARIOS where nome='"+usuario+"'", null);
      // System.out.println(cursor.getCount());
        //no encontrado
        return cursor.getCount() != 0;
//encontrado
    }



}
