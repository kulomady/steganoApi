package com.kulomady.mystegano;

import com.kulomady.mystegano.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableConfigurationProperties({
        FileStorageProperties.class
})

public class MysteganoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysteganoApplication.class, args);
	}
}
