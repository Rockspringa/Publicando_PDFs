package edu.publishPDF.tools;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputReader implements Closeable {

    private BufferedReader bufRead;
    private InputStream input;

    public InputReader(BufferedReader bufRead) {
        this.bufRead = bufRead;
    }

    public InputReader(InputStream input) {
        this.input = input;
    }

    public String readInput() throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        String readline;

        try (BufferedReader reader = (this.bufRead != null) ? this.bufRead
                : new BufferedReader(new InputStreamReader(input))) {

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
            } else if (this.input != null) {
                this.input.close();
                this.input = null;
            }
        } catch (IOException e) {
        }
    }
}
