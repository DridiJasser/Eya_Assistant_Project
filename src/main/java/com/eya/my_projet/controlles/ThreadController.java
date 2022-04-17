package com.eya.my_projet.controlles;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eya.my_projet.Repository.ThreadRepository;
import com.eya.my_projet.Repository.UserRepository;
import com.eya.my_projet.payload.request.ThreadRequest;
import com.eya.my_projet.security.services.UserDetailsImpl;
import com.eya.my_projet.models.Thread;
import com.eya.my_projet.models.User;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ThreadController {

	@Autowired
	ThreadRepository threadRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@PostMapping("/thread/create")
	public Properties create(Authentication auth, @RequestBody ThreadRequest request) {
		Properties response = new Properties();
		UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
		User user = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
		
		Thread thread = new Thread();
		
		thread.setSujet(request.getSujet());
		thread.setUser(user);
		
		this.threadRepo.save(thread);
		
		response.put("message", "thread creer avec succées");
		return response;
	}
	
	
	@GetMapping("/thread/index")
	public List<Thread> index(Authentication auth) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
		User user = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
		
		return user.getThreads();
	}
	
}
