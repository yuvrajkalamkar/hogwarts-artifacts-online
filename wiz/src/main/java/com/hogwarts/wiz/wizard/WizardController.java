package com.hogwarts.wiz.wizard;

import com.hogwarts.wiz.system.Result;
import com.hogwarts.wiz.system.StatusCode;
import com.hogwarts.wiz.wizard.converter.WizardDtoToWizardConverter;
import com.hogwarts.wiz.wizard.converter.WizardToWizardDtoConverter;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yuvraj_Kalamkar
 */
@RestController
@RequestMapping("/api/v1/wizard")
public class WizardController {

    private final WizardService wizardService;
    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;
    private final WizardDtoToWizardConverter wizardDtoToWizardConverter;

    public WizardController(WizardService wizardService, WizardToWizardDtoConverter wizardToWizardDtoConverter, WizardDtoToWizardConverter wizardDtoToWizardConverter) {
        this.wizardService = wizardService;
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
        this.wizardDtoToWizardConverter = wizardDtoToWizardConverter;
    }

    @GetMapping("/{wizardId}")
    public Result findWizById(@PathVariable Integer wizardId) {
        Wizard wizard = this.wizardService.findWizardById(wizardId);
        WizardDTO wizardDTO = this.wizardToWizardDtoConverter.convert(wizard);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", wizardDTO);
    }

    @GetMapping
    public Result findAllWizards() {
        List<Wizard> wizards = this.wizardService.findAllWizards();
        List<WizardDTO> wizardDtoList = this.wizardToWizardDtoConverter.convertList(wizards);
        return new Result(true, StatusCode.SUCCESS, "Find All Success", wizardDtoList);
    }

    @PostMapping
    public Result saveWizard(@Valid @RequestBody WizardDTO wizardDto) {
        Wizard newWizard = this.wizardDtoToWizardConverter.convert(wizardDto);
        Wizard savedWizard = this.wizardService.addWizard(newWizard);
        WizardDTO savedWizardDto = this.wizardToWizardDtoConverter.convert(savedWizard);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedWizardDto);
    }

    @PutMapping("/{wizardId}")
    public Result updateWizard(@Valid @RequestBody WizardDTO wizardDTO, @PathVariable Integer wizardId) {
        Wizard wizard = this.wizardDtoToWizardConverter.convert(wizardDTO);
        Wizard updatedWizard = this.wizardService.updateWizard(wizard,wizardId);
        WizardDTO updatedWizardDto = this.wizardToWizardDtoConverter.convert(updatedWizard);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedWizardDto);
    }

    @DeleteMapping("/{wizardId}")
    public Result deleteWizard(@PathVariable Integer wizardId){
        this.wizardService.deleteWizard(wizardId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

    @PutMapping("/{wizardId}/artifact/{artifactId}")
    public Result updateWizard(@PathVariable Integer wizardId, @PathVariable String artifactId) {
        this.wizardService.addArtifactToWizard(wizardId, artifactId);
        return new Result(true, StatusCode.SUCCESS, "Artifact Assignment Success");
    }
}
