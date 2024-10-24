package com.onestep.business_management.Repository;

import com.onestep.business_management.Entity.OrderOffline;
import com.onestep.business_management.Entity.OrderOfflineDetail;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderOfflineDetailRepository extends JpaRepository<OrderOfflineDetail,UUID> {

    @Query(value = "SELECT od FROM OrderOfflineDetail od WHERE od.orderOffline.orderOfflineId = :orderId")
    List<OrderOfflineDetail> findProductsByOrderId(@Param("orderId") UUID orderId);

     // Thêm hàm để xóa tất cả chi tiết đơn hàng của một đơn hàng
    void deleteByOrderOffline(OrderOffline orderOffline);
}
