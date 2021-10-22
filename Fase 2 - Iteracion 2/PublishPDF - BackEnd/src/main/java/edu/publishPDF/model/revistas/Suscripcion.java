package edu.publishPDF.model.revistas;

import java.time.LocalDate;

import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputValidator;

public class Suscripcion {

    private final int revista;
    private final String suscriptor;
    private LocalDate fechaSuscripcion;
    private boolean mensual;

    private Suscripcion(int revista, String suscriptor) {
        this.revista = revista;
        this.suscriptor = suscriptor;
    }

    public static Suscripcion createSuscripcion(int revista, String suscriptor) throws InvalidInputType {
        int cantValids = (InputValidator.isUnsignedInt(revista)) ? 1 : 0;
        cantValids += (InputValidator.isValidText(suscriptor)) ? 1 : 0;

        if (cantValids == 2) {
            return new Suscripcion(revista, suscriptor);
        } else {
            throw new InvalidInputType();
        }
    }

    public static Suscripcion createSuscripcion(int revista, String suscriptor, LocalDate fechaSuscripcion,
            boolean mensual) throws InvalidInputType {

        Suscripcion suscripcion = createSuscripcion(revista, suscriptor);
        suscripcion.setFechaSuscripcion(fechaSuscripcion);
        suscripcion.setMensual(mensual);

        return suscripcion;
    }

    public int getRevista() {
        return this.revista;
    }

    public String getSuscriptor() {
        return this.suscriptor;
    }

    public LocalDate getFechaSuscripcion() {
        return this.fechaSuscripcion;
    }

    public void setFechaSuscripcion(LocalDate fechaSuscripcion) {
        this.fechaSuscripcion = fechaSuscripcion;
    }

    public boolean isMensual() {
        return this.mensual;
    }

    public void setMensual(boolean mensual) {
        this.mensual = mensual;
    }

}
