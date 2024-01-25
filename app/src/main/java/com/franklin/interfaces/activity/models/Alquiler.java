package com.franklin.interfaces.activity.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Alquiler {
    private int id_alquiler;
    private int id_cliente;
    private int id_auto;
    private int id_proteccion;
    private int id_empleado;
    private Date fecha_ini;
    private Date fecha_fin;
    private double precio_auto;
    private double precio_proteccion;
    private double total;
    private String tipo_pago;
    private boolean pagado;
    private boolean reservado;
    private Date fecha_res = new Date();
    private Date fecha_reg = new Date();

    public int getId_alquiler() {
        return id_alquiler;
    }

    public void setId_alquiler(int id_alquiler) {
        this.id_alquiler = id_alquiler;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_auto() {
        return id_auto;
    }

    public void setId_auto(int id_auto) {
        this.id_auto = id_auto;
    }

    public int getId_proteccion() {
        return id_proteccion;
    }

    public void setId_proteccion(int id_proteccion) {
        this.id_proteccion = id_proteccion;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Date getFecha_ini() {
        return fecha_ini;
    }

    public void setFecha_ini(Date fecha_ini) {
        this.fecha_ini = fecha_ini;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public double getPrecio_auto() {
        return precio_auto;
    }

    public void setPrecio_auto(double precio_auto) {
        this.precio_auto = precio_auto;
    }

    public double getPrecio_proteccion() {
        return precio_proteccion;
    }

    public void setPrecio_proteccion(double precio_proteccion) {
        this.precio_proteccion = precio_proteccion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public boolean isReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    public Date getFecha_res() {
        return fecha_res;
    }

    public void setFecha_res(Date fecha_res) {
        this.fecha_res = fecha_res;
    }

    public Date getFecha_reg() {
        return fecha_reg;
    }

    public void setFecha_reg(Date fecha_reg) {
        this.fecha_reg = fecha_reg;
    }

    @Override
    public String toString() {
        return "Alquiler{" +
                "id_alquiler=" + id_alquiler +
                ", id_cliente=" + id_cliente +
                ", id_auto=" + id_auto +
                ", id_proteccion=" + id_proteccion +
                ", id_empleado=" + id_empleado +
                ", fecha_ini=" + fecha_ini +
                ", fecha_fin=" + fecha_fin +
                ", precio_auto=" + precio_auto +
                ", precio_proteccion=" + precio_proteccion +
                ", total=" + total +
                ", tipo_pago='" + tipo_pago + '\'' +
                ", pagado=" + pagado +
                ", reservado=" + reservado +
                ", fecha_res=" + fecha_res +
                ", fecha_reg=" + fecha_reg +
                '}';
    }
}
