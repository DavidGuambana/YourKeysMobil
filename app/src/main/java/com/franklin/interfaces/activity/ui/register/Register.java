package com.franklin.interfaces.activity.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.franklin.interfaces.R;
import com.franklin.interfaces.activity.services.serCrear;
import com.franklin.interfaces.activity.ui.login.Login;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {
    EditText licencia,nombre,apellido,username,password;
    serCrear service;
    final Context context = this;
    CheckBox ver_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        service = new serCrear(this);
        TextView tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(l-> abrirLogin());

        Button btnRegistro = findViewById(R.id.btnIngresar);
        btnRegistro.setOnClickListener(l-> abrirLogin());

        //atributos del register:
        licencia = findViewById(R.id.licencia);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        username = findViewById(R.id.usermane);
        password = findViewById(R.id.txtPassword);
        ver_pass = findViewById(R.id.verPass);

        Button ingresar = findViewById(R.id.btnIngresar);
        ingresar.setOnClickListener(l-> registrar());

        ver_pass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Cambia el método de transformación del texto del EditText
                if (isChecked) {
                    // CheckBox seleccionado, muestra el texto normal
                    password.setTransformationMethod(null);
                } else {
                    // CheckBox no seleccionado, muestra puntos para ocultar el texto
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    public void registrar() {
        if (licencia.getText().toString().trim().equals("") ||
                nombre.getText().toString().trim().equals("") ||
                apellido.getText().toString().trim().equals("") ||
                username.getText().toString().trim().equals("") ||
                password.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Todos los campos deben ser completados", Toast.LENGTH_LONG).show();
        } else {
            JSONObject JSB_persona = new JSONObject();
            try {
                JSB_persona.put("cedula", licencia.getText().toString());
                JSB_persona.put("nombre1", nombre.getText().toString());
                JSB_persona.put("nombre2", "");
                JSB_persona.put("apellido1", apellido.getText().toString());
                JSB_persona.put("apellido2", "");
                JSB_persona.put("telefono", "");
                JSB_persona.put("direccion", "");
                JSB_persona.put("correo", "");

                service.crear("personas", JSB_persona, new serCrear.ServiceCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        if (response != null) {
                            JSONObject objPer = (JSONObject) response;
                            int idPersona = objPer.optInt("id_persona");

                            JSONObject JSB_cliente = new JSONObject();
                            try {
                                JSB_cliente.put("licencia", licencia.getText().toString());
                                JSB_cliente.put("id_persona", idPersona);

                                service.crear("clientes", JSB_cliente, new serCrear.ServiceCallback() {
                                    @Override
                                    public void onSuccess(Object response) {
                                        if (response != null) {
                                            JSONObject JSB_usuario = new JSONObject();
                                            try {
                                                JSB_usuario.put("id_persona", idPersona);
                                                JSB_usuario.put("username", username.getText().toString());
                                                JSB_usuario.put("password", password.getText().toString());

                                                service.crear("usuarios", JSB_usuario, new serCrear.ServiceCallback() {
                                                    @Override
                                                    public void onSuccess(Object response) {
                                                        if (response != null) {
                                                            JSONObject objUser = (JSONObject) response;
                                                            int idUser = objUser.optInt("id_usuario");

                                                            JSONObject JSB_usuario_rol = new JSONObject();
                                                            try {
                                                                JSB_usuario_rol.put("id_usuario", idUser);
                                                                JSB_usuario_rol.put("id_rol", 3);

                                                                service.crear("usuarios_roles", JSB_usuario_rol, new serCrear.ServiceCallback() {
                                                                    @Override
                                                                    public void onSuccess(Object response) {
                                                                        abrirLogin();
                                                                        Toast.makeText(context, "Tu cuenta ha sido creada exitosamente!", Toast.LENGTH_LONG).show();
                                                                    }

                                                                    @Override
                                                                    public void onError(String errorMessage) {
                                                                        Toast.makeText(context, "Error al crear usuario_rol: " + errorMessage, Toast.LENGTH_LONG).show();
                                                                    }
                                                                });

                                                            } catch (JSONException e) {
                                                                throw new RuntimeException(e);
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(String errorMessage) {
                                                        Toast.makeText(context, "Error al crear usuario: " + errorMessage, Toast.LENGTH_LONG).show();
                                                    }
                                                });

                                            } catch (JSONException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(String errorMessage) {
                                        Toast.makeText(context, "Error al crear cliente: " + errorMessage, Toast.LENGTH_LONG).show();
                                    }
                                });

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Toast.makeText(context, "Error al crear persona: " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void abrirLogin(){
    Intent intent = new Intent(this, Login.class);
    startActivity(intent);
    this.finish();
    }
}