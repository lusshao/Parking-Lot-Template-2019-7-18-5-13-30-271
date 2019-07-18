package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class ParkingOrderService {

    @Autowired
    ParkingOrderRepository parkingOrderRepository;

    public ParkingOrder addParkingOrder(ParkingOrder parkingOrder) {
        return parkingOrderRepository.save(parkingOrder);
    }

    public ParkingOrder updateParkingOrderStatus(ParkingOrder parkingOrder) {
        ParkingOrder returnParkingOrder = parkingOrderRepository.findByCarId(parkingOrder.getCarId());
        returnParkingOrder.setCarOutTime(new Timestamp(new Date().getTime()));
        returnParkingOrder.setOrderStatus("close");
        return parkingOrderRepository.save(returnParkingOrder);
    }
}
