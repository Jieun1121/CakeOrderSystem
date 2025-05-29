package cakeapp.dto;

public class CakeOrderDTO {
    private int orderId;
    private String cakeName;
    private String customizationOptions;
    private String orderDate;
    private String orderStatus;
    private int customerId;

    // Getters
    public int getOrderId() {
        return orderId;
    }

    public String getCakeName() {
        return cakeName;
    }

    public String getCustomizationOptions() {
        return customizationOptions;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public int getCustomerId() {
        return customerId;
    }

    // Setters
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setCakeName(String cakeName) {
        this.cakeName = cakeName;
    }

    public void setCustomizationOptions(String customizationOptions) {
        this.customizationOptions = customizationOptions;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}