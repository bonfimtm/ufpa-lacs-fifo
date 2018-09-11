package br.ufpa.fifo.controller.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author thiago
 */
@javax.faces.bean.ManagedBean
@javax.faces.bean.SessionScoped
public class UtilBean implements Serializable {

    /**
     * Creates a new instance of UtilBean
     */
    public UtilBean() {
    }

    public static String maskBoolean(boolean b) {
        return b ? "Sim" : "Nao";
    }

    public static String maskDate(Date date) {
        SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        ddMMyyyy.setTimeZone(TimeZone.getTimeZone("America/Belem"));
        return ddMMyyyy.format(date);
    }

    public static String maskTime(Date date) {
        SimpleDateFormat hhmmss = new SimpleDateFormat("HH:mm:ss", new Locale("pt", "BR"));
        hhmmss.setTimeZone(TimeZone.getTimeZone("America/Belem"));
        return hhmmss.format(date);
    }

    public static String maskDateAndTime(Date date) {
        SimpleDateFormat ddMMyyyhhmmss = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
        ddMMyyyhhmmss.setTimeZone(TimeZone.getTimeZone("America/Belem"));
        return ddMMyyyhhmmss.format(date);
    }
}
