package adaptadores_recycler_view_pedidos_admitidos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtarefa2.R;

public class Carga_lista_pedidos_admitidos extends RecyclerView.ViewHolder {

    //variable elemento layout
    public TextView texto;

    public Carga_lista_pedidos_admitidos(@NonNull View itemView) {
        super(itemView);
        texto = itemView.findViewById(R.id.ver_historico_compras);
    }
}
