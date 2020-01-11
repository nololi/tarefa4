package admin_adaptadores_recycler_view_ver_Pedidos_En_Tramite;

import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtarefa2.R;

public class Admin_Carga_Pedidos_Tramite extends RecyclerView.ViewHolder {
    public TableLayout table;

    public Admin_Carga_Pedidos_Tramite(@NonNull View itemView) {
        super(itemView);

        //guardo la referencia al elemento para usarlo desde el adaptadores
        table = itemView.findViewById(R.id.tabla_pedidos_tramite);
    }
}
