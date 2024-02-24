package com.inditex.java.spring.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoResponseBody {
    public int id;
    private int albumId;
    private String title;
    private String url;

    private String thumbnailUrl;
}
