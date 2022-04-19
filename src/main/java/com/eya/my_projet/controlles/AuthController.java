package com.eya.my_projet.controlles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eya.my_projet.Repository.RoleRepository;
import com.eya.my_projet.Repository.UserRepository;
import com.eya.my_projet.models.Client;
import com.eya.my_projet.models.Comptable;
import com.eya.my_projet.models.ERole;
import com.eya.my_projet.models.Role;
import com.eya.my_projet.models.User;
import com.eya.my_projet.payload.request.LoginRequest;
import com.eya.my_projet.payload.request.SignupRequest;
import com.eya.my_projet.response.JwtResponse;
import com.eya.my_projet.response.MessageResponse;
import com.eya.my_projet.security.jwt.JwtUtils;
import com.eya.my_projet.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println("signin : ");
		Authentication authentication = authenticationManager.authenticate(
		new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup_comp")
	public ResponseEntity<?> registerComptable(@Valid @RequestBody SignupRequest signUpRequest) {
		System.out.println("signup");
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		Comptable comptable = new Comptable(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getAdresse(),
				signUpRequest.getNumero_telephone(), signUpRequest.getEcole_sperieur(),
				encoder.encode(signUpRequest.getPassword()));
	
		Set<Role> roles = new HashSet<>();
		
			Role comptableRole = roleRepository.findByName(ERole.ROLE_Comptable)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(comptableRole);
			comptable.setRoles(roles);
			userRepository.save(comptable);
			return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}		
		
	
	
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerClient(@Valid @RequestBody SignupRequest signUpRequest) {
		System.out.println("signup");
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}
		// Create new user's account
		Client client = new Client(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getNom_entreprise(),
				
				encoder.encode(signUpRequest.getPassword()));
	
		Set<Role> roles = new HashSet<>();
		
			Role clientRole = roleRepository.findByName(ERole.ROLE_Client)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(clientRole);
			client.setRoles(roles);
			userRepository.save(client);
			return ResponseEntity.ok(new MessageResponse("client registered successfully!"));
	}		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}