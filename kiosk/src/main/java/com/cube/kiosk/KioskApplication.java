package com.cube.kiosk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cube"})
@EntityScan(basePackages = {"com.cube"})
@EnableCaching
public class KioskApplication {

	public static void main(String[] args) {
		SpringApplication.run(KioskApplication.class, args);
	}

}
