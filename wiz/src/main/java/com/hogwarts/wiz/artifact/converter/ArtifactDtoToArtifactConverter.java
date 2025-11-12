package com.hogwarts.wiz.artifact.converter;

import com.hogwarts.wiz.artifact.Artifact;
import com.hogwarts.wiz.artifact.ArtifactDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Yuvraj_Kalamkar
 */
@Component
public class ArtifactDtoToArtifactConverter implements Converter<ArtifactDTO, Artifact> {
    @Override
    public Artifact convert(ArtifactDTO source) {
        Artifact artifact = new Artifact();
        artifact.setId(source.id());
        artifact.setName(source.name());
        artifact.setDescription(source.description());
        artifact.setImageUrl(source.imageUrl());
        return artifact;
    }
}
