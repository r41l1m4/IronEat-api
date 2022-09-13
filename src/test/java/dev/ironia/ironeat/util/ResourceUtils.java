package dev.ironia.ironeat.util;

import java.io.IOException;
import java.io.InputStream;

public class ResourceUtils {

    public static String getContentFromResource(String resourceName) {
        try {
            InputStream stream = ResourceUtils.class.getClassLoader().getResourceAsStream(resourceName);
            return new String(stream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}