package com.inditex.java.spring.infrastructure.pictures;

import com.inditex.java.spring.infrastructure.dto.AlbumResponseBody;
import com.inditex.java.spring.infrastructure.dto.PhotoResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.ExecutionException;

@Component
@Slf4j
public class PictureClientServiceImpl implements PictureClientService {

    private WebClient webClient;

    private String baseUrl;
    private String albumUrl;
    private String photosUrl;

    public PictureClientServiceImpl(WebClient.Builder webClient, @Value("${client.albums}") String albumUrl,
                                    @Value("${client.photos}") String photosUrl,
                                    @Value("${client.baseurl}") String baseUrl) {
        this.webClient = webClient.baseUrl(baseUrl).build();
        this.albumUrl = albumUrl;
        this.photosUrl = photosUrl;
        this.baseUrl = baseUrl;
    }

    @Override
    public AlbumResponseBody[] getAlbums() {
        ResponseEntity<AlbumResponseBody[]> albums;
        try {
            albums = webClient.get().uri(albumUrl).retrieve().
                    onStatus(httpStatus -> httpStatus.is4xxClientError() && httpStatus != HttpStatus.NOT_FOUND,
                            response -> null)
                    .toEntity(AlbumResponseBody[].class).toFuture().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return albums.getBody();
    }
    @Override
    public PhotoResponseBody[] getPhotos(int id) {
        ResponseEntity<PhotoResponseBody[]> photos;
        try {
            photos = webClient.get().uri(albumUrl + "/{id}/photos", id).retrieve().
                    onStatus(httpStatus -> httpStatus.is4xxClientError() && httpStatus != HttpStatus.NOT_FOUND,
                            response -> null)
                    .toEntity(PhotoResponseBody[].class).toFuture().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return photos.getBody();
    }

    @Override
    public AlbumResponseBody getAlbumsById(int id) {
        ResponseEntity<AlbumResponseBody> album;
        try {
            album = webClient.get().uri(albumUrl + "/{id}", id).retrieve().
                    onStatus(httpStatus -> httpStatus.is4xxClientError() && httpStatus != HttpStatus.NOT_FOUND,
                            response -> null)
                    .toEntity(AlbumResponseBody.class).toFuture().get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return album.getBody();
    }
}
