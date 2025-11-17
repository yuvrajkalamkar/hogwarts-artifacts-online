package com.hogwarts.wiz.wizard.converter;

import com.hogwarts.wiz.wizard.Wizard;
import com.hogwarts.wiz.wizard.WizardDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuvraj_Kalamkar
 */
@Component
public class WizardToWizardDtoConverter implements Converter<Wizard, WizardDTO> {
    @Override
    public WizardDTO convert(Wizard source) {
        return new WizardDTO(source.getId(),
                source.getName(),
                source.getNumberOfArtifacts());
    }
    public List<WizardDTO> convertList(List <Wizard> sourceList) {
        List<WizardDTO> dtoList = new ArrayList<>();
        for(Wizard source : sourceList) {
            dtoList.add(new WizardDTO(source.getId(),
                    source.getName(),
                    source.getNumberOfArtifacts())
            );
        }
        return dtoList;
    }
}
