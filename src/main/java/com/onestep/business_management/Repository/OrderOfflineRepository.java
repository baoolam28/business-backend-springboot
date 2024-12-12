package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.OrderOffline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderOfflineRepository extends JpaRepository<OrderOffline, UUID> {

        @Query("SELECT SUM(od.price * od.quantity) AS totalOrderValue, " +
                "o.orderDate AS orderDate " +
                "FROM OrderOffline o " +
                "JOIN o.orderDetails od " +
                "WHERE o.store.storeId = :storeId " +
                "AND o.orderDate BETWEEN :startDate AND :endDate " +
                "AND o.paymentStatus = true " +
                "GROUP BY o.orderDate")
        List<Object[]> getTotalOrderValueByDate(@Param("storeId") UUID storeId,
                                                @Param("startDate") Date startDate,
                                                @Param("endDate") Date endDate);

        @Query(value = "SELECT YEAR(o.order_date) AS year, DATEPART(WEEK, o.order_date) AS week, COUNT(DISTINCT o.customer_id) AS customer_count "
                +
                "FROM orders o " +
                "WHERE o.order_date BETWEEN :startDate AND :endDate " +
                "GROUP BY YEAR(o.order_date), DATEPART(WEEK, o.order_date) " +
                "ORDER BY year, week", nativeQuery = true)
        List<Object[]> countCustomerOrderByWeek(@Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);

        @Query("SELECT SUM(od.price * od.quantity) AS totalOrderValue "
                + "FROM OrderOffline o "
                + "JOIN OrderOfflineDetail od ON o.orderOfflineId = od.orderOffline.orderOfflineId "
                + "WHERE o.store.storeId = :storeId "
                + "AND o.paymentStatus = true "
                + "AND CAST(o.orderDate AS DATE) = CAST(CURRENT_DATE AS DATE)") // Sử dụng CAST để so sánh chỉ
                // ngày
        List<Object[]> getTotalOrderValueByToday(@Param("storeId") UUID storeId);

        @Query("SELECT MONTH(o.orderDate) AS month, SUM(od.price * od.quantity) AS totalOrderValue "
                + "FROM OrderOffline o "
                + "JOIN OrderOfflineDetail od ON o.orderOfflineId = od.orderOffline.orderOfflineId "
                + "WHERE o.store.storeId = :storeId "
                + "AND o.paymentStatus = true "
                + "AND YEAR(o.orderDate) = YEAR(CURRENT_DATE) " // Lọc theo năm hiện tại
                + "GROUP BY MONTH(o.orderDate) "
                + "ORDER BY month")
        List<Object[]> getTotalOrderValueByMonth(@Param("storeId") UUID storeId);

        @Query("SELECT YEAR(o.orderDate) AS year, MONTH(o.orderDate) AS month, SUM(od.price * od.quantity) AS totalOrderValue "
                + "FROM OrderOffline o "
                + "JOIN OrderOfflineDetail od ON o.orderOfflineId = od.orderOffline.orderOfflineId "
                + "WHERE o.store.storeId = :storeId "
                + "AND o.paymentStatus = true "
                + "AND YEAR(o.orderDate) = YEAR(CURRENT_DATE) " // Lọc theo năm hiện tại
                + "GROUP BY YEAR(o.orderDate), MONTH(o.orderDate) "
                + "ORDER BY year, month")
        List<Object[]> getTotalOrderValueByYear(@Param("storeId") UUID storeId);

        // Lấy tổng giá trị của tất cả đơn hàng
        @Query("SELECT SUM(od.price * od.quantity) FROM OrderOffline o " +
                "JOIN OrderOfflineDetail od ON o.orderOfflineId = od.orderOffline.orderOfflineId " +
                "WHERE o.store.storeId = :storeId " +
                "AND o.paymentStatus = true")
        Double getTotalOrderValue(@Param("storeId") UUID storeId);

        @Query(value = "SELECT * FROM orders_offline o WHERE o.store_id = :storeId", nativeQuery = true)
        List<OrderOffline> findBystore(@Param("storeId") UUID storeId);

        @Query(value = "SELECT TOP 3 c.customer_id, c.name, o.store_id, COUNT(o.order_offline_id) AS totalOrders " +
                "FROM orders_offline o " +
                "JOIN customers c ON o.customer_id = c.customer_id " +
                "WHERE o.payment_status = 1 " +
                "AND o.store_id = :storeId " +
                "GROUP BY c.customer_id, c.name, o.store_id " +
                "ORDER BY totalOrders DESC", nativeQuery = true)
        List<Object[]> findTop3CustomersWithMostOrdersByStore(@Param("storeId") UUID storeId);

        @Query(value = "SELECT TOP 5 " +
                "p.product_id, " +
                "p.product_name, " +
                "SUM(od.quantity) AS totalSold " +
                "FROM order_offline_details od " +
                "JOIN orders_offline o ON od.order_offline_id = o.order_offline_id " +
                "JOIN products p ON od.product_id = p.product_id " +
                "WHERE o.payment_status = 1 " +
                "AND o.store_id = :storeId " +
                "GROUP BY p.product_id, p.product_name " +
                "ORDER BY totalSold DESC", nativeQuery = true)
        List<Object[]> findTop3MostSoldProducts(@Param("storeId") UUID storeId);

        @Query("SELECT SUM(od.price * od.quantity) AS totalOrderValue "
                + "FROM OrderOffline o "
                + "JOIN OrderOfflineDetail od ON o.orderOfflineId = od.orderOffline.orderOfflineId "
                + "WHERE o.store.storeId = :storeId "
                + "AND o.paymentStatus = true "
                + "AND CAST(o.orderDate AS DATE) = CAST(CURRENT_DATE AS DATE)")
        List<Object[]> findPaidOrdersMadeTodayByStoreId(UUID storeId);
}