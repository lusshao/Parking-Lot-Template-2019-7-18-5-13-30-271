package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import com.thoughtworks.parking_lot.service.ParkingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.sql.Timestamp;

@RestController
public class ParkingOrderController {

    @Autowired
    ParkingOrderService parkingOrderService;
    @Autowired
    ParkingLotService parkingLotService;

    @PostMapping(value = "/parking-orders",params = "name")
    public ParkingOrder addParkingOrder(@RequestBody ParkingOrder parkingOrder,@RequestParam String name){

        ParkingLot parkingLot = parkingLotService.findParkingByName(name);
        if(parkingLot==null){
            parkingOrder.setError("停车场不存在");
            return parkingOrder;
        }
        parkingOrder.setOrderStatus("open");
        parkingOrder.setCarInTime(new Timestamp(new Date().getTime()));
        parkingOrder.setParkingLot(parkingLot);
        return parkingOrderService.addParkingOrder(parkingOrder);
    }

    @PutMapping(value = "/parking-orders")
    public ParkingOrder closeParkingOrderStatus(@RequestBody ParkingOrder parkingOrder){
        return parkingOrderService.updateParkingOrderStatus(parkingOrder);
    }
}
