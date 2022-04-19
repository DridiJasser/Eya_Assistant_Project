 package com.eya.my_projet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.eya.my_projet.Repository.UserRepository;
import com.eya.my_projet.models.User;

@SpringBootApplication
public class MyProjet2Application {

	@Autowired
	UserRepository userRepo;
	
	public static void main(String[] args) {
		
		SpringApplication.run(MyProjet2Application.class, args);
	}
	
	 @Bean
	 CommandLineRunner commandLineRunner(){
	        return args -> {
	        	if(!userRepo.findByUsername("adminn").isPresent()) {
	        		User admin = new User();
		    		admin.setEmail("admin@example.com");
		    		admin.setUsername("adminn");
		    		admin.setPassword(new BCryptPasswordEncoder().encode("123456789"));
		    		this.userRepo.save(admin);
	        	}
	        };
	        
	 }

}
