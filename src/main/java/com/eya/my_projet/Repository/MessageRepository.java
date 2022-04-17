package com.eya.my_projet.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.eya.my_projet.models.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
