package com.eya.my_projet.controlles;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.eya.my_projet.Repository.ComptableRepository;
import com.eya.my_projet.Repository.UserRepository;
import com.eya.my_projet.models.Comptable;
import com.eya.my_projet.models.FileDB;
import com.eya.my_projet.models.User;
import com.eya.my_projet.security.services.UserDetailsImpl;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	
	@Autowired 
	ComptableRepository comptableRepository;

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

	
	
	@GetMapping("/comptable")
	public ResponseEntity<List<Comptable>> getAllcomptable() {

		try {
			List<Comptable> comp1 = comptableRepository.findAll();

			if (comp1.isEmpty()) {

				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Comptable>>(comp1,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getusersRoles(@PathVariable("id") long id) {
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

	
	@GetMapping("/user/file/recieve")
	// recuperer la liste de documens qui ont été envoyer a cet utilisateur
	public Set<FileDB> recievedFiles(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		User user = this.userRepository.findByUsername(userPrincipal.getUsername()).get();
		
		return user.getRecievedFiles();
	}
	
	
	@GetMapping("/user/file/archive")
	// historique comptable
	public Set<FileDB> sendedFiles(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		User user = this.userRepository.findByUsername(userPrincipal.getUsername()).get();
		
		return user.getSendedFiles();
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	


