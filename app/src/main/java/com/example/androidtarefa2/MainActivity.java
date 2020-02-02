package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.sql.Blob;

import persistencia.BaseDatos;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //creación base de datos al lanzar la primera activity
        crearBD();
    }

    /**
     * Validar campos
     */
    public void validarCampos(View view) {
        Context context = getApplicationContext();
        EditText userText = findViewById(R.id.editUsuario);
        String user = userText.getText().toString();
        EditText passText = findViewById(R.id.editContrasinal);
        String pass = passText.getText().toString();
        int esAdmin=-1;
        String nome="";
        String apelidos="";
        String usuario ="";
        String imaxe ="";
      //  byte[] imaxe = new byte[1];
        
        Bitmap bitmap = null;
        //ByteArrayInputStream bais = null;


        //comprobar si existe el usuario
        Cursor cursor = consultarUsuario(user,pass);
        if (cursor.moveToFirst()){
          do {
                //almacenar valores
                nome= cursor.getString(0);
                apelidos = cursor.getString(1);
                esAdmin = cursor.getInt(2);
                usuario = cursor.getString(3);
                imaxe = cursor.getString(4);
                //byte[] imaxe = cursor.getBlob(4);
             // bais = new ByteArrayInputStream(imaxe);
              //bitmap = BitmapFactory.decodeStream(bais);

            //  byte[] blob = cursor.getBlob(4);

                System.out.println(nome +  " " + apelidos + " " + esAdmin +" " + usuario);
        } while(cursor.moveToNext());
       }

        System.out.println(esAdmin);

        if(esAdmin==-1) { //si no hay valor, la consulta no ha devuelto nada
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "login incorrecto", duration);
            toast.show();
        }else if(esAdmin ==1){//admin
            Intent panelAdmin = new Intent(this, AdminPanel.class);
            panelAdmin.putExtra("nome",nome); //nome
            panelAdmin.putExtra("apelidos",apelidos);//apelidos
            panelAdmin.putExtra("rutaImaxe",imaxe);//imaxe
            startActivity(panelAdmin);
        }else{//user
            Intent hacerPedido = new Intent(this, Cliente1Panel.class);
            hacerPedido.putExtra("nome",nome);//nome
            hacerPedido.putExtra("apelidos",apelidos); //apelidos
            hacerPedido.putExtra("usuario",usuario); //usuario
           // hacerPedido.putExtra("imaxe",bitmap);
            //hacerPedido.putExtra("imaxe",blob);
            hacerPedido.putExtra("rutaImaxe",imaxe);
            startActivity(hacerPedido);
        }

        //reseteo los valores  y pongo el foco en el primer campo
        userText.setText("");
        passText.setText("");
        userText.requestFocus();
    }

    /* Botón rexistrarse*/
    public void rexistrar(View view){
       startActivity(new Intent(this,Rexistro.class));
    }

    public void crearBD(){
     // getApplicationContext().deleteDatabase(BaseDatos.NOME_BD);//control: borrado bd
        new BaseDatos(getApplicationContext(),BaseDatos.NOME_BD,null,BaseDatos.VERSION);
    }

    //consultar si el usuario y contraseña existe
    public Cursor consultarUsuario(String usuario,String contrasinal){
        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select nome,apelidos,es_admin,usuario,imaxe from USUARIOS where usuario='"+usuario+"' " +
                "AND contrasinal ='" + contrasinal +"'", null);
        System.out.println("contador encontrado" + cursor.getCount());
        return cursor;
    }






}
