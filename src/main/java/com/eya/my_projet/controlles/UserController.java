package com.eya.my_projet.controlles;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.eya.my_projet.Repository.UserRepository;
import com.eya.my_projet.models.User;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	UserRepository userRepository;

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAlluseres() {

		try {
			List<User> users1 = userRepository.findAll();

			if (users1.isEmpty()) {

				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<User>>(users1,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getusersById(@PathVariable("id") long id) {
		Optional<User> UserlData = userRepository.findById(id);
		if (UserlData.isPresent()) {
			return new ResponseEntity<>(UserlData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
		Optional<User> UserData = userRepository.findById(id);
		if (UserData.isPresent()) {
			User _User = UserData.get();
			_User.setUsername(user.getUsername());
			_User.setEmail(user.getEmail());

			return new ResponseEntity<>(userRepository.save(_User), HttpStatus.OK);
		}

		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	
	
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> deleteusers(@PathVariable("id") Long id) {
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) { 
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	


