package adaptadores_recycler_view_ver_pedidos_en_curso;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtarefa2.R;

//Esta clase vai ser a que nos permite acceder aos elementos gráficos definidos no CardView (o que está definido no layout que representa cada fila).
public class Carga_lista_pedidos_tramite extends RecyclerView.ViewHolder {

    public TextView itemTexto;

    public Carga_lista_pedidos_tramite(@NonNull View itemView) {
        super(itemView);
        //guardo la referencia al elemento para usarlo desde el adaptadores
        itemTexto = itemView.findViewById(R.id.pedido_listado);
    }
}
