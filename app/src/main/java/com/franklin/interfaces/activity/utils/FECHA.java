package com.franklin.interfaces.activity.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FECHA {
    public String dateToString(Date fecha) {
        if (fecha == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(fecha);
    }
    public Date stringToDate(String fecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
