package com.example.androidtarefa2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import persistencia.BaseDatos;

public class Rexistro extends AppCompatActivity {
    private int esAdmin = 0;
    private ImageView image;

    private int esCamara = 1;//inicialmente es tarjeta

    private final String RUTA_RAIZ = "imagenesAndroid/";
    private final String RUTA_FICHERO = RUTA_RAIZ + "fotosApp";
    private String rutaImagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rexistro);

        //imagen
        image = findViewById(R.id.imagen);

        //crear rutas imágenes si no existen
        File file = new File(RUTA_FICHERO);
        if (!file.exists()) file.mkdirs();
        System.out.println("creada ruta");
    }

    /*Al pulsar botón rexistro*/
    public void rexistrar(View view) {
        //campos del registro
        TextView nomeText = findViewById(R.id.nomeText);
        String nome = nomeText.getText().toString();

        TextView apelidosText = findViewById(R.id.apelidosText);
        String apelidos = apelidosText.getText().toString();

        TextView emailText = findViewById(R.id.emailText);
        String email = emailText.getText().toString();

        TextView usuarioText = findViewById(R.id.usuarioText);
        String usuario = usuarioText.getText().toString();

        TextView contrasinalText = findViewById(R.id.contrasinalText);
        String contrasinal = contrasinalText.getText().toString(); //no codificado


        //realizo una consulta contra la bd con ese usuario si existe
        if (consultarUsuario(usuario)) {//si el usuario existe
            Toast.makeText(getApplicationContext(), "El usuario " + usuario + " ya existe", Toast.LENGTH_LONG).show();
            return;
        }

        //Si he llegado aquí no existe -> guardo

        String insert = "INSERT INTO USUARIOS (nome,apelidos,email, usuario, contrasinal,imaxe,es_admin)" +
                " VALUES ('" + nome + "','" + apelidos + "','" + email + "','" + usuario + "','" + contrasinal + "','" +
                rutaImagen + "'," + esAdmin + ") ";

        System.out.println(insert);
        BaseDatos.operacionsBD.execSQL(insert);

        //redirigir pantalla inicial
        Intent pantalla_inicio = new Intent(this, MainActivity.class);
        startActivity(pantalla_inicio);
    }

    public boolean consultarUsuario(String usuario) {
        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select * from USUARIOS where usuario='" + usuario + "'", null);
        return cursor.getCount() != 0; //devuelve true o false según si se ha encontrado o no
    }


    public void controlSeleccionTipoUsuario(View view) {
        //realizo la comparación por id y asigno  el valor
        switch (view.getId()) {
            case R.id.tipo_admin:
                esAdmin = 1;
                break;
            case R.id.tipo_cliente:
                esAdmin = 0;
                break;

        }
    }

    public void controlFonteImaxe(View view) {
        switch (view.getId()) {
            case R.id.tarxeta:
                System.out.println("tarxeta");
                esCamara = 1;
                break;
            case R.id.camara:
                esCamara = 0;
                System.out.println("camara");
                break;

        }
    }

    //método cuando se pulsa el botón
    public void subirImaxe(View view) {
        System.out.println("Subir imaxe");
        if (esCamara == 0) {
            abrirCamara();//TODO
        } else {//cargar imagen
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //será tipo imagen
            intent.setType("image/");
            startActivityForResult(intent.createChooser(intent, "Seleccione imagen de la galería"), esCamara);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        Bitmap bitMap= (Bitmap) data.getExtras().get("data");
        System.out.println("request code " + requestCode);
        System.out.println(resultCode);
        System.out.println(RESULT_OK);
        if (resultCode == RESULT_OK) {//si todo ok
            System.out.println("result code");
            if (requestCode == 0) {//uso cámara
                System.out.println("ruta" + rutaImagen);
                MediaScannerConnection.scanFile(this, new String[]{rutaImagen},
                        null, new MediaScannerConnection.OnScanCompletedListener() {


                            @Override //método para comprobar si el proceso terminó completamente
                            public void onScanCompleted(String rutaImagen, Uri uri) {
                                System.out.println("Terminó");
                            }
                        });


            } else {//cargo de la galería
                System.out.println("galería");

                Uri path = data.getData();
                image.setImageURI(path);//muestro la imagen en pantalla


                //fuente:https://stackoverflow.com/questions/13209494/how-to-get-the-full-file-path-from-uri
                try {
                    rutaImagen = PathUtil.getPath(getApplicationContext(), path);
                    System.out.println("path util" + rutaImagen);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


            }


        } else if (resultCode == RESULT_CANCELED) {
            System.out.println("Cancelado");
        } else {
            System.out.println("Fallo en la captura");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //convertir el bitmap a blob
    public static byte[] convertirBlob(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }


    private void abrirCamara() {
        String nombreImagen = "";
        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_FICHERO);
        boolean isCreada = fileImagen.exists();//si existe
        if (!isCreada) {
            isCreada = fileImagen.mkdirs();
        }
        if (isCreada) {
            nombreImagen = "imagen.jpg";
        }

        //ruta
        rutaImagen = Environment.getExternalStorageDirectory() + File.separator + RUTA_FICHERO +
                File.separator + nombreImagen;

        //para evitar una excepción
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File imagen = new File(rutaImagen);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//para abrir la cámara
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));//necesario para poder enviar la imagen o almacenarla
        startActivityForResult(intent, 0);//código 0 si es cámara
    }


}
