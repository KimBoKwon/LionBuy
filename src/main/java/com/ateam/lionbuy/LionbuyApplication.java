package com.ateam.lionbuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LionbuyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LionbuyApplication.class, args);
	}

}
