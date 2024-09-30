package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.OrderOffline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Repository
public interface OrderOfflineRepository extends JpaRepository<OrderOffline, Integer> {

    @Query(value = "SELECT YEAR(o.order_date) AS year, DATEPART(WEEK, o.order_date) AS week, COUNT(DISTINCT o.customer_id) AS customer_count " +
            "FROM orders o " +
            "WHERE o.order_date BETWEEN :startDate AND :endDate " +
            "GROUP BY YEAR(o.order_date), DATEPART(WEEK, o.order_date) " +
            "ORDER BY year, week", nativeQuery = true)
    List<Object[]> countCustomerOrderByWeek(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT YEAR(o.order_date) AS year, MONTH(o.order_date) AS month, COUNT(DISTINCT o.customer_id) AS customer_count " +
            "FROM orders o " +
            "WHERE o.order_date BETWEEN :startDate AND :endDate " +
            "GROUP BY YEAR(o.order_date), MONTH(o.order_date) " +
            "ORDER BY year, month", nativeQuery = true)
    List<Object[]> countCustomerOrderByMonth(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "SELECT YEAR(o.order_date) AS year, COUNT(DISTINCT o.customer_id) AS customer_count " +
            "FROM orders o " +
            "WHERE o.order_date BETWEEN :startDate AND :endDate " +
            "GROUP BY YEAR(o.order_date) " +
            "ORDER BY year", nativeQuery = true)
    List<Object[]> countCustomerOrderByYear(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
