package com.Natour.Server.Repository;

import com.Natour.Server.Entity.Opinioni;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OpinioniRepository extends JpaRepository<Opinioni, Long> {


    List<Opinioni> findAllListBysentieriProprietarioId(Long id);


}
