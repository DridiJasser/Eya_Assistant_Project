package com.eya.my_projet.controlles;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eya.my_projet.Repository.DemandeRepository;
import com.eya.my_projet.Repository.UserRepository;
import com.eya.my_projet.models.Comptable;
import com.eya.my_projet.models.User;
import com.eya.my_projet.models.Client;
import com.eya.my_projet.security.services.UserDetailsImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/client")
public class ClientController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private DemandeRepository demandeRepo;
	
	@GetMapping("/comptable")
	// recuperer la liste des comptables associe a cet client
	public List<Comptable> comptables(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		User user = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
	
		Client client = user.getClient();
		
		List<Comptable> comptables = this.demandeRepo.findAllByClient(client).get()
				.stream()
				.filter(demande -> demande.getEtat() == 1)
				.map(demande -> demande.getComptable())
				.collect(Collectors.toList());
	
		return comptables;
	}
}
