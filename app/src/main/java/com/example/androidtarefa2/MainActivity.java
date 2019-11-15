package com.example.androidtarefa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        boolean isAdmin = ((CheckBox) findViewById(R.id.esAdmin)).isChecked();



        if (isAdmin && user.equals("admin") && pass.equals("abc123.")) {//es admin
            setContentView(R.layout.activity_admin_panel);
        } else if (!isAdmin && user.equals("cliente1") && pass.equals("abc123.")) { //no es admin
            Intent hacerPedido = new Intent(this, Cliente1Panel.class);
            startActivity(hacerPedido);
        } else {//si error muestro un toast
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, "login incorrecto", duration);
            toast.show();
        }
        //reseteo los valores  y pongo el foco en el primer campo
        userText.setText("");
        passText.setText("");
        userText.requestFocus();
    }

}
