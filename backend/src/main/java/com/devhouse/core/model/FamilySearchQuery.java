package com.devhouse.core.model;

public record FamilySearchQuery(String search, int page, int size, String sortBy, String sortDirection) {
}
