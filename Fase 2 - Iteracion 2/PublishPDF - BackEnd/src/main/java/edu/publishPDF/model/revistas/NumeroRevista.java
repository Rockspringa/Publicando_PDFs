package edu.publishPDF.model.revistas;

import edu.publishPDF.model.errores.InvalidInputType;
import edu.publishPDF.tools.InputValidator;

public class NumeroRevista {

    private final int id;
    private final int revista;
    private final String archivo;

    private NumeroRevista(int id, int revista, String archivo) {
        this.id = id;
        this.revista = revista;
        this.archivo = archivo;
    }

    public static NumeroRevista createNumeroRevista(int id, int revista, String archivo) throws InvalidInputType {
        int val = (InputValidator.isUnsignedInt(id)) ? 1 : 0;
        val += (InputValidator.isUnsignedInt(revista)) ? 1 : 0;

        if (val != 2)
            throw new InvalidInputType();

        return new NumeroRevista(id, revista, archivo);
    }

    public int getId() {
        return id;
    }

    public int getRevista() {
        return revista;
    }

    public String getArchivo() {
        return archivo;
    }

}
