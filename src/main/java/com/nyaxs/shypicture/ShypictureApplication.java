package com.nyaxs.shypicture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication

public class ShypictureApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShypictureApplication.class, args);
	}

}
