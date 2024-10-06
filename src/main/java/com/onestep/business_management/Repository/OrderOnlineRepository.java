package com.onestep.business_management.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onestep.business_management.Entity.OrderOnline;

@Repository
public interface OrderOnlineRepository extends JpaRepository<OrderOnline, UUID> {

    @Query("SELECT o FROM OrderOnline o WHERE o.user.userId = :userId")
    List<OrderOnline> findByUser(@Param("userId") UUID userId);
}
