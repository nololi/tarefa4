package admin_adaptadores_recycler_view_ver_Pedidos_En_Tramite;

import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtarefa2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import persistencia.BaseDatos;


public class Admin_Lista_pedidos_Tramite extends RecyclerView.Adapter {

    private static ArrayList<JSONObject> values = new ArrayList<>();


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // proceso de 'inflar' o layout que conforma cada fila (card_layout_ud04_01_recycleviewcardview.xml) a un obxecto da clase View
        LayoutInflater mInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //el método inflate devuelve la referencia al layout
        View v = mInflater.inflate(R.layout.admin_lista_pedidos_tramite,viewGroup,false);

        //creamos o ViewHolder creando un obxecto da clase ViewHolder creada por nos previamente
        RecyclerView.ViewHolder viewHolder = new Admin_Carga_Pedidos_Tramite(v); //obtengo

        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final JSONObject object;
        String valores;
        try {
            //utilizo todos los vlores menos el Id que lo usaré para la consulta
            object = values.get(position);
            valores  = "Usuario:  " + object.getString("usuario");
            valores +="\nProducto:  "  + object.getString("producto");
            valores+="\nCantidade:  " + object.getString("cantidade");
            valores+="\nDirección:  " + object.getString("direccion");
            valores+="\nCidade:  " + object.getString("cidade");
            valores+="\nCódigo Postal:   " + object.getString("codigo_postal");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }


        //TODO ¿recarga página tras click??
        Admin_Carga_Pedidos_Tramite viewHolderMeu = (Admin_Carga_Pedidos_Tramite) viewHolder;
       Context context = viewHolderMeu.table.getContext();

       TableRow tableRow = new TableRow(context);

        //FORMATO
        LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        parametros.setMargins(8,8,8,8);

        //TEXTO A MOSTRAR
        TextView texto = new TextView(context);
        texto.setText(valores);
        //ajusto el tamaño  al contenido del texto
        texto.setLayoutParams(parametros);

        //BOTON ACEPTAR
        final Button btn_aceptar = new Button(context);
        //ajusto el tamaño  al contenido del botón
        btn_aceptar.setLayoutParams (parametros);
        btn_aceptar.setText(R.string.aceptar_pedido);
        btn_aceptar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    BaseDatos.operacionsBD.execSQL("UPDATE COMPRAS SET estado_pedido='" + BaseDatos.ACEPTADO
                            +"' where _id='"+ object.getString("id") +"'");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        //BOTON REXEITAR
        Button btn_rexeitar = new Button(context);
        //ajusto el tamaño  al contenido del botón
        btn_rexeitar.setLayoutParams (parametros);
        btn_rexeitar.setText(R.string.rexeitar_pedido);
        btn_rexeitar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                try {
                    BaseDatos.operacionsBD.execSQL("UPDATE COMPRAS SET estado_pedido='" + BaseDatos.REXEITADO
                            +"' where _id='"+ object.getString("id") +"'");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);//1
        layout.addView(texto);
        layout.addView(btn_aceptar);
        layout.addView(btn_rexeitar);


        tableRow.addView(layout);
        viewHolderMeu.table.addView(tableRow);


    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void añadirvalores(JSONObject value){
       values.add(value);
    }

    public void resetearLista(){
        //reseteo
        values = new ArrayList<>();
    }


}
