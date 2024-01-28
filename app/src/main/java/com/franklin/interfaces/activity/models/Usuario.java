package com.franklin.interfaces.activity.models;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id_usuario;
    private int id_persona;
    private String username;
    private String password;

    public Usuario() {
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_persona() {
        return id_persona;
    }

    public void setId_persona(int id_persona) {
        this.id_persona = id_persona;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id_usuario=" + id_usuario +
                ", id_persona=" + id_persona +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
