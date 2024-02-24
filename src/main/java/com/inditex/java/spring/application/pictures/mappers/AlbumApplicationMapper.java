package com.inditex.java.spring.application.pictures.mappers;


import com.inditex.java.spring.application.pictures.dto.AlbumDTO;
import com.inditex.java.spring.domain.generic.GenericMapper;
import com.inditex.java.spring.infrastructure.album.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy =
        ReportingPolicy.IGNORE)
public abstract class AlbumApplicationMapper implements GenericMapper<Album, AlbumDTO> {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "photos", source = "photos")
    @Override
    public abstract AlbumDTO toDTO(Album photo);



}
