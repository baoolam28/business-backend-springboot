package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.Supplier;
import com.onestep.business_management.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Optional<Supplier> findBySupplierName(String supplierName);

    @Query("SELECT s FROM Supplier s WHERE " +
            "s.supplierName LIKE %:keyword% OR " +
            "s.email LIKE %:keyword% OR " +
            "s.phone LIKE %:keyword% OR " +
            "s.fax LIKE %:keyword% OR " +
            "s.address LIKE %:keyword%")
    List<Supplier> searchByKeyword(@Param("keyword") String keyword);

    @Query("SELECT s FROM Supplier s WHERE s.email LIKE %:email%")
    List<Supplier> findByEmail(@Param("email") String email);

    @Query("SELECT s FROM Supplier s WHERE s.phone LIKE %:phone%")
    List<Supplier> findByPhone(@Param("phone") String phone);

    @Query("SELECT s FROM Supplier s WHERE s.fax LIKE %:fax%")
    List<Supplier> findByFax(@Param("fax") String fax);

    @Query("SELECT s FROM Supplier s WHERE s.address LIKE %:address%")
    List<Supplier> findByAddress(@Param("address") String address);
}
