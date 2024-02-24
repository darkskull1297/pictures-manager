package com.inditex.java.spring.application.pictures;

import com.inditex.java.spring.application.dto.AlbumDTO;
import com.inditex.java.spring.application.dto.PhotoDTO;
import com.inditex.java.spring.application.mappers.AlbumApplicationMapper;
import com.inditex.java.spring.application.mappers.PhotoApplicationMapper;
import com.inditex.java.spring.domain.pictures.PictureService;
import com.inditex.java.spring.infrastructure.dto.AlbumResponseBody;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@AllArgsConstructor
public class PicturesController {


    private PictureService pictureService;
    private PhotoApplicationMapper photoApplicationMapper;
    private AlbumApplicationMapper albumApplicationMapper;

    @RequestMapping(value = "/initialize",
    produces = {"application/json"},
    method = RequestMethod.GET)
    public ResponseEntity<String> initializeDataBase() {
        return new ResponseEntity<>(pictureService.initializeDataBase(), HttpStatus.OK);
    }
    @RequestMapping(value = "/webclient/albums/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<AlbumResponseBody> getAlbums(@PathVariable("id") int id) {
        return new ResponseEntity<>(pictureService.getAlbumsById(id), HttpStatus.OK);
    }
    @RequestMapping(value = "/local/storage/photo/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<PhotoDTO> getPhotoById(@PathVariable("id") int id) {
        return new ResponseEntity<>(photoApplicationMapper.toDTO(pictureService.getPhotoById(id)), HttpStatus.OK);
    }
    @RequestMapping(value = "/local/storage/albums/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    @Transactional
    public ResponseEntity<AlbumDTO> getAlbumsFromMemoryById(@PathVariable("id") int id) {
        return new ResponseEntity<>(albumApplicationMapper.toDTO(pictureService.getAlbumByIdFromDataBase(id)), HttpStatus.OK);
    }
}
