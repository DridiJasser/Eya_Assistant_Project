package com.eya.my_projet.services;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.eya.my_projet.Repository.FileDBRepository;
import com.eya.my_projet.Repository.UserRepository;
import com.eya.my_projet.models.FileDB; 
import com.eya.my_projet.models.User;


@Service
public class FileStorageService {
	 @Autowired
	  private FileDBRepository fileDBRepository;
	  
	 @Autowired
	 private UserRepository userRepository;
	 
	 
	 public FileDB store(MultipartFile file, User sender, User recipient, String type) throws IOException {
	    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
	    FileDB fileDB = new FileDB(fileName, file.getContentType(), file.getBytes());
	    
	    fileDB.setRecipent(recipient);
	    fileDB.setSender(sender);
	    fileDB.setType(type);
	    
	    return fileDBRepository.save(fileDB);
	  }
	 
	 public FileDB getFile(String id) {
	    return fileDBRepository.findById(id).get();
	  }
	  
	  public Stream<FileDB> getAllFiles() {
	    return fileDBRepository.findAll().stream();
	  }
	}


