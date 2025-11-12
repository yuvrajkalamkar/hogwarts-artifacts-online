package com.hogwarts.wiz.artifact;

import com.hogwarts.wiz.wizard.WizardDTO;
import jakarta.validation.constraints.NotEmpty;

/**
 * @author Yuvraj_Kalamkar
 */
public record ArtifactDTO(String id,
                          @NotEmpty(message = "Name is required") String name,
                          @NotEmpty(message = "Description is required") String description,
                          @NotEmpty(message = "ImageUrl is required") String imageUrl,
                          WizardDTO owner) {
}
