package com.hogwarts.wiz.artifact;

import com.hogwarts.wiz.artifact.utils.IdWorker;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yuvraj_Kalamkar
 */
@Service
@Transactional
public class ArtifactService {
    private final ArtifactRepository artifactRepository;
    private final IdWorker idWorker;

    public ArtifactService(ArtifactRepository artifactRepository, IdWorker idWorker) {
        this.artifactRepository = artifactRepository;
        this.idWorker = idWorker;
    }

    /**
     * @param artifactId
     * @return Artifact
     */
    public Artifact findById(String artifactId) {
        return artifactRepository.findById(artifactId).orElseThrow(() ->
                new ArtifactNotFoundException(artifactId)
        );
    }

    public List<Artifact> findAll() {
        return artifactRepository.findAll();
    }

    public Artifact saveNewArtifact(Artifact newArtifact) {
        newArtifact.setId(idWorker.nextId() + "");
        return this.artifactRepository.save(newArtifact);
    }

    public Artifact updateArtifact(String artifactId, Artifact updateArtifact) {
        return artifactRepository.findById(artifactId)
                .map(oldArtifact -> {
                    oldArtifact.setName(updateArtifact.getName());
                    oldArtifact.setDescription(updateArtifact.getDescription());
                    oldArtifact.setImageUrl(updateArtifact.getImageUrl());
                    return artifactRepository.save(oldArtifact);
                }).orElseThrow(() -> new ArtifactNotFoundException(artifactId));
    }

    public void delete(String artifactId) {
        this.artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ArtifactNotFoundException(artifactId));
        this.artifactRepository.deleteById(artifactId);
    }
}
