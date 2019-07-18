package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {

    @Autowired
    ParkingLotRepository parkingLotRepository;

    public ParkingLot addParkingLot(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public void deleteParkingLots(ParkingLot parkingLot){
        parkingLotRepository.delete(parkingLot);
    }

    public List<ParkingLot> findParkingLostByPage(int pageInt) {
        return parkingLotRepository.findAll(new PageRequest(pageInt,15)).getContent();
    }
}
