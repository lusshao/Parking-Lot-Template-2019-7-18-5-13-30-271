package com.thoughtworks.parking_lot.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table
public class ParkingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String carId;
    private Timestamp carInTime;
    private Timestamp carOutTime;
    private String orderStatus;

    @ManyToOne
    private ParkingLot parkingLot;


    public ParkingOrder() {
    }

    public ParkingOrder(String carId, Timestamp carInTime, Timestamp carOutTime, String orderStatus, ParkingLot parkingLot) {
        this.carId = carId;
        this.carInTime = carInTime;
        this.carOutTime = carOutTime;
        this.orderStatus = orderStatus;
        this.parkingLot = parkingLot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public Timestamp getCarInTime() {
        return carInTime;
    }

    public void setCarInTime(Timestamp carInTime) {
        this.carInTime = carInTime;
    }

    public Timestamp getCarOutTime() {
        return carOutTime;
    }

    public void setCarOutTime(Timestamp carOutTime) {
        this.carOutTime = carOutTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }
}
