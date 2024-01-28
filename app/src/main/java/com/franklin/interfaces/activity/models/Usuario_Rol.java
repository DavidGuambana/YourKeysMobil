package com.franklin.interfaces.activity.models;

import java.io.Serializable;

public class Usuario_Rol implements Serializable {
    private int id_usuario_rol;
    private int id_usuario;
    private int id_rol;

    public int getId_usuario_rol() {
        return id_usuario_rol;
    }

    public void setId_usuario_rol(int id_usuario_rol) {
        this.id_usuario_rol = id_usuario_rol;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    @Override
    public String toString() {
        return "Usuario_Rol{" +
                "id_usuario_rol=" + id_usuario_rol +
                ", id_usuario=" + id_usuario +
                ", id_rol=" + id_rol +
                '}';
    }
}
