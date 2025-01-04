package org.example.devnet.projectshowcase.helpers;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileHelperImpl implements FileHelper {


    @Override
    public String uploadFile(String folder, String fileName, byte[] bytes) {
        try {
            if (!Files.exists(Paths.get(folder))) {
                Files.createDirectories(Paths.get(folder));
            }
            String newFileName = UUID.randomUUID() + "-" + fileName;

            Path filePath = Paths.get(Paths.get(folder).toString(), newFileName);

            Files.write(filePath, bytes);

            return newFileName;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    }

