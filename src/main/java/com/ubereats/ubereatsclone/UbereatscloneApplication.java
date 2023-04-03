package com.ubereats.ubereatsclone;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableCaching
public class UbereatscloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(UbereatscloneApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
}
