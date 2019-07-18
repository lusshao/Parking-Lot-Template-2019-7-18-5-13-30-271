package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
public class ParkingLotRepositoryTest {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void should_add_and_return_parkingLot(){
        ParkingLot parkingLot = new ParkingLot(100,"香洲区");

        ParkingLot returnParkingLot = parkingLotRepository.save(parkingLot);

        assertThat(returnParkingLot.getCapacity()).isEqualTo(100);
        assertThat(returnParkingLot.getPosition()).isEqualTo("香洲区");

    }
}
