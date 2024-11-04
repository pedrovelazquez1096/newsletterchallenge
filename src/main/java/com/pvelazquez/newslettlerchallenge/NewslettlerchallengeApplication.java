package com.pvelazquez.newslettlerchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewslettlerchallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewslettlerchallengeApplication.class, args);
	}

}
