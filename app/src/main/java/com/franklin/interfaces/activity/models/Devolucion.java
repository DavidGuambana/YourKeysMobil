package com.franklin.interfaces.activity.models;

import java.util.Date;
public class Devolucion {
    private int id_devolucion;
    private int id_alquiler;
    private Date fecha = new Date();

    public int getId_devolucion() {
        return id_devolucion;
    }

    public void setId_devolucion(int id_devolucion) {
        this.id_devolucion = id_devolucion;
    }

    public int getId_alquiler() {
        return id_alquiler;
    }

    public void setId_alquiler(int id_alquiler) {
        this.id_alquiler = id_alquiler;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Devolucion{" +
                "id_devolucion=" + id_devolucion +
                ", id_alquiler=" + id_alquiler +
                ", fecha=" + fecha +
                '}';
    }
}
