package com.eya.my_projet.controlles;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.eya.my_projet.Repository.MessageRepository;
import com.eya.my_projet.Repository.ThreadRepository;
import com.eya.my_projet.Repository.UserRepository;
import com.eya.my_projet.models.User;
import com.eya.my_projet.models.Message;
import com.eya.my_projet.models.Thread;
import com.eya.my_projet.payload.request.MessageRequest;
import com.eya.my_projet.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class MessageController {

	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ThreadRepository threadRepo;
	
	@Autowired
	MessageRepository messageRepo;
	
	@PostMapping("/message/create")
	public Properties create(@RequestBody MessageRequest request, Authentication auth) {
		Properties response = new Properties();
		UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
		
		User user = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
		User reciever = this.userRepo.findById(request.getReciever()).get();
		String contenu = request.getContenu();
		Thread thread = this.threadRepo.findById(request.getThread()).get();
		
		
		Message message = new Message(contenu);
		message.setSender(user);
		message.setReciever(reciever);
		message.setThread(thread);
		
		this.messageRepo.save(message);
	
		response.put("message", "message envoyé avec succées ");
		
		return response;
	}

	@GetMapping("/message/affiche")
	public List<Message> index(@RequestBody Properties request) {
		long id = (long)request.get("thread");
	
		Thread thread = this.threadRepo.findById(id).get();
		
		return thread.getMessages();
	}
}
