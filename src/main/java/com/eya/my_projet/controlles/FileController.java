package com.eya.my_projet.controlles;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eya.my_projet.Repository.DemandeRepository;
import com.eya.my_projet.Repository.FileDBRepository;
import com.eya.my_projet.Repository.UserRepository;
import com.eya.my_projet.models.Demande;
import com.eya.my_projet.models.FileDB;
import com.eya.my_projet.models.User;
import com.eya.my_projet.response.MessageResponse;
import com.eya.my_projet.response.ResponseFile;
import com.eya.my_projet.security.services.UserDetailsImpl;
import com.eya.my_projet.services.FileStorageService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
public class FileController {

	@Autowired
	private FileStorageService storageService;

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private FileDBRepository fileRepo;
	
	@Autowired
	private DemandeRepository demandeRepo;
	
	@PostMapping("/upload")
	public ResponseEntity<MessageResponse> uploadFile(Authentication auth, @RequestParam("file") MultipartFile file, @RequestParam("demande") long idDemande, @RequestParam("recipient") Long recipentId, @RequestParam("type") String type) throws Exception {
		Demande demande = this.demandeRepo.findById(idDemande).get();
		
		if(demande.getEtat() != 1) {
			throw new Exception("action non autorisée");
		}
		
		String message = "fichier envoyé avec succées";
		UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
		
		User sender = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
		User recipient = this.userRepo.findById(recipentId).get();
		
		storageService.store(file, sender, recipient, type, demande);
		
		return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(message));
	}

	@GetMapping("/files")
	public ResponseEntity<List<ResponseFile>> getListFiles() {
		List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(dbFile.getId()).toUriString();
			return new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length );
		}).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/files/{id}")
	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
		FileDB fileDB = storageService.getFile(id);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
				.body(fileDB.getData());
	}
	
	@PostMapping("/file/comptable/markerEnCours")
	public ResponseEntity<Properties> markerEnCours(@RequestBody Properties request, Authentication auth) throws Exception {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
		User user = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
		
		if(!user.hasRole("ROLE_Comptable")) {
			throw new Exception("accées non autorisé");
		}
		
		Properties response = new Properties();
		
		String id = (String)request.get("file");
		FileDB file = this.fileRepo.findById(id).get();
		
		file.setStatus(1);
		
		this.fileRepo.save(file);
		
		response.put("message", "demande marker en cours avec succées");
		
		return new ResponseEntity<Properties>(response, HttpStatus.OK);
	}
	
	
	@PostMapping("/file/comptable/markerTraite")
	public ResponseEntity<Properties> markerTraite(@RequestBody Properties request, Authentication auth) throws Exception {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
		User user = this.userRepo.findByUsername(userPrincipal.getUsername()).get();
		
		if(!user.hasRole("ROLE_Comptable")) {
			throw new Exception("accées non autorisé");
		}
		
		Properties response = new Properties();
		
		String id = (String)request.get("demande");
		FileDB file = this.fileRepo.findById(id).get();
		file.setStatus(2);
		
		response.put("message", "demande traiter avec succées");
		
		return new ResponseEntity<Properties>(response, HttpStatus.OK);
	}
}
