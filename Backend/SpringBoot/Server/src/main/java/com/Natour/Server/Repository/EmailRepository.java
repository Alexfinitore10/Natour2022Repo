package com.Natour.Server.Repository;

import com.Natour.Server.Entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email,Long> {
}
