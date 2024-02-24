package com.inditex.java.spring.domain.pictures;


import com.inditex.java.spring.infrastructure.album.Album;
import com.inditex.java.spring.infrastructure.photos.Photo;
import com.inditex.java.spring.infrastructure.dto.AlbumResponseBody;
import org.springframework.transaction.annotation.Transactional;

public interface PictureService {

    String initializeDataBase();

    AlbumResponseBody getAlbumsById(int id);

    Photo getPhotoById(int id);

    @Transactional
    Album getAlbumByIdFromDataBase(int id);
}
