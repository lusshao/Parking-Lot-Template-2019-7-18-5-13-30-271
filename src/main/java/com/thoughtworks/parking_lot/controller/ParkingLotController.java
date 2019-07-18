package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParkingLotController {

    @Autowired
    ParkingLotRepository parkingLotRepository;

    
}
