package admin_adaptadores_recycler_view_ver_Pedidos_En_Tramite;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtarefa2.R;

public class Admin_Carga_Pedidos_Tramite extends RecyclerView.ViewHolder {
    public TextView texto;
    public Button btnAceptar;
    public Button btnRexeitar;

    public Admin_Carga_Pedidos_Tramite(@NonNull View itemView) {
        super(itemView);

        //guardo la referencia al elemento para usarlo desde el adaptadores
        texto = itemView.findViewById(R.id.admin_elemento_pedidos_tramite);
        btnAceptar  = itemView.findViewById(R.id.aceptar);
        btnRexeitar = itemView.findViewById(R.id.rexeitar);
    }
}
