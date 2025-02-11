package com.example.pet_store.models;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private int id;

    @Column(nullable = false)
    private int petId;

    @Column(nullable = false)
    private int quantity;

    private String shipDate;

    @Column(nullable = false)
    private String status; // placed, approved, delivered

    @Column(nullable = false)
    private boolean complete;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPetId() {
        return petId;
    }
    public void setPetId(int petId) {
        this.petId = petId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getShipDate() {
        return shipDate;
    }
    public void setShipDate(String shipDate) {
        this.shipDate = shipDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public boolean isComplete() {
        return complete;
    }
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
}