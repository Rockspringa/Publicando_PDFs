package edu.publishPDF.tools;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;

public class InputReader implements Closeable {

    private BufferedReader bufRead;
    public InputReader(BufferedReader bufRead) {
        this.bufRead = bufRead;
    }


    public String readInput() throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        String readline;

        try (BufferedReader reader = this.bufRead) {
            while ((readline = reader.readLine()) != null) {
                strBuilder.append(readline);
            }
        }
        return (strBuilder.isEmpty()) ? null : strBuilder.toString();
    }

    @Override
    public void close() throws IOException {
        try {
            if (this.bufRead != null) {
                this.bufRead.close();
                this.bufRead = null;
            }
        } catch (IOException e) {
        }
    }
}
