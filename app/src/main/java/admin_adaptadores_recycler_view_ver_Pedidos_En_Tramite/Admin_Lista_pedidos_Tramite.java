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
import java.util.Collections;

import persistencia.BaseDatos;


public class Admin_Lista_pedidos_Tramite extends RecyclerView.Adapter {

    private static ArrayList<JSONObject> values = new ArrayList<>();
    private ArrayList<JSONObject> copy = new ArrayList<>();


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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
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


        Admin_Carga_Pedidos_Tramite viewHolderPedidos = (Admin_Carga_Pedidos_Tramite) viewHolder;
       viewHolderPedidos.texto.setText(valores);
        viewHolderPedidos.btnRexeitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BaseDatos.operacionsBD.execSQL("UPDATE COMPRAS SET estado_pedido='" + BaseDatos.ACEPTADO
                            + "' where _id='" + object.getString("id") + "'");
                    values.remove(object);
                    notifyItemRemoved(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        });
        viewHolderPedidos.btnAceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    BaseDatos.operacionsBD.execSQL("UPDATE COMPRAS SET estado_pedido='" + BaseDatos.ACEPTADO
                            +"' where _id='"+ object.getString("id") +"'");
                    values.remove(object);
                    notifyItemRemoved(position);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


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
