package com.hogwarts.wiz.wizard;

import com.hogwarts.wiz.artifact.Artifact;
import com.hogwarts.wiz.artifact.ArtifactRepository;
import com.hogwarts.wiz.system.expection.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yuvraj_Kalamkar
 */
@Service
public class WizardService {
    private final WizardRepository wizardRepository;
    private final ArtifactRepository artifactRepository;

    public WizardService(WizardRepository wizardRepository, ArtifactRepository artifactRepository) {
        this.wizardRepository = wizardRepository;
        this.artifactRepository = artifactRepository;
    }
    public Wizard findWizardById(Integer wizardId){
        return this.wizardRepository.findById(wizardId).orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));
    }
    public List<Wizard> findAllWizards(){
        return this.wizardRepository.findAll();
    }
    public Wizard addWizard(Wizard wizard){
        return this.wizardRepository.save(wizard);
    }
    public Wizard updateWizard(Wizard updateWizard, Integer wizardId){
        Wizard foundWizard = this.wizardRepository.findById(wizardId).orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));
        foundWizard.setName(updateWizard.getName());
        return this.wizardRepository.save(foundWizard);
    }
    public void deleteWizard(Integer wizardId){
        Wizard wizardToBeDeleted = this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));

        // Before deletion, we will unassign this wizard's owned artifacts.
        wizardToBeDeleted.removeAllArtifacts();
        this.wizardRepository.deleteById(wizardId);
    }
    public void addArtifactToWizard(Integer wizardId, String artifactId){
        Wizard wizard = this.findWizardById(wizardId);
        Artifact artifact = this.artifactRepository.findById(artifactId).orElseThrow(() -> new ObjectNotFoundException("artifact", artifactId));
        // Artifact assignment
        // We need to see if the artifact is already owned by some wizard.
        if (artifact.getOwner() != null) {
            artifact.getOwner().removeArtifact(artifact);
        }
        wizard.addArtifact(artifact);
    }
}
