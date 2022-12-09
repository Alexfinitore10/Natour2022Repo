package com.Natour.Server.Repository;

import com.Natour.Server.Entity.Sentieri;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SentieriRepository extends JpaRepository<Sentieri,Long> {

    List<Sentieri> findAllByutenteId(Long id);

    List<Sentieri> findAllByOrderByIdDesc();

    @Query("""  
       select a from Sentieri a where
       (:località is null or a.località = :località)
       and (:durata is null or a.durata= :durata)
       and (:difficolta is null or a.difficolta = :difficolta)
       and (:disabile is null or a.disabile = :disabile)
       order by a.id DESC
       """)
    List<Sentieri> getSentieriByQuery(@Param("località")String località, @Param("durata")Integer durata, @Param("difficolta")Integer difficolta, @Param("disabile")Boolean disabile);

}
