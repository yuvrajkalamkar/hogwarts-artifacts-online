package com.hogwarts.wiz.artifact;

import com.hogwarts.wiz.artifact.converter.ArtifactDtoToArtifactConverter;
import com.hogwarts.wiz.artifact.converter.ArtifactToArtifactDtoConverter;
import com.hogwarts.wiz.system.Result;
import com.hogwarts.wiz.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yuvraj_Kalamkar
 */
@RestController
@RequestMapping("/api/v1/artifact")
public class ArtifactController {
    private final ArtifactService artifactService;
    private final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;
    private final ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter;

    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter, ArtifactDtoToArtifactConverter artifactDtoToArtifactConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
        this.artifactDtoToArtifactConverter = artifactDtoToArtifactConverter;
    }

    @GetMapping("/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId){
        Artifact foundArtifact = this.artifactService.findById(artifactId);
        ArtifactDTO artifactDTO = this.artifactToArtifactDtoConverter.convert(foundArtifact);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", artifactDTO);
    }
    @GetMapping
    public Result findAllArtifacts(){
        List<Artifact> foundArtifacts = this.artifactService.findAll();
        return new Result(true, StatusCode.SUCCESS, "Find All Success", this.artifactToArtifactDtoConverter.convertList(foundArtifacts));
    }
    @PostMapping
    public Result addArtifact(@Valid @RequestBody ArtifactDTO artifactDTO){
        Artifact artifact = this.artifactDtoToArtifactConverter.convert(artifactDTO);
        Artifact savedArtifact = this.artifactService.saveNewArtifact(artifact);
        ArtifactDTO newArtifactDTO = this.artifactToArtifactDtoConverter.convert(savedArtifact);
        return new Result(true, StatusCode.SUCCESS, "Add Success", newArtifactDTO);
    }
    @PutMapping("/{artifactId}")
    public Result updateArtifact(@Valid @RequestBody ArtifactDTO artifactDTO, @PathVariable String artifactId){
        Artifact artifact = this.artifactDtoToArtifactConverter.convert(artifactDTO);
        Artifact updatedArtifact = this.artifactService.updateArtifact(artifactId, artifact);
        ArtifactDTO artifactDTO1 = this.artifactToArtifactDtoConverter.convert(updatedArtifact);
        return new Result(true, StatusCode.SUCCESS, "Update Success", artifactDTO1);
    }
    @DeleteMapping("/{artifactId}")
    public Result deleteById(@PathVariable String artifactId){
        this.artifactService.delete(artifactId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success: " + artifactId);
    }
}
