package com.inditex.java.spring.application.pictures.dto;

import com.inditex.java.spring.infrastructure.dto.AlbumResponseBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhotoBase {
    private int id;
    private String title;
    private String url;
    private String thumbnailUrl;
}
