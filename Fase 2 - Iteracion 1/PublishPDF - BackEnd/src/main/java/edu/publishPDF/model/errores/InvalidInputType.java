package edu.publishPDF.model.errores;

public class InvalidInputType extends Exception {

    public InvalidInputType() {
        super("El parametro ingresado, no es valido.");
    }
    
    public InvalidInputType(String param, String type) {
        super("El parametro " + param + " no es un " + type + " valido.");
    }
}
