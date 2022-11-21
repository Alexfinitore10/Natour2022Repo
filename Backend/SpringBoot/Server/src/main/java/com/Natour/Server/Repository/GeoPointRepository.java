package com.Natour.Server.Repository;

import com.Natour.Server.Entity.GeoPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoPointRepository extends JpaRepository<GeoPoint,String> {
}
