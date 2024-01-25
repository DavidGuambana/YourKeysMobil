package com.franklin.interfaces.activity.models;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private int id_cliente;
    private int id_persona;
    private String licencia;
    private String tipo_licencia;

    public Cliente() {
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public String getTipo_licencia() {
        return tipo_licencia;
    }

    public void setTipo_licencia(String tipo_licencia) {
        this.tipo_licencia = tipo_licencia;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id_cliente=" + id_cliente +
                ", id_persona=" + id_persona +
                ", licencia='" + licencia + '\'' +
                ", tipo_licencia='" + tipo_licencia + '\'' +
                '}';
    }
}
