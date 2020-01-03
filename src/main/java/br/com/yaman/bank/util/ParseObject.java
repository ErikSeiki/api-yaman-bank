package br.com.yaman.bank.util;

import java.security.Timestamp;
import java.util.Date;



public class ParseObject {
    
	public static Date toDate(Object campoDoRegistro) {
        if (campoDoRegistro == null)
            return null;
        if (campoDoRegistro.getClass() == Timestamp.class) {
            return new Date(((java.sql.Timestamp)campoDoRegistro).getTime());
        } else {
            Date date = null;
            try {
                date = LocalDateParser.tryParseToDate(campoDoRegistro.toString());
            } catch (Exception e) {
                System.out.println("Ocorreu um erro na conversao da data \"" + campoDoRegistro.toString() +"\". Registro nao incluido no retorno.");
            }
            return date;
        }
    }
	
	/**
     * Converte objeto (campo registro retornado pelo BD) para Integer.
     */
    public static Integer toInteger(Object campoDoRegistro) {
        return (campoDoRegistro == null)? null : Integer.valueOf(campoDoRegistro.toString());
    }

/**
     * Converte objeto (campo registro retornado pelo BD) para String.
     */
    public static String toString(Object campoDoRegistro) {
        return (campoDoRegistro == null)? null : campoDoRegistro.toString();
    }

    /**
     * Converte objeto (campo registro retornado pelo BD) para Double.
     */
    public static Float toFloat(Object campoDoRegistro) {
        return (campoDoRegistro == null) ? null : Float.valueOf(campoDoRegistro.toString().replace(",", "."));
    }
}
