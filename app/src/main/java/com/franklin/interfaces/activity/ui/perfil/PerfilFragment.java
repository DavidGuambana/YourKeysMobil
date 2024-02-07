package com.franklin.interfaces.activity.ui.perfil;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.franklin.interfaces.R;
import com.franklin.interfaces.activity.models.Persona;
import com.franklin.interfaces.activity.services.RUTA;
import com.franklin.interfaces.activity.ui.login.Login;
import com.franklin.interfaces.activity.ui.reservas.ReservasFragment;
import com.franklin.interfaces.databinding.FragmentPerfilBinding;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PerfilFragment extends Fragment {

    private NavController navController;
    private ReservasFragment fecha = new ReservasFragment();
    private TextView licencia, nombres, apellidos, fecha_nac, username,
            telefono, correo, direccion, fecha_reg;
    private ImageView foto, btn_Editar;
    Context context;

    private FragmentPerfilBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        context = getActivity().getApplicationContext();

        username = view.findViewById(R.id.tv_username);
        foto = view.findViewById(R.id.iv_foto);
        btn_Editar = view.findViewById(R.id.btn_editar);
        licencia = view.findViewById(R.id.tv_licencia);
        nombres = view.findViewById(R.id.tv_nombre1);
        apellidos = view.findViewById(R.id.tv_apellido1);
        fecha_nac = view.findViewById(R.id.tv_fecha_nac);
        telefono = view.findViewById(R.id.tc_telefono);
        correo = view.findViewById(R.id.tv_correo);
        direccion = view.findViewById(R.id.tv_direccion);
        fecha_reg = view.findViewById(R.id.tv_fecha_reg);
        cargarDatos();
        btn_Editar.setOnClickListener(l-> abrirEdicion());
    }

    private void cargarDatos(){
        Persona persona = Login.persona;
        username.setText(persona.getUsuario().getUsername());
        licencia.setText(persona.getCedula());
        nombres.setText(persona.getNombre1()+" "+persona.getNombre2());
        apellidos.setText(persona.getApellido1()+" "+persona.getApellido2());
        fecha_nac.setText(dateToString(persona.getFecha_nac()));
        telefono.setText(persona.getTelefono());
        correo.setText(persona.getCorreo());
        direccion.setText(persona.getDireccion());
        fecha_reg.setText(dateToString(persona.getFecha_reg()));
        Picasso.get()
                .load(RUTA.getUrlFoto(persona.getUrl_imagen()))
                .error(R.drawable.perfil)
                .into(foto);
    }

    private void abrirEdicion(){
        Bundle bundle = new Bundle();
        navController.navigate(R.id.nav_edit_perfil, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private java.sql.Date StringToDate(String txt) throws ParseException {
        // Establecer el formato de la fecha según el formato en tu TextView (por ejemplo, "yyyy-MM-dd")
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Parsear el texto a un objeto Date
            return new java.sql.Date(formatoFecha.parse(txt).getTime());
        } catch (ParseException e) {
            // Manejar la excepción en caso de un formato incorrecto
            e.printStackTrace();
            throw e; // O lanzar una excepción específica según tu lógica
        }
    }

    private String dateToString(Date date) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return formatoFecha.format(date);
    }
}