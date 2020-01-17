package admin_adaptadores_recycler_view_ver_pedidos_aceptados;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtarefa2.R;

import java.util.ArrayList;

public class Admin_Lista_Pedidos_Aceptados extends RecyclerView.Adapter {
    //Lista elementos
    private static ArrayList<String> values = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // proceso de 'inflar' o layout que conforma cada fila (card_layout_ud04_01_recycleviewcardview.xml) a un obxecto da clase View
        LayoutInflater mInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //el método inflate devuelve la referencia al layout
        View v = mInflater.inflate(R.layout.admin_lista_pedidos_admitidos,parent,false);

        //creamos o ViewHolder creando un obxecto da clase ViewHolder creada por nos previamente
        RecyclerView.ViewHolder viewHolder = new Admin_Carga_Pedidos_Aceptados(v); //obtengo

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Admin_Carga_Pedidos_Aceptados viewHolderPedidos = (Admin_Carga_Pedidos_Aceptados) viewHolder;
        viewHolderPedidos.texto.setText(values.get(position));
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
