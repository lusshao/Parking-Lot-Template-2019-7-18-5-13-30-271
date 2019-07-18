package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
public class ParkingLotRepositoryTest {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void should_add_and_return_parkingLot(){
        ParkingLot parkingLot = new ParkingLot("第一停车场",100,"香洲区");

        ParkingLot returnParkingLot = parkingLotRepository.save(parkingLot);

        assertThat(returnParkingLot.getCapacity()).isEqualTo(100);
        assertThat(returnParkingLot.getAddress()).isEqualTo("香洲区");

    }

    @Test
    public void should_delete_parkingLot(){
        ParkingLot parkingLot = new ParkingLot("第一停车场",100,"香洲区");

        ParkingLot parkingLot1 = parkingLotRepository.save(parkingLot);
        parkingLotRepository.delete(parkingLot);
        Optional<ParkingLot> returnParkingLot = parkingLotRepository.findById(parkingLot1.getName());

        assertThat(returnParkingLot.equals(null));
    }

    @Test
    public void should_return_all_parkingLot_list_by_page(){
        for(int i=0;i<20;i++){
            parkingLotRepository.save(new ParkingLot("第"+i+"停车场",100,"香洲区"));
        }
        Page<ParkingLot> parkingLotList = parkingLotRepository.findAll(new PageRequest(0,15));

        assertThat(parkingLotList.getContent().get(1).getName()).isEqualTo("第1停车场");
        assertThat(parkingLotList.getContent().get(11).getName()).isEqualTo("第11停车场");

    }

    @Test
    public void should_return_parking_lot_by_name(){
        ParkingLot parkingLot = new ParkingLot("第一停车场",100,"香洲区");

        ParkingLot parkingLot1 = parkingLotRepository.save(parkingLot);
        Optional<ParkingLot> returnParkingLot = parkingLotRepository.findById(parkingLot1.getName());

        assertThat(returnParkingLot.get().getAddress()).isEqualTo("香洲区");
        assertThat(returnParkingLot.get().getCapacity()).isEqualTo(100);
    }

    @Test
    public void should_add_capacity_when_need_extend_parking_lot_given_new_capacity(){
        ParkingLot parkingLot = new ParkingLot("第一停车场",100,"香洲区");

        ParkingLot parkingLot1 = parkingLotRepository.save(parkingLot);
        parkingLot1.setCapacity(200);
        ParkingLot returnParkingLot = parkingLotRepository.save(parkingLot1);

        assertThat(returnParkingLot.getCapacity()).isEqualTo(200);
    }

}
