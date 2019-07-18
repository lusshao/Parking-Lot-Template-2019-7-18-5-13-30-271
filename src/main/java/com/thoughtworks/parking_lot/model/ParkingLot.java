package com.thoughtworks.parking_lot.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Table
public class ParkingLot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String address;

    public ParkingLot() {
    }

    public ParkingLot(int capacity, String address) {
        this.capacity = capacity;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
