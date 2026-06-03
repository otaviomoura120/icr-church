package com.devhouse.core.model;

import java.util.List;

public record PagedResult<T>(List<T> content, long totalElements, int totalPages, int page, int size) {
}
