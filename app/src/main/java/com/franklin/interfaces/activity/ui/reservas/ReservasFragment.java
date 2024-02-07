package com.franklin.interfaces.activity.ui.reservas;


import android.app.Service;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.widget.AdapterView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franklin.interfaces.R;
import com.franklin.interfaces.activity.models.Alquiler;
import com.franklin.interfaces.activity.models.Auto;
import com.franklin.interfaces.activity.models.Proteccion;
import com.franklin.interfaces.activity.services.RUTA;
import com.franklin.interfaces.activity.services.serCrear;
import com.franklin.interfaces.activity.services.serCrear.ServiceCallback;
import com.franklin.interfaces.activity.services.serListar;
import com.franklin.interfaces.activity.ui.login.Login;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import android.app.DatePickerDialog;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReservasFragment extends Fragment{
    private NavController navController;
    Context context;
    Button btnEnviar;
    TextView modelo_marca, precio, Desde, Hasta,dias, total;
    ImageView imagen, btnDesde, btnHasta;
    Spinner spin_proteccion;
    private serListar service_get;
    private serCrear service_post;
    private List<Proteccion>protecciones;
    private Alquiler alquiler;
    private Auto auto;
    public Proteccion proteccion;
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

        context = getContext();
        service_get = new serListar(context);
        service_post = new serCrear(context);

        modelo_marca = view.findViewById(R.id.tvModeloMarca);
        precio = view.findViewById(R.id.tvPrecio);
        imagen = view.findViewById(R.id.ivImagen);
        Desde = view.findViewById(R.id.txtDesde);
        Hasta = view.findViewById(R.id.txtHasta);
        btnDesde = view.findViewById(R.id.btnDesde);
        btnHasta = view.findViewById(R.id.btnHasta);
        btnEnviar = view.findViewById(R.id.btnEnviar);
        spin_proteccion = view.findViewById(R.id.spinner_proteccion);
        dias = view.findViewById(R.id.tvDias);
        total = view.findViewById(R.id.tvTotal);
        btnDesde.setOnClickListener(l-> abrirCalendario(1));
        btnHasta.setOnClickListener(l-> abrirCalendario(2));
        btnEnviar.setOnClickListener(l-> crearAlquiler());

        Bundle args = getArguments();

        if (args != null) {
            navController = Navigation.findNavController(view);
            if (args.containsKey("objeto_auto")) {
                Auto auto = (Auto) args.getSerializable("objeto_auto");
                cargarDatos(auto);
            }
            if (args.containsKey("objeto_alquiler")) {
                Alquiler alquiler = (Alquiler) args.getSerializable("objeto_alquiler");
                this.alquiler = alquiler;
                cargarAlquiler(alquiler);
            }
        }

    }

    private void cargarDatos(Auto auto) {
        this.auto = auto;
        modelo_marca.setText(auto.getModelo().getNombre()+" - "+auto.getMarca().getNombre());
        precio.setText("$" + auto.getPrecio_diario());
        Picasso.get()
                .load(RUTA.getUrlFoto(auto.getUrl_image()))
                .error(R.drawable.aveo)
                .into(imagen);
        getProtecciones();
    }

    private void cargarAlquiler(Alquiler alq){
        cargarDatos(alq.getAuto());
        Desde.setText(dateToString(alq.getFecha_ini()));
        Hasta.setText(dateToString(alq.getFecha_fin()));
        dias.setText(getDias(Desde.getText().toString(), Hasta.getText().toString()) + "");
        total.setText(String.valueOf(alq.getTotal()));
    }

    private void getProtecciones() {
        service_get.listar("protecciones", new serListar.ServiceCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                protecciones = new ArrayList<>();
                List<String> txtProtecciones = new ArrayList<>();
                try {
                    if (response.length() > 0) {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject prot = response.getJSONObject(i);
                            Proteccion proteccion = new Proteccion();
                            proteccion.setId_proteccion(prot.getInt("id_proteccion"));
                            proteccion.setNombre(prot.getString("nombre"));
                            proteccion.setPrecio(prot.getDouble("precio"));
                            protecciones.add(proteccion);

                            // Agregar solo el nombre a la lista
                            txtProtecciones.add("($"+proteccion.getPrecio()+") - "+proteccion.getNombre());
                        }

                        ArrayAdapter<String> adaptador = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, txtProtecciones);
                        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spin_proteccion.setAdapter(adaptador);
                        spin_proteccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                /*
                                if(alquiler != null){
                                    System.out.println("id_alquiler:"+alquiler.getId_alquiler());
                                    position = alquiler.getId_proteccion()-1;
                                }
                                 */
                                Proteccion protSeleccionada = protecciones.get(position);
                                proteccion = protSeleccionada;
                                calcularTotal();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                                // Acción a realizar cuando no se selecciona ningún elemento
                            }
                        });
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Manejar el error aquí si es necesario
            }
        });
    }

    public void abrirCalendario(int num) {
        final Calendar calendario = Calendar.getInstance();

        // Establecer el límite mínimo como la fecha actual
        long fechaMinima = System.currentTimeMillis() - 1000;

        // Establecer el límite máximo como hoy más 30 días
        long fechaMaxima = System.currentTimeMillis() + (29L * 24L * 60L * 60L * 1000L);

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
            if (num == 1) {
                Desde.setText(formatoFecha.format(calendario.getTime()));
                if (!Hasta.getText().toString().isEmpty()) {
                    if(getDias(Desde.getText().toString(), Hasta.getText().toString())>=0){
                        dias.setText(getDias(Desde.getText().toString(), Hasta.getText().toString()) + "");
                        calcularTotal();
                    } else{
                        Toast.makeText(getContext(), "La fecha de inicio es mayor a la fecha fin", Toast.LENGTH_SHORT).show();
                        dias.setText("0");
                    }
                }
            } else if (num == 2) {
                Hasta.setText(formatoFecha.format(calendario.getTime()));
                if (!Desde.getText().toString().isEmpty()) {
                    if (getDias(Desde.getText().toString(), Hasta.getText().toString())>=0){
                        dias.setText(getDias(Desde.getText().toString(), Hasta.getText().toString()) + "");
                        calcularTotal();
                    } else{
                        Toast.makeText(getContext(), "La fecha de fin es mayor a la fecha de inico", Toast.LENGTH_SHORT).show();
                        dias.setText("0");
                    }
                    calcularTotal();
                }
            }
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

    public void crearAlquiler() {
        if (dias.getText().toString().equals("0")) {
            Toast.makeText(context, "Seleccione una fecha de inicio y fin correcta", Toast.LENGTH_LONG).show();
        } else {
            JSONObject JSO_alquiler = new JSONObject();
            try {
                if(alquiler != null){
                    JSO_alquiler.put("id_alquiler",alquiler.getId_alquiler());
                    JSO_alquiler.put("fecha_res",StringToDate(dateToString(alquiler.getFecha_res())));
                    JSO_alquiler.put("fecha_reg",StringToDate(dateToString(alquiler.getFecha_reg())));
                }
                JSO_alquiler.put("id_cliente", Login.persona.getCliente().getId_cliente());
                JSO_alquiler.put("id_auto", auto.getId_auto());
                JSO_alquiler.put("id_proteccion", proteccion.getId_proteccion());
                JSO_alquiler.put("fecha_ini", StringToDate(Desde.getText().toString()));
                JSO_alquiler.put("fecha_fin", StringToDate(Hasta.getText().toString()));
                JSO_alquiler.put("precio_auto", auto.getPrecio_diario());
                JSO_alquiler.put("precio_proteccion", proteccion.getPrecio());
                JSO_alquiler.put("total", xtotal);
                JSO_alquiler.put("pagado", false);
                JSO_alquiler.put("reservado", true);
                service_post.crear("alquileres", JSO_alquiler, new ServiceCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        if (response != null) {
                            if(alquiler != null){
                                Toast.makeText(context, "Tu reserva se actualizó exitosamente!", Toast.LENGTH_LONG).show();
                                navController.navigate(R.id.nav_historial);
                            } else{
                                Toast.makeText(context, "Tu reserva se envió exitosamente!", Toast.LENGTH_LONG).show();
                                navController.navigate(R.id.nav_autos);
                            }
                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                // Manejar la excepción según tu lógica
                e.printStackTrace();
                Toast.makeText(context, "Error al crear el alquiler:"+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
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

    private double xtotal = 0;
    public void calcularTotal() {
        int xdias = Integer.parseInt(dias.getText().toString());
        xtotal = ((auto.getPrecio_diario() * xdias) + (proteccion.getPrecio() * xdias)) * 1.12;

        // Truncar xtotal a dos decimales usando BigDecimal
        BigDecimal bd = new BigDecimal(xtotal).setScale(2, RoundingMode.HALF_UP);
        xtotal = bd.doubleValue();

        total.setText("$" + xtotal);
    }
    private int getDias(String fechaInicioStr, String fechaFinStr) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date fechaInicio = formatoFecha.parse(fechaInicioStr);
            Date fechaFin = formatoFecha.parse(fechaFinStr);

            long diferenciaMillis = fechaFin.getTime() - fechaInicio.getTime();
            long diferenciaDias = TimeUnit.DAYS.convert(diferenciaMillis, TimeUnit.MILLISECONDS);

            return (int) diferenciaDias+1;

        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}