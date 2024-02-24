package com.inditex.java.spring.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.java.spring.application.dto.AlbumBase;
import com.inditex.java.spring.application.dto.PhotoDTO;
import com.inditex.java.spring.domain.pictures.PictureService;
import com.inditex.java.spring.infrastructure.album.Album;
import com.inditex.java.spring.infrastructure.dto.AlbumResponseBody;
import com.inditex.java.spring.infrastructure.pictures.PictureClientService;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
@SpringBootTest
@AutoConfigureMockMvc
class PicturesControllerTest  {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PictureClientService clienteService;
    @Autowired
    private PictureService pictureService;


    private static final String INITIALIZE = "/initialize";
    private static final String ALBUMS = "/webclient/albums/2";
    private static final String PHOTO_BY_ID = "/local/storage/photo/5001";


    @Test
    void initilizeDataBase() throws Exception {
        MvcResult resultOut = this.mockMvc.perform(MockMvcRequestBuilders.get(INITIALIZE))
                .andReturn();
        Assertions.assertThat(resultOut.getResponse().getContentAsString()).isEqualTo("Added to the data base 100 Albums and 5000 photos");
    }
    @Test
    void getAlbums() throws Exception {
        MvcResult resultOut = this.mockMvc.perform(MockMvcRequestBuilders.get(ALBUMS))
                .andReturn();
        AlbumResponseBody albumResult = objectMapper.readValue(resultOut.getResponse().getContentAsByteArray(), AlbumResponseBody.class);
        Assertions.assertThat(albumResult).isEqualTo(AlbumResponseBody.builder().userId(1).id(2).title("sunt qui excepturi placeat culpa").build());
    }
    @Test
    void getPhotoById() throws Exception {

        MvcResult resultOut = this.mockMvc.perform(MockMvcRequestBuilders.get(PHOTO_BY_ID))
                .andReturn();
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setId(5001);
        photoDTO.setUrl("https://via.placeholder.com/600/9e59da");
        photoDTO.setTitle("et inventore quae ut tempore eius voluptatum");
        photoDTO.setThumbnailUrl("https://via.placeholder.com/150/9e59da");
        photoDTO.setAlbumId(AlbumBase.builder()
                .id(101).userId(1).title("quidem molestiae enim").build());
        PhotoDTO photoDTOResult = objectMapper.readValue(resultOut.getResponse().getContentAsByteArray(), PhotoDTO.class);
        Assertions.assertThat(photoDTOResult).isEqualTo(photoDTO);
    }

    @Test
    void test_Web_Client_To_Get_Albums() {
        AlbumResponseBody[] albums = clienteService.getAlbums();
        Assertions.assertThat(albums.length).isEqualTo(100);
    }
    @Test
    void test_Service_To_Get_Albums_From_An_API_Call() {
        AlbumResponseBody albumsById = pictureService.getAlbumsById(2);
        Assertions.assertThat(albumsById).isEqualTo(AlbumResponseBody.builder().userId(1).id(2).title("sunt qui excepturi placeat culpa").build());
    }
    @Test
    @Transactional
    void test_Service_To_Get_Albums_With_Photos_From_DataBase() {
        Album albumByIdFromDataBase = pictureService.getAlbumByIdFromDataBase(101);
        Assertions.assertThat(albumByIdFromDataBase.getPhotos().size()).isEqualTo(1);
        Assertions.assertThat(albumByIdFromDataBase.getPhotos().get(0).getId()).isEqualTo(5001);
        Assertions.assertThat(albumByIdFromDataBase.getId()).isEqualTo(101);
        Assertions.assertThat(albumByIdFromDataBase.getTitle()).isEqualTo("quidem molestiae enim");
    }

}