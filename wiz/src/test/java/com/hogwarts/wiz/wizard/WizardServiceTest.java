package com.hogwarts.wiz.wizard;

import com.hogwarts.wiz.artifact.Artifact;
import com.hogwarts.wiz.artifact.ArtifactNotFoundException;
import com.hogwarts.wiz.artifact.ArtifactRepository;
import com.hogwarts.wiz.system.expection.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * @author Yuvraj_Kalamkar
 */
@ExtendWith(MockitoExtension.class)
class WizardServiceTest {

    @Mock
    WizardRepository wizardRepository;

    @Mock
    ArtifactRepository artifactRepository;

    @InjectMocks
    WizardService wizardService;

    List<Wizard> wizards;
    @BeforeEach
    void setUp() {
        Wizard w1 = new Wizard();
        w1.setId(1);
        w1.setName("Albus Dumbledore");

        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");

        Wizard w3 = new Wizard();
        w3.setId(3);
        w3.setName("Neville Longbottom");

        this.wizards = new ArrayList<>();
        this.wizards.add(w1);
        this.wizards.add(w2);
        this.wizards.add(w3);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindWizardByIdSuccess() {
        //given
        Wizard wizard = new Wizard();
        wizard.setId(1);
        wizard.setName("Albus Dumbledore");
        given(this.wizardRepository.findById(1)).willReturn(Optional.of(wizard));
        Wizard returnWizard = this.wizardService.findWizardById(1);
        //when
        assertThat(returnWizard.getId()).isEqualTo(wizard.getId());
        assertThat(returnWizard.getName()).isEqualTo(wizard.getName());
        //then
        verify(wizardRepository, times(1)).findById(1);
    }

    @Test
    void testFindWizardByIdNotFound() {
        //given
        given(this.wizardRepository.findById(5)).willReturn(Optional.empty());
        Throwable throwable = catchThrowable(() -> {
            Wizard returnedWizard = this.wizardService.findWizardById(5);
        });
        //when
        assertThat(throwable).isInstanceOf(ObjectNotFoundException.class).hasMessage("Could not find wizard with id: " + 5);
        //then
        verify(this.wizardRepository, times(1)).findById(5);
    }

    @Test
    void testFindAllWizardsSuccess() {
        //given
        given(this.wizardRepository.findAll()).willReturn(this.wizards);
        //when
        assertThat(this.wizardService.findAllWizards().size()).isEqualTo(wizards.size());
        //then
        verify(this.wizardRepository, times(1)).findAll();
    }

    @Test
    void testAddWizardsSuccess() {
        //give
        Wizard newWizard = new Wizard();
        newWizard.setName("Severus Snape");
        given(this.wizardRepository.save(newWizard)).willReturn(newWizard);
        //when
        assertThat(this.wizardService.addWizard(newWizard).getName()).isEqualTo(newWizard.getName());
        //then
        verify(this.wizardRepository, times(1)).save(newWizard);
    }

    @Test
    void testUpdateWizardSuccess(){
        //Given
        Wizard oldWizard = new Wizard();
        oldWizard.setId(4);
        oldWizard.setName("Severus Snape");

        Wizard update = new Wizard();
        update.setId(oldWizard.getId());
        update.setName("Hermione Granger");
        given(this.wizardRepository.findById(4)).willReturn(Optional.of(oldWizard));
        given(this.wizardRepository.save(oldWizard)).willReturn(oldWizard);
        Wizard updatedWizard = this.wizardService.updateWizard(update,4);

        //when
        assertThat(updatedWizard.getId()).isEqualTo(update.getId());
        assertThat(updatedWizard.getName()).isEqualTo(update.getName());
        //then
        verify(this.wizardRepository, times(1)).findById(4);
        verify(this.wizardRepository, times(1)).save(oldWizard);
    }

    @Test
    void testUpdateWizardNotFound(){
        Wizard update = new Wizard();
        update.setName("Hermione Granger");
        //given
        given(this.wizardRepository.findById(4)).willReturn(Optional.empty());
        //when
        assertThrows(ObjectNotFoundException.class, () ->{
            this.wizardService.updateWizard(update,4);
        });
        //then
        verify(this.wizardRepository, times(1)).findById(4);
    }

    @Test
    void testDeleteWizardSuccess(){
        //Given
        Wizard wizard = new Wizard();
        wizard.setId(4);
        wizard.setName("Severus Snape");
        given(this.wizardRepository.findById(4)).willReturn(Optional.of(wizard));
        // When
        this.wizardService.deleteWizard(4);

        // Then

        verify(this.wizardRepository, times(1)).deleteById(4);
    }
    @Test
    void testDeleteWizardNotFound(){
        //given
        given(this.wizardRepository.findById(4)).willReturn(Optional.empty());
        // When
        assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.deleteWizard(4);
        });

        // Then
        verify(this.wizardRepository, times(1)).findById(4);
    }

    @Test
    void testAddArtifactToWizardSuccess(){
        //given
        Wizard wizard = new Wizard();
        wizard.setId(1);
        wizard.setName("Albus Dumbledore");
        given(this.wizardRepository.findById(1)).willReturn(Optional.of(wizard));
        Artifact artifact = new Artifact();
        artifact.setId("1250808601744904190");
        artifact.setName("Fawkes");
        artifact.setDescription("Fawkes, and a powerful symbol of rebirth and resilience");
        artifact.setImageUrl("ImageUrl");
        given(this.artifactRepository.findById("1250808601744904190")).willReturn(Optional.of(artifact));
        //when
        this.wizardService.addArtifactToWizard(1, "1250808601744904190");
        // Then
        assertThat(artifact.getOwner().getId()).isEqualTo(1);
        assertThat(wizard.getArtifacts()).contains(artifact);
    }

    @Test
    void testAssignArtifactErrorWithNonExistentWizardId() {
        // Given
        Artifact a = new Artifact();
        a.setId("1250808601744904192");
        a.setName("Invisibility Cloak");
        a.setDescription("An invisibility cloak is used to make the wearer invisible.");
        a.setImageUrl("ImageUrl");

        Wizard w2 = new Wizard();
        w2.setId(2);
        w2.setName("Harry Potter");
        w2.addArtifact(a);

        given(this.wizardRepository.findById(3)).willReturn(Optional.empty());

        // When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.addArtifactToWizard(3, "1250808601744904192");
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find wizard with id: " + 3);
        assertThat(a.getOwner().getId()).isEqualTo(2);
    }

    @Test
    void testAssignArtifactErrorWithNonExistentArtifactId() {
        // Given
        Wizard wizard = new Wizard();
        wizard.setId(2);
        wizard.setName("Harry Potter");
        given(this.wizardRepository.findById(2)).willReturn(Optional.of(wizard));
        given(this.artifactRepository.findById("1250808601744904192")).willReturn(Optional.empty());

        // When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> {
            this.wizardService.addArtifactToWizard(2, "1250808601744904192");
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find artifact with id: " + "1250808601744904192");
    }
}