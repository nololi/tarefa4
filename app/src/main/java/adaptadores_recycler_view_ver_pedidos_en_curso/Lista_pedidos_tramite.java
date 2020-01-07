package adaptadores_recycler_view_ver_pedidos_en_curso;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtarefa2.R;

import java.util.ArrayList;


public class Lista_pedidos_tramite extends RecyclerView.Adapter {


    private static ArrayList<String> values = new ArrayList<>();

    /*
    Este método ten que devolver un obxecto da clase ViewHolder a cal é empregada
     para visualizar o contido da lista. Será na clase ViewHolder onde 'cargaremos' o deseño de fila feito no paso anterior.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // proceso de 'inflar' o layout que conforma cada fila (card_layout_ud04_01_recycleviewcardview.xml) a un obxecto da clase View
        LayoutInflater mInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //el método inflate devuelve la referencia al layout
        View v = mInflater.inflate(R.layout.lista_pedidos_tramite,viewGroup,false);

        //creamos o ViewHolder creando un obxecto da clase ViewHolder creada por nos previamente
        RecyclerView.ViewHolder viewHolder = new Carga_lista_pedidos_tramite(v); //obtengo

        return viewHolder;
    }

    //Este método recibe o ViewHolder do método anterior e asociamos a cada compoñente gráfico de dito View o dato que queremos que visualice
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Carga_lista_pedidos_tramite viewHolderMeu = (Carga_lista_pedidos_tramite) viewHolder;
        viewHolderMeu.itemTexto.setText(values.get(position));
    }

    //Número de elementos de la lista
    @Override
    public int getItemCount() {
        return values.size();
    }


    public void añadirvalores(String value){
        values.add(value);
    }


}
