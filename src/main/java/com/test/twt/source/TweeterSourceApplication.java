package com.test.twt.source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TweeterSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TweeterSourceApplication.class, args);
	}

}
