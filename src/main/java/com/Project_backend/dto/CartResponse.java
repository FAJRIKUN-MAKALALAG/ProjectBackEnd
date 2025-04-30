package com.Project_backend.dto;

public class CartResponse {
    private Long cartId;
    private int quantity;

    private Long productId;
    private String productName;
    private double productPrice; // GANTI DARI int KE double
    private String productImageBase64;

    public CartResponse(Long cartId, int quantity, Long productId, String productName, double productPrice, String productImageBase64) {
        this.cartId = cartId;
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImageBase64 = productImageBase64;
    }

    // Getters & Setters
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public double getProductPrice() { return productPrice; }
    public void setProductPrice(double productPrice) { this.productPrice = productPrice; }

    public String getProductImageBase64() { return productImageBase64; }
    public void setProductImageBase64(String productImageBase64) { this.productImageBase64 = productImageBase64; }
}
