package com.inditex.java.spring.infrastructure.photos;

import com.inditex.java.spring.infrastructure.album.Album;
import com.inditex.java.spring.infrastructure.generic.GenericEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PHOTO")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
public class Photo extends GenericEntity {
    @JoinColumn(name = "ALBUM_ID")
    @ManyToOne
    private Album albumId;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "URL")
    private String url;
    @Column(name = "THUMBNAIL_URL")
    private String thumbnailUrl;
}
