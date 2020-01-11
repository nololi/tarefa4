package admin_adaptadores_recycler_view_ver_pedidos_aceptados;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtarefa2.R;

public class Admin_Carga_Pedidos_Aceptados extends RecyclerView.ViewHolder {
    //variable elemento layout
    public TextView texto;

    public Admin_Carga_Pedidos_Aceptados(@NonNull View itemView) {
        super(itemView);

        texto = itemView.findViewById(R.id.listado_pedidos_clientes_aceptados);
    }
}
