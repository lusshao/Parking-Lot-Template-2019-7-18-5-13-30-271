package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingLotController {

    @Autowired
    ParkingLotService parkingLotService;

    @PostMapping("/parking-lots")
    public ParkingLot addParkingLot(@RequestBody ParkingLot parkingLot){
        return parkingLotService.addParkingLot(parkingLot);
    }

    @DeleteMapping("/parking-lots")
    public void deleteParkingLot(@RequestBody ParkingLot parkingLot){
        parkingLotService.deleteParkingLots(parkingLot);
    }

    @GetMapping(value = "/parking-lots",params = "pageInt")
    public List<ParkingLot> findParkingLotsByPage(@RequestParam int pageInt){
        return parkingLotService.findParkingLostByPage(pageInt);
    }

    @GetMapping(value = "/parking-lots",params = "name")
    public ParkingLot findParkingLotByName(@RequestParam String name){
        return parkingLotService.findParkingByName(name);
    }

    @PutMapping(value = "/parking-lots",params = "capacity")
    public ParkingLot updateParkingLotCapacity(@RequestParam int capacity,@RequestBody ParkingLot parkingLot){
        parkingLot.setCapacity(capacity);
        return parkingLotService.updateParkingLotCapacity(parkingLot);
    }


}
