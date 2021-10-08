package edu.publishPDF.model.users.types;

import com.google.gson.annotations.SerializedName;

import edu.publishPDF.model.users.User;

public class Suscriptor extends User {

    @SerializedName("_tags")
    private String[] tags = new String[0];

    Suscriptor(String username, String password) {
        super(username, password);
    }

    public String[] getTags() {
        return this.tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
    
}
