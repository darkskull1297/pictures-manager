package com.inditex.java.spring.infrastructure.pictures;

import com.inditex.java.spring.infrastructure.dto.AlbumResponseBody;
import com.inditex.java.spring.infrastructure.dto.PhotoResponseBody;

public interface PictureClientService {
    AlbumResponseBody[] getAlbums();

    PhotoResponseBody[] getPhotos(int id);

    AlbumResponseBody getAlbumsById(int id);
}
