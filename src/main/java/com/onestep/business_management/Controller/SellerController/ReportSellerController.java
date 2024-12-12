package com.onestep.business_management.Controller.SellerController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import com.onestep.business_management.Service.ReportService.*;
import com.onestep.business_management.DTO.ReportDTO.*;
import com.onestep.business_management.DTO.API.*;


@RestController
@RequestMapping("/api/seller/reports")
public class ReportSellerController {

    @Autowired
    private ReportService reportService;

    // Endpoint để lấy tổng giá trị đơn hàng theo ngày
    @GetMapping("/{storeId}/by-day")
    public ResponseEntity<?> getAllProducts(@PathVariable UUID storeId,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        try {

            // Use Date values in the service call
            List<Object[]> result = reportService.getOrderTotalValueByDate(storeId, startDate, endDate);

            if (result.isEmpty()) {
                ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "No products found for the given store and date range.",
                        null,
                        LocalDateTime.now());
                return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
            }

            ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Products retrieved successfully",
                    result,
                    LocalDateTime.now());

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error retrieving products: " + e.getMessage());

            ApiResponse errorResponse = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error retrieving products: " + e.getMessage(),
                    null,
                    LocalDateTime.now());

            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{storeId}/by-month")
    public ResponseEntity<?> getOrderTotalValueByMonth(@PathVariable UUID storeId) {
        try {
            // Gọi service để lấy dữ liệu
            List<Object[]> result = reportService.getOrderTotalValueByMonth(storeId);

            if (result.isEmpty()) {
                // Trả về response nếu không có dữ liệu
                ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "No data found for the given store and month range.",
                        null,
                        LocalDateTime.now());
                return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
            }

            // Trả về response nếu có dữ liệu
            ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Monthly data retrieved successfully",
                    result,
                    LocalDateTime.now());

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            // Log lỗi và trả về response lỗi
            System.out.println("Error retrieving data by month: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error retrieving data: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Endpoint để lấy tổng giá trị đơn hàng theo năm
    @GetMapping("/{storeId}/by-year")
    public ResponseEntity<?> getOrderTotalValueByYear(@PathVariable UUID storeId) {
        try {
            List<Object[]> result = reportService.getOrderTotalValueByYear(storeId);

            if (result.isEmpty()) {
                ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "No data found for the given store and year range.",
                        null,
                        LocalDateTime.now());
                return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
            }

            ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Yearly data retrieved successfully",
                    result,
                    LocalDateTime.now());

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error retrieving data by year: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error retrieving data: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint để lấy tổng giá trị của tất cả đơn hàng
    @GetMapping("/{storeId}/total-value")
    public ResponseEntity<?> getTotalOrderValue(@PathVariable UUID storeId) {
        try {
            Double totalValue = reportService.getTotalOrderValue(storeId);

            ApiResponse<Double> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Total order value retrieved successfully",
                    totalValue,
                    LocalDateTime.now());

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error retrieving total order value: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error retrieving total order value: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{storeId}/top-customers")
    public ResponseEntity<?> getTop3CustomersWithMostOrdersByStore(@PathVariable UUID storeId) {
        try {
            List<Object[]> topCustomers = reportService.getTop3CustomersWithMostOrdersByStore(storeId);

            if (topCustomers.isEmpty()) {
                ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "No customers found for the given store.",
                        null,
                        LocalDateTime.now());
                return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
            }

            ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Top customers retrieved successfully",
                    topCustomers,
                    LocalDateTime.now());

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error retrieving top customers: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error retrieving top customers: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{storeId}/top-products")
    public ResponseEntity<?> getTop3MostSoldProducts(@PathVariable UUID storeId) {
        try {
            List<Object[]> topProducts = reportService.getTop3MostSoldProducts(storeId);

            if (topProducts.isEmpty()) {
                ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "No products found for the given store.",
                        null,
                        LocalDateTime.now());
                return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
            }

            ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Top 3 most sold products retrieved successfully",
                    topProducts,
                    LocalDateTime.now());
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error retrieving top products: " + e.getMessage());
            ApiResponse errorResponse = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error retrieving top products: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{storeId}/by-toDay")
    public ResponseEntity<?> getPaidOrdersMadeTodayByStoreId(@PathVariable UUID storeId) {
        try {
            // Lấy danh sách đơn hàng đã thanh toán trong ngày hôm nay theo storeId
            List<Object[]> orders = reportService.getPaidOrdersMadeTodayByStoreId(storeId);

            if (orders.isEmpty()) {
                ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "No paid orders found for today in this store.",
                        null,
                        LocalDateTime.now());
                return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
            }

            ApiResponse<List<Object[]>> apiResponse = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Paid orders retrieved successfully for store.",
                    orders,
                    LocalDateTime.now());

            return new ResponseEntity<>(apiResponse, HttpStatus.OK);

        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error retrieving paid orders: " + e.getMessage(),
                    null,
                    LocalDateTime.now());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}