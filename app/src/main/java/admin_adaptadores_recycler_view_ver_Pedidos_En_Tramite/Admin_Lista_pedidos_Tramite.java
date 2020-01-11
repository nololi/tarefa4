package admin_adaptadores_recycler_view_ver_Pedidos_En_Tramite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtarefa2.R;

import java.util.ArrayList;

import persistencia.BaseDatos;


public class Admin_Lista_pedidos_Tramite extends RecyclerView.Adapter {

    private static ArrayList<String> values = new ArrayList<>();


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

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Admin_Carga_Pedidos_Tramite viewHolderMeu = (Admin_Carga_Pedidos_Tramite) viewHolder;
        String id = values.get(position).split("Pedido")[0];
        final String id2 = id.split("_id")[1];


        //TODO ¿recarga página??

       Context context = viewHolderMeu.table.getContext();

        TableRow tableRow = new TableRow(context);

//TODO dar formato

        TextView texto = new TextView(context);
        texto.setText(values.get(position));

        final Button btn_aceptar = new Button(context);
        btn_aceptar.setText(R.string.aceptar_pedido);
        btn_aceptar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
              BaseDatos.operacionsBD.execSQL("UPDATE COMPRAS SET estado_pedido='" + BaseDatos.ACEPTADO +"' where _id='"+ id2 +"'");
            }
        });


        Button btn_rexeitar = new Button(context);
        btn_rexeitar.setText(R.string.rexeitar_pedido);
        btn_rexeitar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                BaseDatos.operacionsBD.execSQL("UPDATE COMPRAS SET estado_pedido='" + BaseDatos.REXEITADO +"' where _id='"+ id2 +"'");
            }
        });

        tableRow.addView(texto);
        tableRow.addView(btn_aceptar);
        tableRow.addView(btn_rexeitar);

        viewHolderMeu.table.addView(tableRow);


    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void añadirvalores(String value){
       values.add(value);
    }

    public void resetearLista(){
        //reseteo
        values = new ArrayList<>();
    }


}
