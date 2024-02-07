package com.franklin.interfaces.activity.ui.editPerfil;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.franklin.interfaces.R;
import com.franklin.interfaces.activity.models.Persona;
import com.franklin.interfaces.activity.services.RUTA;
import com.franklin.interfaces.activity.services.serActualizar;
import com.franklin.interfaces.activity.services.serCrear;
import com.franklin.interfaces.activity.ui.login.Login;
import com.franklin.interfaces.databinding.FragmentEditPerfilBinding;
import com.franklin.interfaces.databinding.FragmentPerfilBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditPerfilFragment extends Fragment {
    private FragmentPerfilBinding binding;
    private NavController navController;
    Context context;
    private EditText username,password, nombre1, nombre2, apellido1,
            apellido2, telefono, correo, direccion;
    private TextView licencia,fecha_nac;
    private ImageView foto, btn_abrir_foto, btn_calendar;
    private Button btn_guardar;
    private Spinner spin_tipo_licencia;
    private serActualizar service;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        context = getActivity().getApplicationContext();
        service = new serActualizar(context);

        spin_tipo_licencia = view.findViewById(R.id.spinner_tipo_licencia);
        username = view.findViewById(R.id.tv_user_name);
        password = view.findViewById(R.id.tv_password);
        foto = view.findViewById(R.id.iv_foto);
        btn_abrir_foto = view.findViewById(R.id.btn_abrir_foto);
        btn_calendar = view.findViewById(R.id.btn_ver_calendar);
        btn_guardar = view.findViewById(R.id.btnGuardar);
        licencia = view.findViewById(R.id.tv_licencia);
        nombre1 = view.findViewById(R.id.tv_nombre1);
        nombre2 = view.findViewById(R.id.tv_nombre2);
        apellido1 = view.findViewById(R.id.tv_apellido1);
        apellido2 = view.findViewById(R.id.tv_apellido2);
        fecha_nac = view.findViewById(R.id.tv_fecha_nac);
        telefono = view.findViewById(R.id.tc_telefono);
        correo = view.findViewById(R.id.tv_correo);
        direccion = view.findViewById(R.id.tv_direccion);
        cargarDatos();
        btn_calendar.setOnClickListener(l-> abrirCalendario());
        btn_abrir_foto.setOnClickListener(l->abrir_galeria());
        btn_guardar.setOnClickListener(l-> guardar());
    }

    public void guardar() {
        if (    username.getText().toString().trim().equals("") ||
                password.getText().toString().trim().equals("") ||
                licencia.getText().toString().trim().equals("") ||
                nombre1.getText().toString().trim().equals("") ||
                nombre2.getText().toString().trim().equals("") ||
                apellido1.getText().toString().trim().equals("") ||
                apellido2.getText().toString().trim().equals("") ||
                fecha_nac.getText().toString().trim().equals("") ||
                telefono.getText().toString().trim().equals("") ||
                correo.getText().toString().trim().equals("") ||
                direccion.getText().toString().trim().equals("")) {
            Toast.makeText(context, "Todos los campos deben ser completados", Toast.LENGTH_LONG).show();
        } else {
            JSONObject JSB_persona = new JSONObject();
            try {
                JSB_persona.put("cedula", licencia.getText().toString());
                JSB_persona.put("nombre1", nombre1.getText().toString());
                JSB_persona.put("nombre2", nombre2.getText().toString());
                JSB_persona.put("apellido1", apellido1.getText().toString());
                JSB_persona.put("apellido2", apellido2.getText().toString());
                JSB_persona.put("telefono", telefono.getText().toString());
                JSB_persona.put("direccion", direccion.getText().toString());
                JSB_persona.put("fecha_nac", StringToDate(fecha_nac.getText().toString()));
                JSB_persona.put("correo", correo.getText().toString());
                service.actualizar("personas/"+Login.persona.getId_persona(), JSB_persona, new serActualizar.ServiceCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        if (response != null) {
                            JSONObject JSB_cliente = new JSONObject();
                            try {
                                JSB_cliente.put("licencia", Login.persona.getCliente().getLicencia());
                                JSB_cliente.put("tipo_licencia", Login.persona.getCliente().getTipo_licencia());
                                JSB_cliente.put("id_persona", Login.persona.getId_persona());

                                service.actualizar("clientes/"+Login.persona.getCliente().getId_cliente(), JSB_cliente, new serActualizar.ServiceCallback() {
                                    @Override
                                    public void onSuccess(Object response) {
                                        if (response != null) {
                                            JSONObject JSB_usuario = new JSONObject();
                                            try {
                                                JSB_usuario.put("id_persona", Login.persona.getUsuario().getId_persona());
                                                JSB_usuario.put("username", username.getText().toString());
                                                JSB_usuario.put("password", password.getText().toString());
                                                service.actualizar("usuarios/"+Login.persona.getUsuario().getId_usuario(), JSB_usuario, new serActualizar.ServiceCallback() {
                                                    @Override
                                                    public void onSuccess(Object response) {
                                                        if (response != null) {
                                                            Toast.makeText(context, "Tu cuenta ha sido actualizada exitosamente!", Toast.LENGTH_LONG).show();
                                                        } else{
                                                            Toast.makeText(context, "Tu cuenta no pudo ser actualizada!", Toast.LENGTH_LONG).show();
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(String errorMessage) {
                                                        Toast.makeText(context, "Error al actualizar usuario: " + errorMessage, Toast.LENGTH_LONG).show();
                                                    }
                                                });

                                            } catch (JSONException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }
                                    @Override
                                    public void onError(String errorMessage) {
                                        Toast.makeText(context, "Error al actualizar cliente: " + errorMessage, Toast.LENGTH_LONG).show();
                                    }
                                });

                            } catch (JSONException e) {
                                Toast.makeText(context, "Error al actualizar cliente: " + e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(context, "Error al actualizar persona: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void abrir_galeria(){

    }

    private void cargarDatos(){
        licencia.setText(Login.persona.getCedula());
        username.setText(Login.persona.getUsuario().getUsername());
        password.setText(Login.persona.getUsuario().getPassword());
        nombre1.setText(Login.persona.getNombre1());
        nombre2.setText(Login.persona.getNombre2());
        apellido1.setText(Login.persona.getApellido1());
        apellido2.setText(Login.persona.getApellido2());
        fecha_nac.setText(dateToString(Login.persona.getFecha_nac()));
        telefono.setText(Login.persona.getTelefono());
        correo.setText(Login.persona.getCorreo());
        direccion.setText(Login.persona.getDireccion());
        Picasso.get()
                .load(RUTA.getUrlFoto(Login.persona.getUrl_imagen()))
                .error(R.drawable.perfil)
                .into(foto);
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

    public void abrirCalendario() {
        final Calendar calendario = Calendar.getInstance();

        long cienAniosEnMillis = 100L * 365L * 24L * 60L * 60L * 1000L;
        long fechaMinima = System.currentTimeMillis() - cienAniosEnMillis;

        // Establecer el límite máximo hoy:
        long fechaMaxima = System.currentTimeMillis();

        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, monthOfYear);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // Configurar la fecha actual sin tener en cuenta la parte del tiempo
            Calendar fechaActual = Calendar.getInstance();
            fechaActual.set(Calendar.HOUR_OF_DAY, 0);
            fechaActual.set(Calendar.MINUTE, 0);
            fechaActual.set(Calendar.SECOND, 0);
            fechaActual.set(Calendar.MILLISECOND, 0);

            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            fecha_nac.setText(formatoFecha.format(calendario.getTime()));
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                dateSetListener,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(fechaMinima);
        datePickerDialog.getDatePicker().setMaxDate(fechaMaxima);
        datePickerDialog.show();
    }

    private void abrirPefil(){
        Bundle bundle = new Bundle();
        navController.navigate(R.id.nav_perfil, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}