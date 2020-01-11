package admin_adaptadores_recycler_view_ver_pedidos_rexeitados;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtarefa2.R;

public class Admin_Carga_Pedidos_Rexeitados extends RecyclerView.ViewHolder {
    public TextView texto;

    public Admin_Carga_Pedidos_Rexeitados(@NonNull View itemView) {
        super(itemView);

        texto = itemView.findViewById(R.id.listado_pedidos_clientes_rexeitados);
    }
}
