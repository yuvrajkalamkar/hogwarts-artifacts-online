package com.hogwarts.wiz.artifact;

import com.hogwarts.wiz.artifact.utils.IdWorker;
import com.hogwarts.wiz.wizard.Wizard;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Yuvraj_Kalamkar
 */
@ExtendWith(MockitoExtension.class)
class ArtifactServiceTest {
    @Mock
    ArtifactRepository artifactRepository;
    @Mock
    IdWorker idWorker;
    @InjectMocks
    ArtifactService artifactService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        //given. Arrange inputs & targets. Define the behavior of Mock object artifactRepository.
        Artifact artifact = new Artifact();
        artifact.setId("1250808601744904192");
        artifact.setName("Invisibility Cloak");
        artifact.setDescription("An invisibility cloak is used to make the wearer invisible.");
        artifact.setImageUrl("ImageUrl");
        Wizard wizard = new Wizard();
        wizard.setId(2);
        wizard.setName("Harry Potter");
        artifact.setOwner(wizard);
        given(artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(artifact)); // Define the behavior of mock object
        //when. Act on target behavior. When steps should cover the method to be tested.
        Artifact returnedArtifact = artifactService.findById("1250808601744904192");
        //Then. Assert expected output.
        Assertions.assertThat(returnedArtifact.getId().equals(artifact.getId()));
        Assertions.assertThat(returnedArtifact.getName().equals(artifact.getName()));
        Assertions.assertThat(returnedArtifact.getDescription().equals(artifact.getDescription()));
        Assertions.assertThat(returnedArtifact.getImageUrl().equals(artifact.getImageUrl()));
        verify(artifactRepository, times(1)).findById("1250808601744904192");
    }

    @Test
    void testFindByIdNotFound() {
        //given
        given(artifactRepository.findById(Mockito.any(String.class))).willReturn(Optional.empty());
        //when
        Throwable throwable = catchThrowable(() -> {
            Artifact returnedArtifact = artifactService.findById("1250808601744904192");
        });
        //then
        assertThat(throwable)
                .isInstanceOf(ArtifactNotFoundException.class)
                .hasMessage("Could not find artifact with id: 1250808601744904192");
        verify(artifactRepository, times(1)).findById("1250808601744904192");
    }
    @Test
    void testSaveSuccess() {
        //given
        Artifact artifact = new Artifact();
        artifact.setName("name"); artifact.setDescription("description"); artifact.setImageUrl("imageUrl");
        given(idWorker.nextId()).willReturn(123456L);
        given(artifactRepository.save(artifact)).willReturn(artifact);
        //when
        Artifact savedArtifact = artifactService.saveNewArtifact(artifact);
        //then
        assertThat(savedArtifact.getId()).isEqualTo("123456");
        assertThat(savedArtifact.getName()).isEqualTo(artifact.getName());
        assertThat(savedArtifact.getDescription()).isEqualTo(artifact.getDescription());
        assertThat(savedArtifact.getImageUrl()).isEqualTo(artifact.getImageUrl());

        verify(artifactRepository, times(1)).save(artifact);

    }
    @Test
    void testUpdateSuccess() {
        //given
        Artifact oldArtifact = new Artifact();
        oldArtifact.setId("1250808601744904192");
        oldArtifact.setName("Invisibility Cloak");
        oldArtifact.setDescription("An invisibility cloak is used to make the wearer invisible.");
        oldArtifact.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/invisibility-cloak.jpg");
        Wizard wizard = new Wizard();
        wizard.setId(2);
        wizard.setName("Harry Potter");
        oldArtifact.setOwner(wizard);
        Artifact update = new Artifact();
        update.setId("1250808601744904192");
        update.setName("Invisibility Cloak");
        update.setDescription("An invisibility cloak is used to make the wearer invisible.");
        update.setImageUrl("ImageUrl");
        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(oldArtifact));
        given(this.artifactRepository.save(oldArtifact)).willReturn(oldArtifact);
        //when
        Artifact updatedArtifact = artifactService.updateArtifact("1250808601744904192", update);
        //then
        assertThat(updatedArtifact.getId()).isEqualTo(update.getId());
        assertThat(updatedArtifact.getImageUrl()).isEqualTo(update.getImageUrl());
        verify(this.artifactRepository, times(1)).findById("1250808601744904192");
        verify(this.artifactRepository, times(1)).save(oldArtifact);
    }
    @Test
    void testUpdateNotFound() {
        //given
        Artifact oldArtifact = new Artifact();
        oldArtifact.setName("Invisibility Cloak");
        oldArtifact.setDescription("An invisibility cloak is used to make the wearer invisible.");
        oldArtifact.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/invisibility-cloak.jpg");
        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());
        //when
        //then
        assertThrows(ArtifactNotFoundException.class, () ->{
            artifactService.updateArtifact("1250808601744904192", oldArtifact);
        });
    }
    @Test
    void testDeleteByIdSuccess() {
        //given
        Artifact oldArtifact = new Artifact();
        oldArtifact.setId("1250808601744904192");
        oldArtifact.setName("Invisibility Cloak");
        oldArtifact.setDescription("An invisibility cloak is used to make the wearer invisible.");
        oldArtifact.setImageUrl("https://hogwartsartifactsonline.blob.core.windows.net/artifact-image-container/invisibility-cloak.jpg");
        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.of(oldArtifact));
        doNothing().when(this.artifactRepository).deleteById("1250808601744904192");
        //when
        artifactService.delete("1250808601744904192");
        //then
        verify(artifactRepository, times(1)).deleteById("1250808601744904192");
    }
    @Test
    void testDeleteByIdNotFound() {
        //given
        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());
        //when
        assertThrows(ArtifactNotFoundException.class, () ->{
            artifactService.delete("1250808601744904192");
        });
        //then
        verify(artifactRepository, times(1)).findById("1250808601744904192");
    }
}