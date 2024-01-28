package com.franklin.interfaces.activity.models;

import java.io.Serializable;

public class Modelo implements Serializable {
    private int id_modelo;
    private String nombre;
    private int id_marca;

    public Modelo() {
    }

    public int getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(int id_modelo) {
        this.id_modelo = id_modelo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    @Override
    public String toString() {
        return "Modelo{" +
                "id_modelo=" + id_modelo +
                ", nombre='" + nombre + '\'' +
                ", id_marca=" + id_marca +
                '}';
    }
}
