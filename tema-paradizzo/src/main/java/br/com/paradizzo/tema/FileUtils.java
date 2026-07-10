package br.com.paradizzo.tema;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Collections;

public class FileUtils {

    public static String getResourceContents(String resource) {
       try (InputStream in = FileUtils.class.getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalArgumentException("Recurso não encontrado: " + resource);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
       } catch (IOException e) {
           throw new RuntimeException("Erro ao ler o recurso " + resource, e);
       }
    }
}
