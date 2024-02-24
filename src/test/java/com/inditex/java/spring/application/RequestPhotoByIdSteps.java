package com.inditex.java.spring.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.java.spring.application.dto.AlbumBase;
import com.inditex.java.spring.application.dto.PhotoDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

@Slf4j
@DirtiesContext
@CucumberContextConfiguration
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class RequestPhotoByIdSteps {
    private static final String PHOTO_BY_ID = "/local/storage/photo/50";
    private static final String INITIALIZE = "/initialize";


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private MvcResult resultOut;

    @Given("Loading the data into a H2 BBDD from Urls")
    public void loadingTheDataIntoAHBBDDFromUrls() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(INITIALIZE))
                .andReturn();
    }

    @When("I send a GET request")
    public void iSendAGETRequest() throws Exception {
        resultOut = this.mockMvc.perform(MockMvcRequestBuilders.get(PHOTO_BY_ID))
                .andReturn();
    }

    @Then("I verify the data received")
    public void iVerifyTheDataReceived() throws IOException {
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setId(50);
        photoDTO.setUrl("https://via.placeholder.com/600/9e59da");
        photoDTO.setTitle("et inventore quae ut tempore eius voluptatum");
        photoDTO.setThumbnailUrl("https://via.placeholder.com/150/9e59da");
        photoDTO.setAlbumId(AlbumBase.builder()
                .id(1).userId(1).title("quidem molestiae enim").build());
        PhotoDTO photoDTOResult = objectMapper.readValue(resultOut.getResponse().getContentAsByteArray(), PhotoDTO.class);
        Assertions.assertThat(photoDTOResult).isEqualTo(photoDTO);
    }

}
