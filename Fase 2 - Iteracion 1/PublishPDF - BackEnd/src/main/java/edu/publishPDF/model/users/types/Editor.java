package edu.publishPDF.model.users.types;

import com.google.gson.annotations.SerializedName;

import edu.publishPDF.model.users.User;

public class Editor extends User {

    @SerializedName("_revistas")
    private String[] revistas = new String[0];

    Editor(String username, String password) {
        super(username, password);
    }

    public String[] getRevistas() {
        return this.revistas;
    }

    public void setRevistas(String[] revistas) {
        this.revistas = revistas;
    }
    
}
