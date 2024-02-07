package com.franklin.interfaces.activity.services;

public class RUTA {
    //apecs:
    //final String endPoint="http://192.168.18.79:8080/api/";
    //localhost:
    //public static String endPoint="http://192.168.56.1:8080/api/";
    public static String endPoint="http://192.168.0.119:8080/api/";

    public static String getUrlFoto(String url) {
        String[] partes = url.split("/");
        String new_url = endPoint+"imagenes/"+partes[partes.length - 1];
        return new_url;
    }
}
