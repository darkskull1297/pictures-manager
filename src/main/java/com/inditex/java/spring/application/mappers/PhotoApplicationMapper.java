package com.inditex.java.spring.application.mappers;


import com.inditex.java.spring.application.dto.PhotoDTO;
import com.inditex.java.spring.domain.generic.GenericMapper;
import com.inditex.java.spring.infrastructure.photos.Photo;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy =
        ReportingPolicy.IGNORE)
public abstract class PhotoApplicationMapper implements GenericMapper<Photo, PhotoDTO> {

    @Mapping(target = "albumId", source = "albumId")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "thumbnailUrl", source = "thumbnailUrl")
    @Override
    public abstract PhotoDTO toDTO(Photo photo);


}
