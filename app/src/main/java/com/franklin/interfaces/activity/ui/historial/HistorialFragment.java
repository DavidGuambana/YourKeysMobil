package com.franklin.interfaces.activity.ui.historial;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.franklin.interfaces.R;
import com.franklin.interfaces.activity.models.Alquiler;
import com.franklin.interfaces.activity.models.Auto;
import com.franklin.interfaces.activity.models.Categoria;
import com.franklin.interfaces.activity.models.Devolucion;
import com.franklin.interfaces.activity.models.Marca;
import com.franklin.interfaces.activity.models.Modelo;
import com.franklin.interfaces.activity.services.serEliminar;
import com.franklin.interfaces.activity.services.serListar;
import com.franklin.interfaces.activity.ui.login.Login;
import com.franklin.interfaces.activity.utils.FECHA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistorialFragment extends Fragment {
    private NavController navController;
    private serListar service;
    private serEliminar service_elim;
    FECHA fecha = new FECHA();
    ArrayList<Alquiler> alquileres;
    ArrayList<Auto> autos;
    List <Modelo> modelos;
    List <Marca> marcas;
    List <Categoria> categorias;
    public static HistorialFragment newInstance() {
        return new HistorialFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_historial, container, false);
    }

    Context context;
    TableLayout tlAlquileres, tlReservas;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        context = getActivity().getApplicationContext();
        tlAlquileres = view.findViewById(R.id.tlAlquileres);
        tlReservas = view.findViewById(R.id.tlReservas);
        service = new serListar(context);
        service_elim = new serEliminar(context);
        alquileres = new ArrayList<>();
        getModelos();
    }

    public void cargarTablas(){
        List <Alquiler> alq = new ArrayList<>();
        tlReservas.removeAllViews();
        tlAlquileres.removeAllViews();
        int id_cliente = Login.persona.getCliente().getId_cliente();
        for (int k=0; k<alquileres.size(); k++){
            if(alquileres.get(k).getId_cliente() == id_cliente){
                alq.add(alquileres.get(k));
            }
        }
        if (alq.size()>0){
            View col_reservas = LayoutInflater.from(context).inflate(R.layout.col_reservas, null, false);
            tlReservas.addView(col_reservas);

            View col_alquileres = LayoutInflater.from(context).inflate(R.layout.col_alquileres, null, false);
            tlAlquileres.addView(col_alquileres);
            for (int i = 0; i < alq.size(); i++) {
                Alquiler xalquiler = alq.get(i);
                if (xalquiler.isPagado()) { // cargar alquileres:
                    View row_alquiler = LayoutInflater.from(context).inflate(R.layout.row_alquileres, null, false);
                    TextView r1_a = row_alquiler.findViewById(R.id.r1_a);
                    TextView r2_a = row_alquiler.findViewById(R.id.r2_a);
                    TextView r3_a = row_alquiler.findViewById(R.id.r3_a);
                    TextView r4_a = row_alquiler.findViewById(R.id.r4_a);

                    r1_a.setText(xalquiler.getAuto().getModelo().getNombre()+" - "+xalquiler.getAuto().getMarca().getNombre());
                    r2_a.setText("$"+xalquiler.getTotal());
                    r3_a.setText(fecha.dateToString(xalquiler.getFecha_reg()));
                    if(xalquiler.getAuto().getId_estado() == 2){
                        r4_a.setText("Alquilado");
                    }else{
                        r4_a.setText("Devuelto");
                    }

                    tlAlquileres.addView(row_alquiler);
                } else { // cargar reservas:
                    View row_reserva = LayoutInflater.from(context).inflate(R.layout.row_reservas, null, false);
                    TextView r1_r = row_reserva.findViewById(R.id.r1_r);
                    TextView r2_r = row_reserva.findViewById(R.id.r2_r);
                    TextView r3_r = row_reserva.findViewById(R.id.r3_r);
                    TextView r4_r = row_reserva.findViewById(R.id.r4_r);
                    TextView r5_r = row_reserva.findViewById(R.id.r5_r);

                    r1_r.setText(xalquiler.getAuto().getModelo().getNombre()+" - "+xalquiler.getAuto().getMarca().getNombre());
                    r2_r.setText("$"+xalquiler.getTotal());
                    r3_r.setText(fecha.dateToString(xalquiler.getFecha_reg()));
                    r4_r.setOnClickListener(l->editarReserva(xalquiler));
                    r5_r.setOnClickListener(l->eliminarReserva(xalquiler));
                    tlReservas.addView(row_reserva);
                }
            }
        }
    }
    public void editarReserva(Alquiler xalquiler){
        Bundle bundle = new Bundle();
        bundle.putSerializable("objeto_alquiler", xalquiler);
        navController.navigate(R.id.nav_reserva, bundle);
    }
    public void eliminarReserva(final Alquiler xalquiler){
        if (!isAdded() || getActivity() == null || getActivity().isFinishing()) {
            // Evitar mostrar el cuadro de diálogo si el fragmento o la actividad no están en un estado válido.
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("¿Estás seguro?");
        builder.setMessage("Esta acción no se puede deshacer");

        builder.setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                realizarEliminacion(xalquiler);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private void realizarEliminacion(Alquiler xalquiler) {
        service_elim.eliminar("alquileres/" + xalquiler.getId_alquiler(), new serEliminar.ServiceCallback() {
            @Override
            public void onSuccess(Object response) {
                getAlquileres();
                Toast.makeText(context, "Su Reserva fue eliminada exitosamente", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(context, "Su Reserva no pudo ser eliminada", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void getAlquileres() {
        service.listar("alquileres", new serListar.ServiceCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                listarAlquileres(response);
            }
            @Override
            public void onError(String errorMessage) {
                // Manejar el error aquí
            }
        });
    }

    public void listarAlquileres(JSONArray response) {
        try {
            if (response.length() > 0) {
                alquileres = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject objAlq = response.getJSONObject(i);
                    // Parsea el objeto JSON y crea instancias de Alquiler
                    Alquiler alquiler = new Alquiler();
                    alquiler.setId_alquiler(objAlq.getInt("id_alquiler"));
                    alquiler.setId_cliente(objAlq.getInt("id_cliente"));
                    alquiler.setId_auto(objAlq.getInt("id_auto"));
                    alquiler.setId_proteccion(objAlq.getInt("id_proteccion"));
                    alquiler.setId_empleado(objAlq.isNull("id_empleado") ? 0 : objAlq.getInt("id_empleado"));

                    // Obtener las fechas en string
                    String fechaIniStr = objAlq.getString("fecha_ini");
                    String fechaFinStr = objAlq.getString("fecha_fin");
                    String fechaResStr = objAlq.getString("fecha_res");
                    String fechaRegStr = objAlq.getString("fecha_reg");

                    // Convertir las fechas string a Date
                    alquiler.setFecha_ini(fecha.stringToDate(fechaIniStr));
                    alquiler.setFecha_fin(fecha.stringToDate(fechaFinStr));
                    alquiler.setFecha_res(fecha.stringToDate(fechaResStr));
                    alquiler.setFecha_reg(fecha.stringToDate(fechaRegStr));

                    alquiler.setPrecio_auto(objAlq.getDouble("precio_auto"));
                    alquiler.setPrecio_proteccion(objAlq.getDouble("precio_proteccion"));
                    alquiler.setTotal(objAlq.getDouble("total"));
                    alquiler.setTipo_pago(objAlq.getString("tipo_pago"));
                    alquiler.setPagado(objAlq.getBoolean("pagado"));
                    alquiler.setReservado(objAlq.getBoolean("reservado"));
                    Devolucion devolucion = new Devolucion();
                    JSONArray devolucionesArray = objAlq.getJSONArray("devoluciones");
                    if (devolucionesArray.length()>0){
                        JSONObject objDev = devolucionesArray.getJSONObject(0);
                        devolucion.setId_devolucion(objDev.getInt("id_devolucion"));
                        devolucion.setId_alquiler(objDev.getInt("id_alquiler"));
                        String fec_dev = objDev.getString("fecha");
                        devolucion.setFecha( fecha.stringToDate(fec_dev));
                    }
                    if(autos.size() >0){
                        for(int j = 0; j<autos.size(); j++){
                            System.out.println(autos.get(j).getId_auto()+"");
                            if(alquiler.getId_auto() == autos.get(j).getId_auto()){
                                alquiler.setAuto(autos.get(j));
                            }
                        }
                    }
                    alquileres.add(alquiler);
                }
                cargarTablas();
            }
        } catch (JSONException e) {
            Toast.makeText(context,e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Autos:
    public void getAutos(){
        service.listar("autos", new serListar.ServiceCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                listarAutos(response);
            }
            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    public void listarAutos(JSONArray response) {
        if (response.length() > 0) {
            autos = new ArrayList<>();
            Auto auto;
            for (int i = 0; i < response.length(); i++) {
                try{
                    JSONObject objAuto = response.getJSONObject(i);
                    auto = new Auto();
                    auto.setId_auto(objAuto.getInt("id_auto"));
                    auto.setMatricula(objAuto.getString("matricula"));
                    auto.setId_modelo(objAuto.getInt("id_modelo"));
                    auto.setId_categoria(objAuto.getInt("id_categoria"));
                    auto.setColor(objAuto.getString("color"));
                    auto.setCapacidad(objAuto.getInt("capacidad"));
                    auto.setPotencia(objAuto.getDouble("potencia"));
                    auto.setPrecio_diario(objAuto.getDouble("precio_diario"));
                    auto.setUrl_image(objAuto.getString("url_imagen"));
                    auto.setId_estado(objAuto.getInt("id_estado"));
                    auto.setModelo(getModelo(auto.getId_modelo()));
                    auto.setMarca(getMarca(auto.getModelo().getId_marca()));
                    auto.setCategoria(getCategoria(auto.getId_categoria()));
                    autos.add(auto);
                }catch (Exception e){
                }
            }
            getAlquileres();
        }
    }

    public void getModelos(){
        modelos = new ArrayList<>();
        service.listar("modelos", new serListar.ServiceCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        Modelo modelo;
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject objMod = response.getJSONObject(i);
                            modelo = new Modelo();
                            modelo.setId_modelo(objMod.getInt("id_modelo"));
                            modelo.setNombre(objMod.getString("nombre"));
                            modelo.setId_marca(objMod.getInt("id_marca"));
                            modelos.add(modelo);
                        }
                        getMarcas();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context,e.getMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    public void getMarcas(){
        marcas = new ArrayList<>();
        service.listar("marcas", new serListar.ServiceCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        Marca marca;
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject objMar = response.getJSONObject(i);
                            marca = new Marca();
                            marca.setId_marca(objMar.getInt("id_marca"));
                            marca.setNombre(objMar.getString("nombre"));
                            marcas.add(marca);
                        }
                        getCategorias();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context,e.getMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onError(String errorMessage) {
            }
        });
    }
    public void getCategorias(){
        categorias = new ArrayList<>();
        service.listar("categorias", new serListar.ServiceCallback() {
            @Override
            public void onSuccess(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        Categoria categoria;
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject objCat = response.getJSONObject(i);
                            categoria = new Categoria();
                            categoria.setId_categoria(objCat.getInt("id_categoria"));
                            categoria.setNombre(objCat.getString("nombre"));
                            categorias.add(categoria);
                        }
                        getAutos();
                    }
                } catch (JSONException e) {
                    Toast.makeText(context,e.getMessage() , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    public Modelo getModelo(int id) {
        for (Modelo modelo : modelos) {
            if (modelo.getId_modelo() == id) {
                return modelo;
            }
        }
        return null;
    }
    public Marca getMarca(int id) {
        for (Marca marca : marcas) {
            if (marca.getId_marca() == id) {
                return marca;
            }
        }
        return null;
    }

    public Categoria getCategoria(int id) {
        for (Categoria categoria : categorias) {
            if (categoria.getId_categoria() == id) {
                return categoria;
            }
        }
        return null;
    }
}
