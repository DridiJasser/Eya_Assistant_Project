package com.eya.my_projet.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eya.my_projet.models.Client;
import com.eya.my_projet.models.Comptable;
import com.eya.my_projet.models.Demande;








public interface DemandeRepository extends JpaRepository <Demande,Long> {
		
	
	public Optional<List<Demande>> findAllByComptable(Comptable comptable);
	
	public Optional<List<Demande>> findAllByClient(Client client);

}
