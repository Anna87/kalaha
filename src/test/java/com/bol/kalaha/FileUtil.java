package com.bol.kalaha;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;

@UtilityClass
public class FileUtil {
    public static String classpathFileToString(String path) throws IOException {
        return Files.readString(new ClassPathResource(path).getFile().toPath());
    }

}
