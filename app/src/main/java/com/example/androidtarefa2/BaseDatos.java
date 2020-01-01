package com.example.androidtarefa2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {

    //modo "sencillo" uso misma referencia bd : crear propiedad
    public static SQLiteDatabase operacionsBD; //para manejar desde otras clases


    /*
     *Constructor con parámetros
     */
    public BaseDatos(Context context, String nome_BD, SQLiteDatabase.CursorFactory factory , int version_bd) {
        super(context, nome_BD, factory, version_bd);
    }

    /*
    nome, apelidos, email, usuario, contrasinal e ter que elixir se o usuario é un cliente ou un administrador.
     */

    @Override
    public void onCreate(SQLiteDatabase db) { //creación tabla
        String crear_taboa_usuarios ="CREATE TABLE USUARIOS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "nome VARCHAR(50) NOT NULL,"+
                "apelidos VARCHAR(50) NOT NULL,"+
                "email VARCHAR(50) NOT NULL,"+
                "usuario VARCHAR(50) NOT NULL,"+
                "contrasinal VARCHAR(50) NOT NULL,"+
                "es_admin BOOLEAN NOT NULL)"; // 0= falso se almacenan como enteros
        db.execSQL(crear_taboa_usuarios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //TODO destroy

    public void asigarSQLiteDatabase(SQLiteDatabase operacionsBD){
        BaseDatos.operacionsBD = operacionsBD;
    }


}
