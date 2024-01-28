package com.franklin.interfaces.activity.ui.vista_auto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.franklin.interfaces.R;
import com.franklin.interfaces.activity.models.Auto;
import com.squareup.picasso.Picasso;

public class VistaAutoFragment extends Fragment {
    private NavController navController;
    TextView modelo_marca, precio, matricula, color, potencia, capacidad,categoria;
    ImageView imagen;
    Button reservar;

    public static VistaAutoFragment newInstance() {
        return new VistaAutoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vista_auto, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Obtén los argumentos del fragmento
        Bundle args = getArguments();
        if (args != null) {
            navController = Navigation.findNavController(view);
            if (args.containsKey("objeto_auto")) {
                Auto auto = (Auto) args.getSerializable("objeto_auto");
                modelo_marca = view.findViewById(R.id.tvModeloMarca);
                precio = view.findViewById(R.id.tvPrecio);
                matricula = view.findViewById(R.id.txtDesde);
                color = view.findViewById(R.id.txtHasta);
                potencia = view.findViewById(R.id.tvDias);
                capacidad = view.findViewById(R.id.tvCapacidad);
                categoria = view.findViewById(R.id.tvCategoria);
                imagen = view.findViewById(R.id.ivImagen);
                reservar = view.findViewById(R.id.btnEnviar);
                reservar.setOnClickListener(l-> abrirReserva(auto));
                verAuto(auto);
            }
        }
    }

    private void verAuto(Auto auto) {
        modelo_marca.setText(auto.getModelo().getNombre()+" - "+auto.getMarca().getNombre());
        precio.setText("$" + auto.getPrecio_diario());
        matricula.setText(auto.getMatricula()+"");
        color.setText(auto.getColor()+"");
        potencia.setText(auto.getPotencia()+" hp");
        capacidad.setText(auto.getCapacidad()+" personas");
        categoria.setText(auto.getCategoria().getNombre()+"");

        Picasso.get()
                .load(auto.getUrl_image())
                .error(R.drawable.aveo)
                .into(imagen);
    }

    public void abrirReserva(Auto auto) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("objeto_auto", auto);
        navController.navigate(R.id.nav_reserva, bundle);
    }
}