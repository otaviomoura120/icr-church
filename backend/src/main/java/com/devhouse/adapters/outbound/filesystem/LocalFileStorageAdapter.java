package com.devhouse.adapters.outbound.filesystem;

import com.devhouse.core.ports.outbound.FileStoragePort;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Singleton
public class LocalFileStorageAdapter implements FileStoragePort {

    private final String basePath;

    public LocalFileStorageAdapter(@Value("${file.storage.base-path:/public}") String basePath) {
        this.basePath = basePath;
    }

    @Override
    public String save(String relativePath, byte[] data) {
        try {
            Path fullPath = getFullPath(relativePath);

            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, data);
            return relativePath;
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to save file: " + relativePath, e);
        }
    }

    @Override
    public void delete(String relativePath) {
        try {
            Path fullPath = getFullPath(relativePath);
            Files.deleteIfExists(fullPath);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to delete file: " + relativePath, e);
        }
    }

    private Path getFullPath(String relativePath) {
        return Paths.get(
                System.getProperty("user.dir"),
                basePath,
                relativePath
        );
    }
}
