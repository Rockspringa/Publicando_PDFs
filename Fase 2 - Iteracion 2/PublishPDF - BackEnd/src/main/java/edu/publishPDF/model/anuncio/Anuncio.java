package edu.publishPDF.model.anuncio;

import java.time.LocalDate;

import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputValidator;

public class Anuncio {

    private final int id;
    private final boolean activo;
    private final String texto;
    private String administrador;
    private LocalDate fechaPublicacion;
    private Double costoDia;
    private String imagen;
    private String video;

    private Anuncio(int id, boolean activo, String texto) {
        this.id = id;
        this.activo = activo;
        this.texto = texto;
    }

    public static Anuncio createAnuncio(int id, boolean activo, String texto) throws InvalidInputType {
        int val = (InputValidator.isUnsignedInt(id)) ? 1 : 0;
        val += (InputValidator.isValidText(texto)) ? 1 : 0;

        if (val != 2)
            throw new InvalidInputType();

        return new Anuncio(id, activo, texto);
    }

    public int getId() {
        return id;
    }

    public boolean isActivo() {
        return activo;
    }

    public String getTexto() {
        return texto;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Double getCostoDia() {
        return costoDia;
    }

    public void setCostoDia(Double costoDia) {
        this.costoDia = costoDia;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

}
