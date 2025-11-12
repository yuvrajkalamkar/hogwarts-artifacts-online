package com.hogwarts.wiz.artifact.converter;

import com.hogwarts.wiz.artifact.Artifact;
import com.hogwarts.wiz.artifact.ArtifactDTO;
import com.hogwarts.wiz.wizard.converter.WizardToWizardDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Yuvraj_Kalamkar
 */
@Component
public class ArtifactToArtifactDtoConverter implements Converter<Artifact, ArtifactDTO> {
    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;

    public ArtifactToArtifactDtoConverter(WizardToWizardDtoConverter wizardToWizardDtoConverter){
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }
    @Override
    public ArtifactDTO convert(Artifact source) {
        return new ArtifactDTO(source.getId(),
                source.getName(),
                source.getDescription(),
                source.getImageUrl(),
                source.getOwner()!=null? this.wizardToWizardDtoConverter.convert(source.getOwner()) : null);
    }

    public List<ArtifactDTO> convertList(List <Artifact> sourceList) {
        List<ArtifactDTO> dtoList = new ArrayList<>();
        for(Artifact source : sourceList){
            dtoList.add(new ArtifactDTO(source.getId(),
                    source.getName(),
                    source.getDescription(),
                    source.getImageUrl(),
                    source.getOwner()!=null? this.wizardToWizardDtoConverter.convert(source.getOwner()) : null));
        }
        return dtoList;
    }
}
