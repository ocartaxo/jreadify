package br.com.paradizo.tema;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
