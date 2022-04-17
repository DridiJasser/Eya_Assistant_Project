package com.eya.my_projet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eya.my_projet.models.Thread;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {

}
