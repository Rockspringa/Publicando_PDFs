package edu.publishPDF.tools.filesTools;

import javax.servlet.http.Part;

import edu.publishPDF.model.errores.InvalidInputType;

public class FilePartGetter {
    private String[] data;
    private Part filePart;
    private String path;
    private String type;

    public FilePartGetter(Part partFile, String datosUsuario) throws InvalidInputType {
        this.data = datosUsuario.split("/");
        this.filePart = partFile;
        this.type = getFileType();

        if (this.type == null) {
            throw new InvalidInputType();
        }
    }

    private void setPath() throws InvalidInputType {
        this.path = "";
        if (data.length == 3 || data.length == 2) {
            for (int i = 1; i < data.length; i++) {
                this.path += data[i] + "/";
            }
        } else {
            throw new InvalidInputType();
        }
    }

    private String getFileType() throws InvalidInputType {
        String contentType = this.filePart.getContentType();
        
        for (AcceptedTypeFile acceptedType : AcceptedTypeFile.values()) {
            if (acceptedType.isThisTypeMe(contentType)) {
                return acceptedType.toString();
            }
        }
        return null;
    }

    public String getPath() throws InvalidInputType {
        if (this.path == null) {
            setPath();
        }
        return this.path;
    }

    public String getType() {
        return this.type;
    }

    private enum AcceptedTypeFile {
        PDF("application/pdf"), IMG("image/jpeg", "image/png", "image/gif");

        private final String[] contentType;

        private AcceptedTypeFile(String ...contentType) {
            this.contentType = contentType;
        }

        public boolean isThisTypeMe(String type) {
            for (String contType : contentType)
                if (type.equals(contType))
                    return true;
            
            return false;
        }
    }
    
}

