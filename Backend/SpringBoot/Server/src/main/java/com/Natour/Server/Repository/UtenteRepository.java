package com.Natour.Server.Repository;

import com.Natour.Server.Entity.Sentieri;
import com.Natour.Server.Entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Utente findAllByUsername(String username);

}
