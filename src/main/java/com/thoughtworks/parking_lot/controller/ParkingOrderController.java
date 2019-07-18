package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import com.thoughtworks.parking_lot.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.sql.Timestamp;

@RestController
public class ParkingOrderController {

    @Autowired
    ParkingOrderService parkingOrderService;

    @PostMapping("/parking-orders")
    public ParkingOrder addParkingOrder(@RequestBody ParkingOrder parkingOrder){
        parkingOrder.setOrderStatus("open");
        parkingOrder.setCarInTime(new Timestamp(new Date().getTime()));
        return parkingOrderService.addParkingOrder(parkingOrder);
    }

    @PutMapping(value = "/parking-orders")
    public ParkingOrder closeParkingOrderStatus(@RequestBody ParkingOrder parkingOrder){
        return parkingOrderService.updateParkingOrderStatus(parkingOrder);
    }
}
