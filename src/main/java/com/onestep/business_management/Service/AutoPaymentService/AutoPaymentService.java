package com.onestep.business_management.Service.AutoPaymentService;

import com.onestep.business_management.DTO.PaymentDTO.VNPayRequest;
import com.onestep.business_management.Entity.OrderOffline;
import com.onestep.business_management.Entity.OrderOnline;
import com.onestep.business_management.Entity.OrderOnlineDetail;
import com.onestep.business_management.Exeption.ResourceNotFoundException;
import com.onestep.business_management.Repository.OrderOfflineRepository;
import com.onestep.business_management.Repository.OrderOnlineRepository;
import com.onestep.business_management.Scurity.config.VNPayConfig;
import com.onestep.business_management.Service.OrderOnlineService.OrderOnlineService;
import com.onestep.business_management.Service.OrderService.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AutoPaymentService {




    @Autowired
    OrderOnlineRepository orderOnlineRepository;

    @Autowired
    OrderOfflineRepository orderOfflineRepository;


    public boolean autoUpdatePayment(UUID orderId){
        if(orderId == null) return false;

        OrderOnline orderOnline = orderOnlineRepository.findById(orderId).orElse(null);

        if(orderOnline != null){
            orderOnline.setPaymentStatus(true);
            orderOnline.setPaymentMethod("Transfer");
            orderOnlineRepository.save(orderOnline);
            return true;
        }

        OrderOffline orderOffline = orderOfflineRepository.findById(orderId).orElse(null);

        if(orderOffline != null){
            orderOffline.setPaymentStatus(true);
            orderOffline.setPaymentMethod("Transfer");
            orderOffline.setStatus("COMPLETED");
            orderOfflineRepository.save(orderOffline);
            return true;
        }

        return false;
    }

}
