package edu.publishPDF.model.users.types;

import com.google.gson.annotations.SerializedName;

import edu.publishPDF.model.users.User;

public class Administrador extends User {

    @SerializedName("_anuncios")
    private String[] anuncios = new String[0];

    Administrador(String username, String password) {
        super(username, password);
    }

    public String[] getAnuncios() {
        return this.anuncios;
    }

    public void setAnuncios(String[] anuncios) {
        this.anuncios = anuncios;
    }
    
    
}
