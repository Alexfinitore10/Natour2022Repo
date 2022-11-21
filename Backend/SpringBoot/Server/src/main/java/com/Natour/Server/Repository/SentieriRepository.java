package com.Natour.Server.Repository;

import com.Natour.Server.Entity.Sentieri;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SentieriRepository extends JpaRepository<Sentieri,Long> {

    //@Query()
    List<Sentieri> findAllByutenteProprietarioId(Long id);

    List<Sentieri> findAllByOrderByIdDesc();

    List<Sentieri> findAllByLocalitàAndDurataAndDifficoltaAndDisabile(String località, Integer durata, Integer difficolta, Boolean disabile);


    @Query("""  
       select a from Sentieri a where
       (:località is null or a.località = :località)
       and (:durata is null or a.durata= :durata)
       and (:difficolta is null or a.difficolta = :difficolta)
       and (:disabile is null or a.disabile = :disabile)
       order by a.id DESC
       """)
    List<Sentieri> getSentieriByQuery(@Param("località")String località, @Param("durata")Integer durata, @Param("difficolta")Integer difficolta, @Param("disabile")Boolean disabile);

    /*

    @Query("""
       select a from Sentieri a where
       (?1 is null or a.località = ?1)
       and (?2 is null or a.durata= ?2)
       and (?3 is null or a.difficolta = ?3)
       and (?4 is null or a.disabile = ?4)
       """)
        and (:disabile null or a.disabile = :disabile)
     */
}
