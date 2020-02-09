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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;

import persistencia.BaseDatos;

public class Rexistro extends AppCompatActivity {
    private int esAdmin;
    //campos del registro
    private ImageView image;
    private TextView nomeText;
    private TextView apelidosText;
    private TextView emailText;
    private TextView usuarioText;
    private TextView contrasinalText;
    private TextView contrasinalRepeatCampo;
    private TextView contrasinalRepeatText;
    private RadioButton cliente;
    private RadioButton administrador;
    private Button btnRegistroModificacion;

    //campo de la constraseña antiguar
    private String contrasinalAlmacenada;

    private int esCamara = 1;//inicialmente es tarjeta

    private final String RUTA_RAIZ = "imagenesAndroid/";
    private final String RUTA_FICHERO = RUTA_RAIZ + "appPMDM04";
    private String rutaImagen;

    private static final String TAG = "Rexistro";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rexistro);

        //campos
        nomeText = findViewById(R.id.nomeText);
        apelidosText = findViewById(R.id.apelidosText);
        emailText = findViewById(R.id.emailText);
        usuarioText = findViewById(R.id.usuarioText);
        contrasinalText = findViewById(R.id.contrasinalText);
        cliente = findViewById(R.id.tipo_cliente);
        administrador = findViewById(R.id.tipo_admin);
        contrasinalRepeatCampo = findViewById(R.id.contrasinalRepeat);
        contrasinalRepeatText = findViewById(R.id.contrasinalRepeatText);
        btnRegistroModificacion = findViewById(R.id.registro);

        //imagen
        image = findViewById(R.id.imagen);

        //crear rutas imágenes si no existen
        File file = new File(RUTA_FICHERO);
        if (!file.exists()) {
            file.mkdirs();
            Log.i(TAG, "creada ruta " + file.getAbsolutePath());
        }


        //si vengo de modificación de datos, cargo los datos del cliente
        if (getIntent() != null && getIntent().getExtras() != null) {
            String usuario = getIntent().getExtras().getString("usuario");
            btnRegistroModificacion.setText("Modificar");
            cargaDatos(usuario);
        } else {//si estoy en registro no quiero que se vean los campos y el valor esAdmin es 1 = usuario
            contrasinalRepeatCampo.setVisibility(View.INVISIBLE);
            contrasinalRepeatText.setVisibility(View.INVISIBLE);
            esAdmin=1;
        }
    }

    //si vengo del panel (no es registro) cargo los datos
    private void cargaDatos(String usuario) {
        Cursor cursor = BaseDatos.operacionsBD.rawQuery("select nome,apelidos,es_admin,imaxe,contrasinal,email from USUARIOS where usuario='" + usuario + "' "
                , null);
        if (cursor.moveToFirst()) {
            do {
                //almacenar valores
                nomeText.setText(cursor.getString(0));
                apelidosText.setText(cursor.getString(1));
                usuarioText.setText(usuario);
                usuarioText.setEnabled(false);//no puedo editar el campo

                //esAdmin = cursor.getInt(2); //si es admin marca
                if (Integer.parseInt(cursor.getString(2)) == 1) {
                    System.out.println("es admin");
                    administrador.setChecked(true);
                    cliente.setChecked(false);

                } else {
                    System.out.println("es usuario");
                    administrador.setChecked(false);
                    cliente.setChecked(true);
                }

                //imagen
                rutaImagen = cursor.getString(3);
                Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);
                image.setImageBitmap(bitmap);
                //contrasinal
                contrasinalAlmacenada = cursor.getString(4);
                contrasinalText.setText(contrasinalAlmacenada);
                contrasinalRepeatText.setText(contrasinalAlmacenada);
                emailText.setText(cursor.getString(5));
            } while (cursor.moveToNext());
        }
        System.out.println(usuario);

    }

    /*Al pulsar botón rexistro*/
    public void rexistrar(View view) {//TODO comprobar si los campos están vacíos
        //campos del registro
        String nome = nomeText.getText().toString();
        String apelidos = apelidosText.getText().toString();
        String email = emailText.getText().toString();
        String usuario = usuarioText.getText().toString();
        String contrasinal = contrasinalText.getText().toString(); //no codificado

        if (contrasinalAlmacenada != null) {//es modificación
            String contrasinalRepeat = contrasinalRepeatText.getText().toString();
            if (!contrasinalRepeat.equals(contrasinal)) {
                Log.e(TAG, "Las contraseñas no coinciden");
                Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                return;
            }

            //TODO actualizar solo campos modificados
            String update = "UPDATE USUARIOS SET nome = '" + nome + "'," +
                    " apelidos ='" + apelidos + "'," +
                    " email ='" + email + "'," +
                    " contrasinal ='" + contrasinalRepeat + "'," +
                    " imaxe ='" + rutaImagen + "'," +
                    " es_admin ='" + esAdmin + "'" +
                    " WHERE usuario = '" + usuario + "'";
            BaseDatos.operacionsBD.execSQL(update);
            Log.i(TAG, "Se han actualizado los datos del usuario " + usuario);

            //regreso a mi pantalla : cliente o administrador
            Intent pantalla = new Intent(this, (esAdmin == 0 ? Cliente1Panel.class : AdminPanel.class));
            pantalla.putExtra("nome", nome);//nome
            pantalla.putExtra("apelidos", apelidos); //apelidos
            pantalla.putExtra("usuario", usuario); //usuario
            pantalla.putExtra("rutaImaxe", rutaImagen);
            startActivity(pantalla);
            return;

        }
        //si no he salido, no es modificación es registro ->

        //realizo una consulta contra la bd con ese usuario si existe
        if (consultarUsuario(usuario)) {//si el usuario existe
            Toast.makeText(getApplicationContext(), "El usuario " + usuario + " ya existe", Toast.LENGTH_LONG).show();
            return;
        }

        //Si he llegado aquí no existe -> guardo
        Log.i(TAG, "Guardo nuevo usuario");
        String insert = "INSERT INTO USUARIOS (nome,apelidos,email, usuario, contrasinal,imaxe,es_admin)" +
                " VALUES ('" + nome + "','" + apelidos + "','" + email + "','" + usuario + "','" + contrasinal + "','" +
                rutaImagen + "'," + esAdmin + ") ";

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
                esCamara = 1;
                break;
            case R.id.camara:
                esCamara = 0;
                break;

        }
    }

    //método cuando se pulsa el botón
    public void subirImaxe(View view) {
        if (esCamara == 0) {
            Log.i(TAG, "Selección uso cámara");
            abrirCamara();//TODO
        } else {//cargar imagen
            Log.i(TAG, "Selección imaxe galería");
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //será tipo imagen
            intent.setType("image/");
            startActivityForResult(intent.createChooser(intent, "Seleccione imagen de la galería"), esCamara);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {//si todo ok
            if (requestCode == 0) {//uso cámara
                Bitmap bitmap = BitmapFactory.decodeFile(rutaImagen);
                image.setImageBitmap(bitmap);//muestro la imagen en pantalla

                MediaScannerConnection.scanFile(this, new String[]{rutaImagen},
                        null, new MediaScannerConnection.OnScanCompletedListener() {

                            @Override //método para comprobar si el proceso terminó completamente
                            public void onScanCompleted(String rutaImagen, Uri uri) {
                                Log.i(TAG, "Imagen cámara se cargó correctamente");
                            }
                        });

            } else {//cargo de la galería
                Uri path = data.getData();
                image.setImageURI(path);//muestro la imagen en pantalla

                //fuente:https://stackoverflow.com/questions/13209494/how-to-get-the-full-file-path-from-uri
                try {
                    rutaImagen = PathUtil.getPath(getApplicationContext(), path);
                    Log.i(TAG, "Imagen se cargó correctamente ruta " + rutaImagen);
                    //TODO mejora: clonar las imágenes en directorio app
                } catch (URISyntaxException e) {
                    Log.e(TAG, "Imagen no se cargó correctamente ruta " + rutaImagen);
                    e.printStackTrace();
                }


            }
        } else if (resultCode == RESULT_CANCELED) {
            Log.e(TAG, "Proceso cancelado");
        } else {
            Log.e(TAG, "Fallo al cargar imagen");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //método para usar la cámara
    private void abrirCamara() {
        String nombreImagen = "";
        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_FICHERO);
        System.out.println(fileImagen.getAbsolutePath());
        boolean isCreada = fileImagen.exists();//si existe
        if (!isCreada) {
            isCreada = fileImagen.mkdirs();
            Log.i(TAG, "Creada ruta fichero externo " + fileImagen.getAbsolutePath());
        }
        if (isCreada) {
            nombreImagen = new Date().getTime() + "imagen.jpg";
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
        startActivityForResult(intent, esCamara);//código 0 si es cámara
    }


}
