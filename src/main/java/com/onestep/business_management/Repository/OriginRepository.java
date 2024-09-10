package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.Origin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OriginRepository extends JpaRepository<Origin, Integer> {
}
