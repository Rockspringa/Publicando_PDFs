package edu.publishPDF.model.revistas;

import java.sql.Date;
import java.time.LocalDate;

import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputValidator;

public class Revista {

    private final int id;
    private final String editor;
    private final LocalDate fechaPublicacion;
    private final String nombre;
    private final String categoria;
    private Double costeMes;
    private int numeros;
    private int meGustas;
    private String descripcion;
    private String[] etiquetas;
    private Boolean comentariosActivos;
    private Boolean meGustasActivos;
    private Boolean suscripcionesActivas = true;

    private Revista(int id, String editor, LocalDate fechaPublicacion, String nombre, String categoria, double costeMes,
            String descripcion, Boolean comentariosActivos, Boolean meGustasActivos, Boolean suscripcionesActivas) {
        this.id = id;
        this.editor = editor;
        this.fechaPublicacion = fechaPublicacion;
        this.nombre = nombre;
        this.categoria = categoria;
        this.costeMes = costeMes;
        this.descripcion = descripcion;
        this.comentariosActivos = comentariosActivos;
        this.meGustasActivos = meGustasActivos;
        this.suscripcionesActivas = suscripcionesActivas;
    }

    private Revista(int id, String editor, LocalDate fechaPublicacion, String nombre, String categoria) {
        this.id = id;
        this.editor = editor;
        this.fechaPublicacion = fechaPublicacion;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public static Revista createRevista(int id, String editor, LocalDate fechaPublicacion, String nombre,
            double costeMes, String categoria, String descripcion, Boolean comentariosActivos, Boolean meGustasActivos,
            Boolean suscripcionesActivas) throws InvalidInputType {

        int numValido = (InputValidator.isUnsignedInt(id)) ? 1 : 0;
        numValido += (InputValidator.isValidText(editor)) ? 1 : 0;
        numValido += (InputValidator.isValidText(nombre)) ? 1 : 0;
        numValido += (InputValidator.isValidText(categoria)) ? 1 : 0;
        numValido += (InputValidator.isValidText(descripcion)) ? 1 : 0;

        if (numValido == 5)
            return new Revista(id, editor, fechaPublicacion, nombre, categoria, costeMes, descripcion,
                    comentariosActivos, meGustasActivos, suscripcionesActivas);
        else
            throw new InvalidInputType();
    }

    public static Revista createRevista(int id, String editor, LocalDate fechaPublicacion, String nombre,
            String categoria) throws InvalidInputType {

        int numValido = (InputValidator.isUnsignedInt(id)) ? 1 : 0;
        numValido += (InputValidator.isValidText(editor)) ? 1 : 0;
        numValido += (InputValidator.isValidText(nombre)) ? 1 : 0;
        numValido += (InputValidator.isValidText(categoria)) ? 1 : 0;

        if (numValido == 4)
            return new Revista(id, editor, fechaPublicacion, nombre, categoria);
        else
            throw new InvalidInputType();
    }

    public static Revista createRevista(int id, String editor, Date fechaPublicacion, String nombre, String categoria,
            double costeMes, String descripcion, Boolean comentariosActivos, Boolean meGustasActivos,
            boolean suscripcionesActivas) throws InvalidInputType {

        int numValido = (InputValidator.isUnsignedInt(id)) ? 1 : 0;
        numValido += (InputValidator.isValidText(editor)) ? 1 : 0;
        numValido += (InputValidator.isValidText(nombre)) ? 1 : 0;
        numValido += (InputValidator.isValidText(categoria)) ? 1 : 0;
        numValido += (InputValidator.isValidText((descripcion != null) ? descripcion : "")) ? 1 : 0;

        if (numValido == 5)
            return new Revista(id, editor, fechaPublicacion.toLocalDate(), nombre, categoria, costeMes, descripcion,
                    comentariosActivos, meGustasActivos, suscripcionesActivas);
        else
            throw new InvalidInputType();
    }

    public static Revista createRevista(int id, String editor, Date fechaPublicacion, String nombre, String categoria)
            throws InvalidInputType {

        int numValido = (InputValidator.isUnsignedInt(id)) ? 1 : 0;
        numValido += (InputValidator.isValidText(editor)) ? 1 : 0;
        numValido += (InputValidator.isValidText(nombre)) ? 1 : 0;
        numValido += (InputValidator.isValidText(categoria)) ? 1 : 0;

        if (numValido == 4)
            return new Revista(id, editor, fechaPublicacion.toLocalDate(), nombre, categoria);
        else
            throw new InvalidInputType();
    }

    public int getId() {
        return this.id;
    }

    public String getEditor() {
        return this.editor;
    }

    public LocalDate getFechaPublicacion() {
        return this.fechaPublicacion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean isComentariosActivos() {
        return this.comentariosActivos;
    }

    public void setComentariosActivos(Boolean comentariosActivos) {
        this.comentariosActivos = comentariosActivos;
    }

    public Boolean isMeGustasActivos() {
        return this.meGustasActivos;
    }

    public void setMeGustasActivos(Boolean meGustasActivos) {
        this.meGustasActivos = meGustasActivos;
    }

    public Boolean isSuscripcionesActivas() {
        return this.suscripcionesActivas;
    }

    public void setSuscripcionesActivas(Boolean suscripcionesActivas) {
        this.suscripcionesActivas = suscripcionesActivas;
    }

    public int getNumeros() {
        return numeros;
    }

    public void setNumeros(int numeros) {
        this.numeros = numeros;
    }

    public int getMeGustas() {
        return meGustas;
    }

    public void setMeGustas(int meGustas) {
        this.meGustas = meGustas;
    }

    public String[] getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(String[] etiquetas) {
        this.etiquetas = etiquetas;
    }

    public double getCosteMes() {
        return costeMes;
    }

    public void setCosteMes(double costeMes) {
        this.costeMes = costeMes;
    }

}
