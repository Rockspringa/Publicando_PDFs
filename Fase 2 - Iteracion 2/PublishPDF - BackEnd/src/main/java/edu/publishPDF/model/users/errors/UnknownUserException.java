package edu.publishPDF.model.users.errors;

public class UnknownUserException extends RuntimeException {
    
    @Override
    public String getMessage() {
        return "El tipo de usuario ingresado no existe.";
    }
}
