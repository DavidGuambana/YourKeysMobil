package com.franklin.interfaces.activity.ui.reservas;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franklin.interfaces.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.TextView;

public class ReservasFragment extends Fragment {

    TextView Desde, Hasta;
    public static ReservasFragment newInstance() {
        return new ReservasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reservas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Obtén los argumentos del fragmento
        Bundle args = getArguments();

        if (args != null) {
            /*
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
             */
        }
        Desde = view.findViewById(R.id.txtDesde);
        Hasta = view.findViewById(R.id.txtHasta);
        Desde.setOnFocusChangeListener((l, hasFocus) -> {
            if (hasFocus) {
                abrirCalendario(1);
            }
        });
        Hasta.setOnFocusChangeListener((l, hasFocus) -> {
            if (hasFocus) {
                abrirCalendario(2);
            }
        });
        Desde.setOnClickListener(l-> abrirCalendario(1));
        Hasta.setOnClickListener(l-> abrirCalendario(2));
    }

    // Método para mostrar el selector de fecha
    public void abrirCalendario(int num) {
        int tipoEntradaOriginal = Desde.getInputType();
        if (num == 1){
            Desde.setInputType(InputType.TYPE_NULL);
        } else if (num==2){
            Hasta.setInputType(InputType.TYPE_NULL);
        }
        final Calendar calendario = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, monthOfYear);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Formatear la fecha y establecerla en el EditText
            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            if (num == 1){
                Desde.setInputType(tipoEntradaOriginal);
                Desde.setText(formatoFecha.format(calendario.getTime()));

            } else if (num==2){
                Hasta.setText(formatoFecha.format(calendario.getTime()));
                Hasta.setInputType(tipoEntradaOriginal);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                dateSetListener,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

}