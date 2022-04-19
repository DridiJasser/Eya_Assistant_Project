package com.eya.my_projet.controlles;


import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.ReadableDuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.eya.my_projet.Repository.ClientRepository;
import com.eya.my_projet.Repository.ComptableRepository;
import com.eya.my_projet.Repository.DemandeRepository;
import com.eya.my_projet.Repository.UserRepository;
import com.eya.my_projet.models.Client;
import com.eya.my_projet.models.Comptable;
import com.eya.my_projet.models.Demande;
import com.eya.my_projet.models.User;
import com.eya.my_projet.payload.request.AcceptRequest;
import com.eya.my_projet.payload.request.DemandeRequest;
import com.eya.my_projet.payload.request.RefuseRequest;
import com.eya.my_projet.response.MessageResponse;
import com.eya.my_projet.security.services.UserDetailsImpl;
import com.eya.my_projet.security.services.UserDetailsServiceImpl;







@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class DemandeController  {
	
	@Autowired
	ClientRepository clientRepo;
	@Autowired
	ComptableRepository comptableRepo;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	DemandeRepository demandeRepo;
	
	@PostMapping("/save")
	public ResponseEntity<?> registerDemande(@Valid @RequestBody DemandeRequest demandeRequest, Authentication authentication) {
		
		Demande demande = new Demande(demandeRequest.getDuree());
		
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		User user = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
		
		Client client = user.getClient();
		Comptable comptable = comptableRepo.findById(demandeRequest.getIdComp()).get();
		
		
		demande.setClient(client);
		demande.setComptable(comptable);
		
		
		DateTime date = DateTime.now();
		
		demande.setDateD(date.toString());
		
		demandeRepo.save(demande);
		return ResponseEntity.ok(new MessageResponse("demande saved successfully!"));
	}
	
	@GetMapping("/demendes")
	public ResponseEntity<List<Demande>> getAlldemande() {

		try {
			List<Demande> demande1 = demandeRepo.findAll();

			if (demande1.isEmpty()) {

				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Demande>>(demande1,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/demande/comptable")	
	public ResponseEntity<List<Demande>> comptables(@RequestBody DemandeRequest request, Authentication auth) throws Exception {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
		User user = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
		
		if(!user.hasRole("ROLE_Comptable")) {
			throw new Exception("accées non autorisé");
		}
		
		List<Demande> demandes = this.demandeRepo.findAllByComptable(
				this.comptableRepo.findById(request.getIdComp()).get()
				).get();
		return new ResponseEntity<List<Demande>>(demandes, HttpStatus.OK);
	}
	
	@GetMapping("/demande/client")	
	public ResponseEntity<List<Demande>> clients(@RequestBody DemandeRequest request, Authentication auth) throws Exception {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
		User user = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
		
		if(!user.hasRole("ROLE_Client")) {
			throw new Exception("accées non autorisé");
		}
		
		List<Demande> demandes = this.demandeRepo.findAllByClient(this.clientRepo.findById(request.getIdC()).get()).get();
		return new ResponseEntity<List<Demande>>(demandes, HttpStatus.OK);
	}
	
	
	@PutMapping("/demande/comptable/accept")
	public ResponseEntity<Properties> accept(@RequestBody AcceptRequest request, Authentication auth) throws Exception {
		
		UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
		User user = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
		
		if(!user.hasRole("ROLE_Comptable")) {
			throw new Exception("accées non autorisé");
		}
		
		
		Properties response = new Properties();
		Demande demande = this.demandeRepo.findById(request.getIdDemande()).get();
		demande.setEtat(1);
		DateTime date = new DateTime(demande.getDateAC());
		String dateAccep = date.plusYears(Integer.parseInt(demande.getDuree())).toString();
		demande.setDateAC(dateAccep);
		this.demandeRepo.save(demande);
		response.put("message", "demande accepté avec succées");
		return new ResponseEntity<Properties>(response, HttpStatus.OK);
	}
	
	@PutMapping("/demande/comptable/refuse")
	public ResponseEntity<Properties> refuse(@RequestBody RefuseRequest request,Authentication auth) throws Exception{
		
		UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
		User user = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
		
		if(!user.hasRole("ROLE_Comptable")) {
			throw new Exception("accées non autorisé");
		}
		
		
		Properties response = new Properties();
		Demande demande = this.demandeRepo.findById(request.getIdDemande()).get();
		demande.setEtat(0);
		String dateRefuse = DateTime.now().toString();
		demande.setDateAC(dateRefuse);
		this.demandeRepo.save(demande);
		
		response.put("message", "demande refusée avec succées");
		return new ResponseEntity<Properties>(response, HttpStatus.OK);
	}
	
	
}
	
	
		
		
		
		
		
		
		
		
	
	

