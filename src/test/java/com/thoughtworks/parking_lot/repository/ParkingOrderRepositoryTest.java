package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.sql.Timestamp;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DataJpaTest
public class ParkingOrderRepositoryTest {

    @Autowired
    ParkingOrderRepository parkingOrderRepository;

    @Test
    public void should_add_and_return_a_new_order() {
        ParkingOrder parkingOrder = new ParkingOrder("13245", new Timestamp(new Date().getTime()), null, "open", new ParkingLot("第一停车场", 2, "香洲区"));

        ParkingOrder parkingOrder1 = parkingOrderRepository.save(parkingOrder);

        assertThat(parkingOrder1.getCarId()).isEqualTo("13245");
    }

    @Test
    public void should_update_the_order_when_car_is_live() throws InterruptedException {
        ParkingOrder parkingOrder = new ParkingOrder("13245", new Timestamp(new Date().getTime()), null, "open", new ParkingLot("第一停车场", 2, "香洲区"));

        sleep(1000);
        ParkingOrder parkingOrder1 = parkingOrderRepository.save(parkingOrder);
        parkingOrder1.setCarOutTime(new Timestamp(new Date().getTime()));
        parkingOrder1.setOrderStatus("close");

        ParkingOrder returnParkingOrder = parkingOrderRepository.save(parkingOrder1);

        assertThat(returnParkingOrder.getOrderStatus()).isEqualTo("close");
    }

}
