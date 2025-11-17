package com.hogwarts.wiz.wizard;

/**
 * @author Yuvraj_Kalamkar
 */
public class WizardNotFoundException extends RuntimeException{
    public WizardNotFoundException(Integer id) {
        super("Could not find wizard with id: " + id);
    }
}
