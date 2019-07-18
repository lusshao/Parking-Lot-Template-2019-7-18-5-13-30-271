package com.thoughtworks.parking_lot.model;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class ParkingLot {

    @Id
    private String name;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private String address;

    @OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY, mappedBy = "parkingLot")
    private List<ParkingOrder> parkingOrders;

    public ParkingLot() {
    }

    public ParkingLot(String name, int capacity, String address) {
        this.name = name;
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
