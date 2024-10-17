package com.onestep.business_management.Controller.BuyerController;

import com.onestep.business_management.DTO.API.ApiResponse;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineRequest;
import com.onestep.business_management.DTO.OrderDTO.OrderOnlineResponse;
import com.onestep.business_management.DTO.ProductDTO.ProductRequest;
import com.onestep.business_management.DTO.ProductDTO.ProductResponse;
import com.onestep.business_management.Service.OrderOnlineService.OrderOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/buyer/ordersOnline")
public class BuyerOrderOlineController {

    @Autowired
    private OrderOnlineService orderOnlineService;

    @PostMapping
    public ResponseEntity<?> createOrderOnline(@RequestBody OrderOnlineRequest orderRequest) {
        System.out.println("order rq: "+orderRequest);
        List<OrderOnlineResponse> response = orderOnlineService.createMultipleOrders(orderRequest);
        ApiResponse<List<OrderOnlineResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),  // Status code 200
                "create order by "+ orderRequest.getUserId()+" successfully!",
                response,
                LocalDateTime.now()  // Current date
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getOrdersByUserId(@RequestParam UUID userId) {
        List<OrderOnlineResponse> response = orderOnlineService.getOrdersOnlineByUser(userId);
        ApiResponse<List<OrderOnlineResponse>> apiResponse = new ApiResponse<>(
                HttpStatus.OK.value(),  // Status code 200
                "Successfully retrieved the orders of the user with id: "+userId,
                response,
                LocalDateTime.now()  // Current date
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
