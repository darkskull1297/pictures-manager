package com.inditex.java.spring.domain.pictures;


import com.inditex.java.spring.infrastructure.album.Album;
import com.inditex.java.spring.infrastructure.album.AlbumRepository;
import com.inditex.java.spring.infrastructure.photos.Photo;
import com.inditex.java.spring.infrastructure.photos.PhotoRepository;
import com.inditex.java.spring.infrastructure.dto.AlbumResponseBody;
import com.inditex.java.spring.infrastructure.dto.PhotoResponseBody;
import com.inditex.java.spring.infrastructure.pictures.PictureClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class PictureServiceImpl implements PictureService {

    private PictureClientService clienteService;
    private AlbumRepository albumRepository;
    private PhotoRepository photoRepository;
    private AlbumInfrastructureMapper albumInfrastructureMapper;
    private PhotoInfrastructureMapper photoInfrastructureMapper;
    @Override
    public String initializeDataBase() {
        List<AlbumResponseBody> albumList = Arrays.stream(clienteService.getAlbums()).toList();
        albumRepository.saveAll(albumInfrastructureMapper.toEntities(albumList));
        long photoQty = 0L;
        for (AlbumResponseBody album : albumList) {
            List<PhotoResponseBody> photoList = Arrays.stream(clienteService.getPhotos(album.getId())).toList();
            photoRepository.saveAll(photoInfrastructureMapper.toEntities(photoList));
            photoQty = photoQty + photoList.size();
        }
        return "Added to the data base " + albumList.size() + " Albums and " + photoQty + " photos";
    }

    @Override
    public AlbumResponseBody getAlbumsById(int id) {
        return clienteService.getAlbumsById(id);
    }

    @Override
    @Transactional
    public Photo getPhotoById(int id) {
        return photoRepository.getReferenceById(String.valueOf(id));
    }
    @Override
    @Transactional
    public Album getAlbumByIdFromDataBase(int id) {
        return albumRepository.getReferenceById(String.valueOf(id));
    }
}
