package com.franklin.interfaces.activity.models;

import java.io.Serializable;

public class Proteccion implements Serializable {
    private int id_proteccion;
    private String nombre;
    private double precio;

    public int getId_proteccion() {
        return id_proteccion;
    }

    public void setId_proteccion(int id_proteccion) {
        this.id_proteccion = id_proteccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
