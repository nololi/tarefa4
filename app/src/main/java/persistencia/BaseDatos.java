package persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseDatos extends SQLiteOpenHelper {
    public static String NOME_BD ="TENDAPMDM03";
    public static int VERSION =1;
    public static int EN_TRAMITE=0;
    public static int ACEPTADO=1;
    public static int REXEITADO=2;

    //modo "sencillo" uso misma referencia bd : crear propiedad
    public static SQLiteDatabase operacionsBD; //para manejar desde otras clases

    /*
     *Constructor con parámetros
     */
    public BaseDatos(Context context, String nome_BD, SQLiteDatabase.CursorFactory factory , int version_bd) {
        super(context, nome_BD, factory, version_bd);
        //guardado referencia a la base de datos
        SQLiteDatabase operacionsBD = this.getWritableDatabase();
        this.asigarSQLiteDatabase(operacionsBD);
       //operacionsBD.execSQL("DROP TABLE IF EXISTS COMPRAS");
       //operacionsBD.execSQL("DROP TABLE IF EXISTS USUARIOS");
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
                //"imaxe BLOB NOT NULL,"+
                "imaxe VARCHAR(200) NOT NULL,"+
                "es_admin BOOLEAN NOT NULL)"; // 0= falso se almacenan como enteros
        db.execSQL(crear_taboa_usuarios);


        //categoría, producto, cantidade, dirección, cidade e código postal, estado pedido
        String crear_taboa_compras ="CREATE TABLE COMPRAS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "categoria VARCHAR(50) NOT NULL,"+
                "producto VARCHAR(50) NOT NULL,"+
                "cantidade VARCHAR(50) NOT NULL,"+
                "usuario VARCHAR(50) NOT NULL,"+ //TODO FK
                "direccion VARCHAR(50) NOT NULL,"+
                "cidade VARCHAR(50) NOT NULL,"+
                "codigo_postal INTEGER(5) NOT NULL,"+
                "estado_pedido INTEGER(1) NOT NULL)"; // 0 = trámite, 1 = aceptado, 2 = rexeitado
        db.execSQL(crear_taboa_compras);

        //tabla imágenes
       // String tabla_imagenes = "CREATE TABLE IMAGENES (id INTEGER PRIMARY KEY AUTOINCREMENT, img BLOB)";
        //db.execSQL(tabla_imagenes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //TODO destroy


    private void asigarSQLiteDatabase(SQLiteDatabase operacionsBD){
        BaseDatos.operacionsBD = operacionsBD;
    }



}
