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
    private int numeros;
    private int meGustas;
    private String descripcion;
    private String[] etiquetas;
    private boolean comentariosActivos = true;
    private boolean meGustasActivos = true;
    private boolean suscripcionesActivas = true;

    private Revista(int id, String editor, LocalDate fechaPublicacion, String nombre, String categoria,
            String descripcion, boolean comentariosActivos, boolean meGustasActivos, boolean suscripcionesActivas) {
        this.id = id;
        this.editor = editor;
        this.fechaPublicacion = fechaPublicacion;
        this.nombre = nombre;
        this.categoria = categoria;
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
            String categoria, String descripcion, boolean comentariosActivos, boolean meGustasActivos,
            boolean suscripcionesActivas) throws InvalidInputType {

        int numValido = (InputValidator.isUnsignedInt(id)) ? 1 : 0;
        numValido += (InputValidator.isValidText(editor)) ? 1 : 0;
        numValido += (InputValidator.isValidText(nombre)) ? 1 : 0;
        numValido += (InputValidator.isValidText(categoria)) ? 1 : 0;
        numValido += (InputValidator.isValidText(descripcion)) ? 1 : 0;

        if (numValido == 5)
            return new Revista(id, editor, fechaPublicacion, nombre, categoria, descripcion, comentariosActivos,
                    meGustasActivos, suscripcionesActivas);
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
            String descripcion, boolean comentariosActivos, boolean meGustasActivos, boolean suscripcionesActivas)
            throws InvalidInputType {

        int numValido = (InputValidator.isUnsignedInt(id)) ? 1 : 0;
        numValido += (InputValidator.isValidText(editor)) ? 1 : 0;
        numValido += (InputValidator.isValidText(nombre)) ? 1 : 0;
        numValido += (InputValidator.isValidText(categoria)) ? 1 : 0;
        numValido += (InputValidator.isValidText(descripcion)) ? 1 : 0;

        if (numValido == 5)
            return new Revista(id, editor, fechaPublicacion.toLocalDate(), nombre, categoria, descripcion,
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

    public boolean isComentariosActivos() {
        return this.comentariosActivos;
    }

    public void setComentariosActivos(boolean comentariosActivos) {
        this.comentariosActivos = comentariosActivos;
    }

    public boolean isMeGustasActivos() {
        return this.meGustasActivos;
    }

    public void setMeGustasActivos(boolean meGustasActivos) {
        this.meGustasActivos = meGustasActivos;
    }

    public boolean isSuscripcionesActivas() {
        return this.suscripcionesActivas;
    }

    public void setSuscripcionesActivas(boolean suscripcionesActivas) {
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
    

}
