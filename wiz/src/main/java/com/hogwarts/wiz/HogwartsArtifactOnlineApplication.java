package com.hogwarts.wiz;

import com.hogwarts.wiz.artifact.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HogwartsArtifactOnlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(HogwartsArtifactOnlineApplication.class, args);
	}

    @Bean
    public IdWorker getIdWorker(){
        return new IdWorker(1,1);
    }
}
