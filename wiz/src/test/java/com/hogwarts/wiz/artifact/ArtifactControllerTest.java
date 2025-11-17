package com.hogwarts.wiz.artifact;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogwarts.wiz.system.StatusCode;
import com.hogwarts.wiz.system.expection.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Yuvraj_Kalamkar
 */
@SpringBootTest
@AutoConfigureMockMvc
class ArtifactControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ArtifactService artifactService;

    @Autowired
    ObjectMapper objectMapper;

    List<Artifact> artifactList;

    @BeforeEach
    void setUp() {
        this.artifactList = new ArrayList<>();
        Artifact a1 = new Artifact();
        a1.setId("1250808601744904191");
        a1.setName("Deluminator");
        a1.setDescription("A Deluminator is a device invented by Albus Dumbledore that resembles a cigarette lighter. It is used to remove or absorb (as well as return) the light from any light source to provide cover to the user.");
        a1.setImageUrl("ImageUrl");
        this.artifactList.add(a1);

        Artifact a2 = new Artifact();
        a2.setId("1250808601744904192");
        a2.setName("Invisibility Cloak");
        a2.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a2.setImageUrl("ImageUrl");
        this.artifactList.add(a2);

        Artifact a3 = new Artifact();
        a3.setId("1250808601744904193");
        a3.setName("Elder Wand");
        a3.setDescription("The Elder Wand, known throughout history as the Deathstick or the Wand of Destiny, is an extremely powerful wand made of elder wood with a core of Thestral tail hair.");
        a3.setImageUrl("ImageUrl");
        this.artifactList.add(a3);

        Artifact a4 = new Artifact();
        a4.setId("1250808601744904194");
        a4.setName("The Marauder's Map");
        a4.setDescription("A magical map of Hogwarts created by Remus Lupin, Peter Pettigrew, Sirius Black, and James Potter while they were students at Hogwarts.");
        a4.setImageUrl("ImageUrl");
        this.artifactList.add(a4);

        Artifact a5 = new Artifact();
        a5.setId("1250808601744904195");
        a5.setName("The Sword Of Gryffindor");
        a5.setDescription("A goblin-made sword adorned with large rubies on the pommel. It was once owned by Godric Gryffindor, one of the medieval founders of Hogwarts.");
        a5.setImageUrl("ImageUrl");
        this.artifactList.add(a5);

        Artifact a6 = new Artifact();
        a6.setId("1250808601744904196");
        a6.setName("Resurrection Stone");
        a6.setDescription("The Resurrection Stone allows the holder to bring back deceased loved ones, in a semi-physical form, and communicate with them.");
        a6.setImageUrl("ImageUrl");
        this.artifactList.add(a6);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findArtifactByIdSuccess() throws Exception {
        //given
        given(this.artifactService.findById("1250808601744904191")).willReturn(this.artifactList.get(0));
        //when & Then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/artifact/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904191"));

    }

    @Test
    void findArtifactByIdNotFound() throws Exception {
        //given
        given(this.artifactService.findById("1250808601744904191")).willThrow(new ObjectNotFoundException("artifact", "1250808601744904191"));
        //when & Then
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/artifact/1250808601744904191").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with id: 1250808601744904191"))
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    void testAddArtifactSuccess() throws Exception {
        //given
        ArtifactDTO artifactDTO = new ArtifactDTO(null,
                "Remembrall",
                "A Remembrall was a magical large marble-sized glass ball that contained smoke which turned red when its owner or user had forgotten something. It turned clear once whatever was forgotten was remembered.",
                "ImageUrl",
                null);
        String jsonString = this.objectMapper.writeValueAsString(artifactDTO);

        Artifact savedArtifact = new Artifact();
        savedArtifact.setId("1250808601744904197");
        savedArtifact.setName("Remembrall");
        savedArtifact.setDescription("A Remembrall was a magical large marble-sized glass ball that contained smoke which turned red when its owner or user had forgotten something. It turned clear once whatever was forgotten was remembered.");
        savedArtifact.setImageUrl("ImageUrl");

        given(this.artifactService.saveNewArtifact(Mockito.any(Artifact.class))).willReturn(savedArtifact);
        //when & then
        this.mockMvc.perform(post("/api/v1/artifact").contentType(MediaType.APPLICATION_JSON).content(jsonString).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value(savedArtifact.getName()))
                .andExpect(jsonPath("$.data.description").value(savedArtifact.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(savedArtifact.getImageUrl()));
    }

    @Test
    void testUpdateArtifactSuccess() throws Exception {
        //given
        ArtifactDTO artifactDTO = new ArtifactDTO("1250808601744904192",
                "Invisibility Cloak",
                "An invisibility cloak is used to make the wearer invisible.",
                "ImageUrl",
                null);
        String jsonString = this.objectMapper.writeValueAsString(artifactDTO);

        Artifact update = new Artifact();
        update.setId("1250808601744904192");
        update.setName("Invisibility Cloak");
        update.setDescription("An invisibility cloak is used to make the wearer invisible.");
        update.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/invisibility-cloak.jpg");

        given(this.artifactService.updateArtifact(eq("1250808601744904192"),Mockito.any(Artifact.class))).willReturn(update);
        //when & then
        this.mockMvc.perform(put("/api/v1/artifact/" + "1250808601744904192").contentType(MediaType.APPLICATION_JSON).content(jsonString).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").value("1250808601744904192"))
                .andExpect(jsonPath("$.data.name").value(update.getName()))
                .andExpect(jsonPath("$.data.description").value(update.getDescription()))
                .andExpect(jsonPath("$.data.imageUrl").value(update.getImageUrl()));
    }

    @Test
    void testUpdateArtifactErrWithNonExistingId() throws Exception {
        //given
        ArtifactDTO artifactDTO = new ArtifactDTO("1250808601744904192",
                "Invisibility Cloak",
                "An invisibility cloak is used to make the wearer invisible.",
                "ImageUrl",
                null);
        String jsonString = this.objectMapper.writeValueAsString(artifactDTO);

        given(this.artifactService.updateArtifact(eq("1250808601744904192"),Mockito.any(Artifact.class))).willThrow(new ObjectNotFoundException("artifact", "1250808601744904192"));
        //when & then
        this.mockMvc.perform(put("/api/v1/artifact/" + "1250808601744904192").contentType(MediaType.APPLICATION_JSON).content(jsonString).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with id: 1250808601744904192"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteArtifactSuccess() throws Exception {
        //given
        doNothing().when(this.artifactService).delete("1250808601744904192");
        //when & then
        this.mockMvc.perform(delete("/api/v1/artifact/" + "1250808601744904192").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success: " + "1250808601744904192"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testDeleteArtifactErrWithNonExistingId() throws Exception {
        //given
        doThrow(new ObjectNotFoundException("artifact", "1250808601744904192")).when(this.artifactService).delete("1250808601744904192");
        //when & then
        this.mockMvc.perform(delete("/api/v1/artifact/" + "1250808601744904192").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find artifact with id: 1250808601744904192"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}