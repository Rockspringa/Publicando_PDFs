package edu.publishPDF.tools.filesTools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.Part;

import edu.publishPDF.model.errores.InvalidInputType;

public class FilePasser {

    private String username;
    private String filename;
    private String root;
    private File outputFile;
    private FilePartGetter getter;
    private Part filePart;
    private String relativePath;

    public FilePasser(String root, Part partFile, String userData) throws InvalidInputType {
        this.root = root;
        this.filePart = partFile;
        this.username = userData.split("/")[1];
        this.filename = partFile.getSubmittedFileName();
        this.getter = new FilePartGetter(partFile, userData);
    }

    public boolean passFile() throws InvalidInputType, IllegalStateException, IOException {
        setPath();
        createFolders();

        try (InputStream passer = filePart.getInputStream()) {
            Files.copy(passer, this.outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return true;
    }

    public String getUsername() {
        return this.username;
    }

    public String getType() {
        return this.getter.getType();
    }

    public String getPath() {
        return this.relativePath;
    }

    private void setPath() throws InvalidInputType {
        this.relativePath = this.getter.getPath() + this.filename;
        this.outputFile = new File(this.root + this.relativePath);
    }

    private void createFolders() throws IllegalStateException {
        File parentsDirs = outputFile.getParentFile();
        if (parentsDirs != null && !parentsDirs.exists() && !parentsDirs.mkdirs()) {
            throw new IllegalStateException("No se pudo crear el directorio: " + parentsDirs);
        }
    }
}