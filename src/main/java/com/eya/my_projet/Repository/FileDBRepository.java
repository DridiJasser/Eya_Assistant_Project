package com.eya.my_projet.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eya.my_projet.models.FileDB;
import com.eya.my_projet.models.User;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

	
	
     }