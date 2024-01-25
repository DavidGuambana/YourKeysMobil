package com.franklin.interfaces.activity.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.franklin.interfaces.R;
import com.franklin.interfaces.activity.InicioActivity;
import com.franklin.interfaces.activity.models.Alquiler;
import com.franklin.interfaces.activity.models.Cliente;
import com.franklin.interfaces.activity.models.Devolucion;
import com.franklin.interfaces.activity.models.Persona;
import com.franklin.interfaces.activity.models.Usuario;
import com.franklin.interfaces.activity.models.Usuario_Rol;
import com.franklin.interfaces.activity.services.serCrear;
import com.franklin.interfaces.activity.services.serListar;
import com.franklin.interfaces.activity.ui.register.Register;
import com.franklin.interfaces.activity.utils.FECHA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {

    EditText username,password;
    serListar service;
    final Context context = this;
    FECHA fecha = new FECHA();

    ArrayList<Persona> personas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        service = new serListar(context);

        //atributos del login:
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);

        Button btnIngresar = findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(l-> abrirInicio());

        Button btnRegister = findViewById(R.id.btnRegistrarse);
        btnRegister.setOnClickListener(l-> abrirRegistro());
    }

    public void loguear(){
        if (username.getText().toString().trim().equals("") ||
                password.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Todos los campos deben ser completados", Toast.LENGTH_LONG).show();
        } else {
            service.listar("personas", new serListar.ServiceCallback() {
                @Override
                public void onSuccess(JSONArray response) {
                    listarPersonas(response);
                }

                @Override
                public void onError(String errorMessage) {
                    Toast.makeText(context,errorMessage , Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void listarPersonas(JSONArray response) {
        Persona persona;
        Cliente cliente;
        List<Alquiler> alquileres;
        List<Devolucion> devoluciones;
        Devolucion devolucion;
        Usuario usuario;
        List<Usuario_Rol> users_roles;

        try {
            if (response.length() > 0) {
                personas = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    JSONObject objPer = response.getJSONObject(i);
                    persona = new Persona();
                    persona.setId_persona(objPer.getInt("id_persona"));
                    persona.setCedula(objPer.getString("cedula"));
                    persona.setNombre1(objPer.getString("nombre1"));
                    persona.setNombre2(objPer.getString("nombre2"));
                    persona.setApellido1(objPer.getString("apellido1"));
                    persona.setApellido2(objPer.getString("apellido2"));
                    persona.setTelefono(objPer.getString("telefono"));
                    persona.setDireccion(objPer.getString("direccion"));
                    persona.setCorreo(objPer.getString("correo"));
                    persona.setUrl_imagen(objPer.getString("url_imagen"));
                    persona.setFecha_nac(fecha.stringToDate(objPer.getString("fecha_nac")));
                    persona.setFecha_reg(fecha.stringToDate(objPer.getString("fecha_reg")));


                    cliente = new Cliente();
                    JSONArray clientesArray = objPer.getJSONArray("clientes");
                    if (clientesArray.length() > 0) {
                        JSONObject objCli = clientesArray.getJSONObject(0);
                        cliente.setId_cliente(objCli.getInt("id_cliente"));
                        cliente.setId_persona(objCli.getInt("id_persona"));
                        cliente.setLicencia(objCli.getString("licencia"));
                        cliente.setTipo_licencia(objCli.getString("tipo_licencia"));
                        persona.setCliente(cliente);

                        //asignar alquileres del cliente
                        JSONArray alquileresArray = objCli.getJSONArray("alquileres");
                        if (alquileresArray.length() > 0) {
                            alquileres = new ArrayList<>();
                            devoluciones = new ArrayList<>();
                            for (int j = 0; j < alquileresArray.length(); j++) {
                                JSONObject objAlq = alquileresArray.getJSONObject(j);
                                Alquiler alquiler = new Alquiler();
                                alquiler.setId_alquiler(objAlq.getInt("id_alquiler"));
                                alquiler.setId_cliente(objAlq.getInt("id_cliente"));
                                alquiler.setId_auto(objAlq.getInt("id_auto"));
                                alquiler.setId_proteccion(objAlq.getInt("id_proteccion"));
                                alquiler.setId_empleado(objAlq.getInt("id_empleado"));
                                alquiler.setFecha_ini(fecha.stringToDate(objAlq.getString("fecha_ini")));
                                alquiler.setFecha_fin(fecha.stringToDate(objAlq.getString("fecha_fin")));
                                alquiler.setPrecio_auto(objAlq.getDouble("precio_auto"));
                                alquiler.setPrecio_proteccion(objAlq.getDouble("precio_proteccion"));
                                alquiler.setTotal(objAlq.getDouble("total"));
                                alquiler.setTipo_pago(objAlq.getString("tipo_pago"));
                                alquiler.setPagado(objAlq.getBoolean("pagado"));
                                alquiler.setReservado(objAlq.getBoolean("reservado"));
                                alquiler.setFecha_res(fecha.stringToDate(objAlq.getString("fecha_res")));
                                alquiler.setFecha_reg(fecha.stringToDate(objAlq.getString("fecha_reg")));
                                alquileres.add(alquiler);


                                JSONArray devolucionesArray = objAlq.getJSONArray("devoluciones");
                                if (devolucionesArray.length() > 0) {
                                    devolucion = new Devolucion();
                                    JSONObject objDev = devolucionesArray.getJSONObject(0);
                                    devolucion.setId_devolucion(objDev.getInt("id_devolucion"));
                                    devolucion.setFecha(fecha.stringToDate(objDev.getString("fecha")));
                                    devolucion.setId_alquiler(objDev.getInt("id_alquiler"));
                                    devoluciones.add(devolucion);
                                }

                            }
                            persona.setAlquileres(alquileres);
                            persona.setDevoluciones(devoluciones);

                        }
                    }
                    /*
                    usuario = new Usuario();
                    JSONArray usuariosArray = objPer.getJSONArray("usuarios");
                    if (usuariosArray.length() > 0) {
                        JSONObject objUser = usuariosArray.getJSONObject(0);
                        usuario.setId_usuario(objUser.getInt("id_usuario"));
                        usuario.setId_persona(objUser.getInt("id_persona"));
                        usuario.setUsername(objUser.getString("username"));
                        usuario.setPassword(objUser.getString("password"));
                        persona.setUsuario(usuario);

                        //asignar usuarios_roles del usuario
                        JSONArray users_rolesArray = objUser.getJSONArray("usuarios_roles");
                        if (users_rolesArray.length() > 0) {
                            users_roles = new ArrayList<>();
                            for (int k = 0; k < users_rolesArray.length(); k++) {
                                JSONObject objUserRol = users_rolesArray.getJSONObject(k);
                                Usuario_Rol usuario_rol = new Usuario_Rol();
                                usuario_rol.setId_usuario_rol(objUserRol.getInt("id_usuario_rol"));
                                usuario_rol.setId_usuario(objUserRol.getInt("id_usuario"));
                                usuario_rol.setId_rol(objUserRol.getInt("id_rol"));

                                users_roles.add(usuario_rol);
                            }
                            persona.setUsuarios_roles(users_roles);
                        }
                    }

                     */
                    personas.add(persona);
                }

                //Toast.makeText(context,"Personas: "+ personas.size(), Toast.LENGTH_LONG).show();
                Toast.makeText(context,"Clientes: "+ personas.get(0).getAlquileres(), Toast.LENGTH_LONG).show();
                //Toast.makeText(context,"Usuarios: "+ personas.size(), Toast.LENGTH_LONG).show();
                //Toast.makeText(context,"Alquileres: "+ personas.size(), Toast.LENGTH_LONG).show();
                //Toast.makeText(context,"Devoluciones: "+ personas.size(), Toast.LENGTH_LONG).show();
                //Toast.makeText(context,"Usuarios_Roles: "+ personas.size(), Toast.LENGTH_LONG).show();

            }
        } catch (JSONException e) {
            Toast.makeText(context,e.getMessage() , Toast.LENGTH_LONG).show();
        }

    }

    public void abrirRegistro(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        finish();
    }
    public void abrirInicio(){
        Intent intent = new Intent(this, InicioActivity.class);
        startActivity(intent);
        finish();
    }
}