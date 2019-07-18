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

}
