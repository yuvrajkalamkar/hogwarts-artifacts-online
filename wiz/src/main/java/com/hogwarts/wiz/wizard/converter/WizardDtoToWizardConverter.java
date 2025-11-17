package com.hogwarts.wiz.wizard.converter;

import com.hogwarts.wiz.wizard.Wizard;
import com.hogwarts.wiz.wizard.WizardDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author Yuvraj_Kalamkar
 */
@Component
public class WizardDtoToWizardConverter implements Converter<WizardDTO, Wizard> {
    @Override
    public Wizard convert(WizardDTO source) {
        Wizard wizard = new Wizard();
        wizard.setName(source.name());
        return wizard;
    }
}
