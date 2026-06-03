package com.devhouse.core.ports.outbound;

public interface FileStoragePort {
    String save(String relativePath, byte[] data);
    void delete(String relativePath);
}
