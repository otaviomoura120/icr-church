package com.devhouse.core.model;

public record SearchQuery(String search, int page, int size, String sortBy, String sortDirection) {

    public static SearchQuery withDefaults(String search, Integer page, Integer size, String sortBy, String sortDirection) {
        return new SearchQuery(
                search,
                page != null ? page : 0,
                size != null ? size : 20,
                sortBy != null ? sortBy : "name",
                sortDirection != null ? sortDirection : "ASC"
        );
    }
}
