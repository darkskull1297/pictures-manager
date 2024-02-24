package com.inditex.java.spring.application.pictures.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoDTO {
    private int id;
    private AlbumBase albumId;
    private String title;
    private String url;
    private String thumbnailUrl;
}
