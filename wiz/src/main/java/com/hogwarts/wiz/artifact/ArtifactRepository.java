package com.hogwarts.wiz.artifact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yuvraj_Kalamkar
 */
@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, String> {
}
