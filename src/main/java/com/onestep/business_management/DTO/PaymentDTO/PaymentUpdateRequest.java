package com.onestep.business_management.DTO.PaymentDTO;

public class PaymentUpdateRequest {
    private String paymentMethod;
    private boolean paymentStatus;

    // Getter và Setter
    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}