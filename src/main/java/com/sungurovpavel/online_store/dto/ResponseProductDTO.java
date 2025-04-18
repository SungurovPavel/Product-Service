package com.sungurovpavel.online_store.dto;

import lombok.Builder;
import lombok.Value;
import java.util.List;

@Value
@Builder
public class ResponseProductDTO {
    List<ProductDTO> products;
    int totalPages;
    int currentPage;
    long totalElements;
    int pageSize;
}
