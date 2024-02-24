package com.inditex.java.spring.domain.pictures;


import com.inditex.java.spring.domain.generic.GenericMapper;
import com.inditex.java.spring.infrastructure.album.Album;
import com.inditex.java.spring.infrastructure.dto.AlbumResponseBody;
import com.inditex.java.spring.infrastructure.photos.PhotoRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE, unmappedTargetPolicy =
        ReportingPolicy.IGNORE, uses = {PhotoInfrastructureMapper.class})
public abstract class AlbumInfrastructureMapper implements GenericMapper<Album, AlbumResponseBody> {

    @Override
    public abstract AlbumResponseBody toDTO(Album album);

    @Override
    public abstract Album toEntity(AlbumResponseBody albumResponseBody);


}
