package com.inditex.java.spring.infrastructure.album;


import com.inditex.java.spring.infrastructure.generic.GenericEntity;
import com.inditex.java.spring.infrastructure.photos.Photo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "ALBUM")
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class Album extends GenericEntity {
    @Column(name = "USER_ID")
    private int userId;
    @Column(name = "TITLE")
    private String title;
    @OneToMany(mappedBy = "albumId", fetch = FetchType.EAGER)
    private List<Photo> photos;
}
