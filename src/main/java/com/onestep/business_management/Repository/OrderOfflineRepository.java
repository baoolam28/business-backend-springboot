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

        @Query("SELECT o.orderOfflineId AS orderId, " +
                        "o.store.storeId AS storeId, " +
                        "o.orderDate AS orderDate, " +
                        "o.paymentStatus AS paymentStatus, " +
                        "od.price * od.quantity AS totalPrice " +
                        "FROM OrderOffline o " +
                        "JOIN OrderOfflineDetail od ON o.orderOfflineId = od.orderOffline.orderOfflineId " +
                        "WHERE CAST(o.orderDate AS date) = CAST(CURRENT_DATE AS date)")
        List<Object[]> getOrdersWithPriceByToday(UUID storeId);

        @Query("SELECT MONTH(o.orderDate) AS month, DAY(o.orderDate) AS day, SUM(od.price * od.quantity) AS totalOrderValue "
                        + "FROM OrderOffline o "
                        + "JOIN OrderOfflineDetail od ON o.orderOfflineId = od.orderOffline.orderOfflineId "
                        + "WHERE o.store.storeId = :storeId "
                        + "AND o.paymentStatus = true "
                        + "AND MONTH(o.orderDate) = MONTH(CURRENT_DATE) " // Lọc theo tháng hiện tại
                        + "AND YEAR(o.orderDate) = YEAR(CURRENT_DATE) " // Lọc theo năm hiện tại
                        + "GROUP BY MONTH(o.orderDate), DAY(o.orderDate) "
                        + "ORDER BY day")
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

        @Query(value = "SELECT c.customer_id, c.name, o.store_id, COUNT(o.order_offline_id) AS totalOrders " +
                        "FROM orders_offline o " +
                        "JOIN customers c ON o.customer_id = c.customer_id " +
                        "WHERE o.payment_status = 1 " +
                        "AND o.store_id = :storeId " +
                        "GROUP BY c.customer_id, c.name, o.store_id " +
                        "ORDER BY totalOrders DESC " +
                        "OFFSET 1 ROWS FETCH NEXT 3 ROWS ONLY", nativeQuery = true)
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

        @Query(value = "SELECT DATEPART(HOUR, o.order_date) AS hour, " +
                        "DATEPART(MINUTE, o.order_date) AS minute, " +
                        "DATEPART(SECOND, o.order_date) AS second, " +
                        "SUM(od.price * od.quantity) AS totalOrderPrice " +
                        "FROM orders_offline o " +
                        "JOIN order_offline_details od ON o.order_offline_id = od.order_offline_id " +
                        "WHERE CAST(o.order_date AS DATE) = CAST(GETDATE() AS DATE) " +
                        "GROUP BY DATEPART(HOUR, o.order_date), DATEPART(MINUTE, o.order_date), DATEPART(SECOND, o.order_date) "
                        +
                        "ORDER BY hour ASC, minute ASC, second ASC", nativeQuery = true)
        List<Object[]> findPaidOrdersMadeTodayByStoreId(@Param("storeId") UUID storeId);

        @Query(value = "SELECT o.order_offline_id AS orderId, " +
                        "CONVERT(DATE, o.order_date) AS orderDate, " +
                        "c.name AS customerName, " +
                        "c.phone AS customerPhone, " +
                        "o.store_id AS storeId, " +
                        "SUM(od.price * od.quantity) AS totalAmount " +
                        "FROM orders_offline o " +
                        "JOIN customers c ON o.customer_id = c.customer_id " +
                        "JOIN order_offline_details od ON o.order_offline_id = od.order_offline_id " +
                        "WHERE o.payment_status = 1 " +
                        "AND o.store_id = :storeId " +
                        "GROUP BY o.order_offline_id, CONVERT(DATE, o.order_date), c.name, c.phone, o.store_id " +
                        "ORDER BY CONVERT(DATE, o.order_date) DESC", nativeQuery = true)
        List<Object[]> findAllOrdersByStoreId(@Param("storeId") UUID storeId);

        @Query("SELECT o FROM OrderOffline o JOIN FETCH o.orderDetails od WHERE o.store.storeId = :storeId AND o.paymentStatus = true")
        List<OrderOffline> findOrdersByStoreId(UUID storeId);

}
