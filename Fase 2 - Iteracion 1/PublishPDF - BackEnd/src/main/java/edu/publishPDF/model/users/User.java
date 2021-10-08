package edu.publishPDF.model.users;

import com.google.gson.annotations.SerializedName;

public abstract class User {

    @SerializedName("_username")
    private final String username;
    
    @SerializedName("_password")
    private final String password;

    @SerializedName("_nombre")
    private String nombre;
    
    @SerializedName("_descripcion")
    private String descripcion;
    
    @SerializedName("_gustos")
    private String gustos;
    
    @SerializedName("_hobbies")
    private String hobbies;
    
    @SerializedName("_foto")
    private String fotoPath;

    protected User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGustos() {
        return this.gustos;
    }

    public void setGustos(String gustos) {
        this.gustos = gustos;
    }

    public String getHobbies() {
        return this.hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getFotoPath() {
        return this.fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

}