package com.hogwarts.wiz.wizard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yuvraj_Kalamkar
 */
@Repository
public interface WizardRepository extends JpaRepository<Wizard, Integer> {
}
