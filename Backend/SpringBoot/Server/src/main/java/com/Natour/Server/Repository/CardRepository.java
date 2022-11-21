package com.Natour.Server.Repository;

import com.Natour.Server.Entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CardRepository extends JpaRepository<Card,String> {
}
