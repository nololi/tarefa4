package admin_adaptadores_recycler_view_ver_Pedidos_En_Tramite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtarefa2.R;

import java.util.ArrayList;

//import adaptadores_recycler_view_ver_pedidos_en_curso.Carga_lista_pedidos_tramite;



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
        viewHolderMeu.itemTexto.setText(values.get(position));
       //botons
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public void añadirvalores(String value){
       values.add(value);
    }
}
