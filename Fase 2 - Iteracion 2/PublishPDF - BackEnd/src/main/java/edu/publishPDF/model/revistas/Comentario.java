package edu.publishPDF.model.revistas;

import java.sql.Date;
import java.time.LocalDate;

import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputValidator;

public class Comentario {
    
    private final int revista;
    private final String suscriptor;
    private final String comentario;
    private final LocalDate fecha;
    
    private Comentario(int revista, String suscriptor, String comentario, LocalDate fecha) {
        this.revista = revista;
        this.suscriptor = suscriptor;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public static Comentario createComentario(int revista, String suscriptor, String comentario, LocalDate fecha) throws InvalidInputType {
        int val = (InputValidator.isUnsignedInt(revista)) ? 1 : 0;
        val += (InputValidator.isValidText(suscriptor)) ? 1 : 0;
        val += (InputValidator.isValidText(comentario)) ? 1 : 0;

        if (val != 3)
            throw new InvalidInputType();
        
        return new Comentario(revista, suscriptor, comentario, fecha);
    }

    public static Comentario createComentario(int revista, String suscriptor, String comentario, Date fecha) throws InvalidInputType {
        return createComentario(revista, suscriptor, comentario, fecha.toLocalDate());
    }

    public int getRevista() {
        return this.revista;
    }

    public String getSuscriptor() {
        return this.suscriptor;
    }

    public String getComentario() {
        return this.comentario;
    }

    public LocalDate getFecha() {
        return this.fecha;
    }

}
