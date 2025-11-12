package com.hogwarts.wiz.artifact;

/**
 * @author Yuvraj_Kalamkar
 */
public class ArtifactNotFoundException extends RuntimeException {
    public ArtifactNotFoundException(String id) {
        super("Could not find artifact with id: " + id);
    }
}
