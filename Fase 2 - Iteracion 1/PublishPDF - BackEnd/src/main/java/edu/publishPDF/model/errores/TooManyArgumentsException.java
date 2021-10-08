package edu.publishPDF.model.errores;

public class TooManyArgumentsException extends Exception {

    private final int maxArguments;

    public TooManyArgumentsException(int maxArguments) {
        this.maxArguments = maxArguments;
    }
    
    @Override
    public String getMessage() {
        return "Demasiados argumentos, se esperaban maximo " + maxArguments;
    }
}
