package com.inditex.java.spring.domain.pictures;

import com.inditex.java.spring.domain.generic.GenericMapper;
import com.inditex.java.spring.infrastructure.album.Album;
import com.inditex.java.spring.infrastructure.album.AlbumRepository;
import com.inditex.java.spring.infrastructure.dto.PhotoResponseBody;
import com.inditex.java.spring.infrastructure.photos.Photo;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy =
        ReportingPolicy.IGNORE)
public abstract class PhotoInfrastructureMapper implements GenericMapper<Photo, PhotoResponseBody> {
    @Autowired
    private AlbumRepository albumRepository;

    @Mapping(target = "albumId", source = "albumId.id")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "thumbnailUrl", source = "thumbnailUrl")
    @Override
    public abstract PhotoResponseBody toDTO(Photo photo);

    @Mapping(target = "albumId", qualifiedByName="intToAlbum", source = "albumId")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "thumbnailUrl", source = "thumbnailUrl")
    @Override
    public abstract Photo toEntity(PhotoResponseBody photoResponseBody);

    @Named("intToAlbum")
    public Album intToAlbum(int albumId) {
        return albumRepository.getReferenceById(String.valueOf(albumId));
    }
}
