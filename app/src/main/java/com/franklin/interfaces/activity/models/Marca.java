package com.franklin.interfaces.activity.models;

import java.io.Serializable;


public class Marca implements Serializable{
    private int id_marca;
    private String nombre;

    public Marca() {
    }
    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Marca{" +
                "id_marca=" + id_marca +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
