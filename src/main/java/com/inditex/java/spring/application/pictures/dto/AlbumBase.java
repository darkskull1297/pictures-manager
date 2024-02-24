package com.inditex.java.spring.application.pictures.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumBase {
    private int userId;
    private int id;
    private String title;
}
