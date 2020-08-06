package com.mac.trader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/***
 * 
 * @author Mac
 *
 * Spring Application Class
 */
@SpringBootApplication
@EnableScheduling
public class TraderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraderServiceApplication.class, args);
	}

}
