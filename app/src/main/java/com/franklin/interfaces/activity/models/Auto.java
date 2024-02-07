package com.franklin.interfaces.activity.models;

import java.io.Serializable;
import java.util.List;

public class Auto implements Serializable {

    private int id_auto;
    private String matricula;
    private int id_modelo;
    private int id_categoria;
    private String color;
    private int capacidad;
    private double potencia;
    private double precio_diario;
    private String url_image;
    private int id_estado;

    private Modelo modelo;
    private Marca marca;
    private Categoria categoria;

    public int getId_auto() {
        return id_auto;
    }

    public void setId_auto(int id_auto) {
        this.id_auto = id_auto;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(int id_modelo) {
        this.id_modelo = id_modelo;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPotencia() {
        return potencia;
    }

    public void setPotencia(double potencia) {
        this.potencia = potencia;
    }

    public double getPrecio_diario() {
        return precio_diario;
    }

    public void setPrecio_diario(double precio_diario) {
        this.precio_diario = precio_diario;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public int getId_estado() {
        return id_estado;
    }

    public void setId_estado(int id_estado) {
        this.id_estado = id_estado;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id_auto=" + id_auto +
                ", matricula='" + matricula + '\'' +
                ", id_modelo=" + id_modelo +
                ", id_categoria=" + id_categoria +
                ", color='" + color + '\'' +
                ", capacidad=" + capacidad +
                ", potencia=" + potencia +
                ", precio_diario=" + precio_diario +
                ", url_image='" + url_image + '\'' +
                ", id_estado=" + id_estado +
                ", modelo=" + modelo +
                ", marca=" + marca +
                ", categoria=" + categoria +
                '}';
    }
}

