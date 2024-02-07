package com.franklin.interfaces.activity.services;

public class RUTA {
    public static String endPoint="http://192.168.252.23:8080/api/";

    public static String getUrlFoto(String url) {
        String[] partes = url.split("/");
        String new_url = endPoint+"imagenes/"+partes[partes.length - 1];
        return new_url;
    }
}
