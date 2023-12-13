package com.example.practicecrude;

public class Transaction {
    private int productId;
    private String productName;
    private int quantity;

    private int transID;

    public Transaction(int transID, int productId, String productName, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.transID = transID;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTransID() {
        return transID;
    }

}

