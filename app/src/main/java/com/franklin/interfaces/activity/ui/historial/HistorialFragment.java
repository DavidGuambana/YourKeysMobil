package com.franklin.interfaces.activity.ui.historial;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.franklin.interfaces.R;
import com.franklin.interfaces.activity.models.Alquiler;
import com.franklin.interfaces.activity.models.Devolucion;
import com.franklin.interfaces.activity.services.serListar;
import com.franklin.interfaces.activity.utils.FECHA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistorialFragment extends Fragment {
    private HistorialViewModel mViewModel;
    private serListar service;
    FECHA fecha = new FECHA();
    ArrayList<Alquiler> alquileres;
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
        context = getActivity().getApplicationContext();
        tlAlquileres = view.findViewById(R.id.tlAlquileres);
        tlReservas = view.findViewById(R.id.tlReservas);
        service = new serListar(context);
        getAlquileres();
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
                    alquiler.setId_empleado(objAlq.getInt("id_empleado"));

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
                    alquileres.add(alquiler);
                }
                cargarTablas();
            }
        } catch (JSONException e) {
            Toast.makeText(context,e.getMessage() , Toast.LENGTH_LONG).show();
        }
    }
    public void cargarTablas(){
        tlReservas.removeAllViews();
        tlAlquileres.removeAllViews();

        for (int i=0; i < alquileres.size(); i++){
            View registro = LayoutInflater.from(context).inflate(R.layout.row_4c, null, false);
            TextView tv0 = registro.findViewById(R.id.tv0);
            TextView tv1 = registro.findViewById(R.id.tv1);
            TextView tv2 = registro.findViewById(R.id.tv2);
            TextView tv3 = registro.findViewById(R.id.tv3);
            if (!alquileres.get(i).isPagado()){
                tv0.setText(alquileres.get(i).getId_alquiler()+"");
                tv1.setText(fecha.dateToString(alquileres.get(i).getFecha_ini()));
                tv2.setText(fecha.dateToString(alquileres.get(i).getFecha_fin()));
                tv3.setText(alquileres.get(i).getTotal()+"");
                tlReservas.addView(registro);
            } else{
                tv0.setText(alquileres.get(i).getId_alquiler()+"");
                tv1.setText(fecha.dateToString(alquileres.get(i).getFecha_ini()));
                tv2.setText(fecha.dateToString(alquileres.get(i).getFecha_fin()));
                tv3.setText(alquileres.get(i).getTotal()+"");
                tlAlquileres.addView(registro);
            }
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HistorialViewModel.class);
        // TODO: Use the ViewModel
    }
}
