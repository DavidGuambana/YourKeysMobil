package com.franklin.interfaces.activity.ui.autos;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.franklin.interfaces.R;
import com.franklin.interfaces.activity.models.Alquiler;
import com.franklin.interfaces.activity.models.Auto;
import com.franklin.interfaces.activity.models.Categoria;
import com.franklin.interfaces.activity.models.Cliente;
import com.franklin.interfaces.activity.models.Devolucion;
import com.franklin.interfaces.activity.models.Marca;
import com.franklin.interfaces.activity.models.Modelo;
import com.franklin.interfaces.activity.models.Persona;
import com.franklin.interfaces.activity.models.Usuario;
import com.franklin.interfaces.activity.models.Usuario_Rol;
import com.franklin.interfaces.activity.services.serBuscar;
import com.franklin.interfaces.activity.services.serListar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaAutosFragment extends Fragment {

    Context context;
    public AutosAdapter mAdapter;
    private serListar service;
    ArrayList<Auto> autos;
    List <Modelo> modelos;
    List <Marca> marcas;
    List <Categoria> categorias;
    public static ListaAutosFragment newInstance() {
        return new ListaAutosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_autos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isAdded()){
            context = getContext();
            RecyclerView rv_autos = view.findViewById(R.id.recyclerAutos);
            rv_autos.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            rv_autos.setLayoutManager(layoutManager);
            mAdapter = new AutosAdapter(context, Navigation.findNavController(view));
            rv_autos.setAdapter(mAdapter);
            service = new serListar(context);
            getModelos();
        }
    }
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
            mAdapter.setDataSet(autos);
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