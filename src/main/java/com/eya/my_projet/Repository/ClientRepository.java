package com.eya.my_projet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.eya.my_projet.models.Client;
import com.eya.my_projet.models.User;




public interface ClientRepository  extends JpaRepository<Client,Long >{

}
