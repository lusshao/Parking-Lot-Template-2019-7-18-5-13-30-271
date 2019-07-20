package com.thoughtworks.parking_lot;

import com.thoughtworks.parking_lot.exception.FullParkingLotException;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import com.thoughtworks.parking_lot.repository.ParkingOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Autowired
    private ParkingLotRepository parkingLotRepository;
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    @BeforeEach
    void setup(){ mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build(); }

    @Test
    public void should_return_a_list_when_get_to_parking_lots_given_page() throws Exception {
        for(int i=0;i<20;i++){
            parkingLotRepository.save(new ParkingLot("第"+i+"停车场",100,"香洲区"));
        }

        ResultActions result = mockMvc.perform(get("/parking-lots?pageInt={pageInt}",0));

        result.andExpect(status().isOk());
        for(int i=0;i<15;i++){
            result.andExpect(jsonPath("$["+i+"].name",is("第"+i+"停车场")));
        }
    }

    @Test
    void Story1ac3() throws Exception {
        String jsonString ="{\n" +
                "\n" +
                "    \"name\": \"lay\",\n" +
                "    \"capacity\":100,\n" +
                "    \"address\":\"香洲区\"\n" +
                "}";
        ResultActions result = mockMvc.perform(post("/parking-lots")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk());

        assertThat(parkingLotRepository.findById("lay").get().getCapacity()).isEqualTo(100);
        assertThat(parkingLotRepository.findById("lay").get().getAddress()).isEqualTo("香洲区");

    }

    @Test
    void Story1ac2() throws Exception {
        ParkingLot parkingLot = new ParkingLot("第一停车场",100,"香洲区");
        String jsonString ="{\n" +
                "\n" +
                "    \"name\": \"第一停车场\",\n" +
                "    \"capacity\":100,\n" +
                "    \"address\":\"香洲区\"\n" +
                "}";

        parkingLotRepository.save(parkingLot);
        mockMvc.perform(delete("/parking-lots").contentType(MediaType.APPLICATION_JSON).content(jsonString));
        Optional<ParkingLot> returnParkingLot = parkingLotRepository.findById(parkingLot.getName());

        assertThat(returnParkingLot.equals(null));
    }


    @Test
    public void Story1ac4() throws Exception {
        ParkingLot parkingLot = new ParkingLot("第1停车场",100,"香洲区");
        parkingLotRepository.save(parkingLot);

        ResultActions result = mockMvc.perform(get("/parking-lots?name={name}",parkingLot.getName()));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.name",is("第1停车场")));
    }

    @Test
    public void Story1ac5() throws Exception {
        ParkingLot parkingLot = new ParkingLot("第一停车场",100,"香洲区");
        ParkingLot parkingLot1 = parkingLotRepository.save(parkingLot);
        String jsonString ="{\n" +
                "\n" +
                "    \"name\": \"第一停车场\",\n" +
                "    \"capacity\":100,\n" +
                "    \"address\":\"香洲区\"\n" +
                "}";

        ResultActions result = mockMvc.perform(put("/parking-lots?capacity={capacity}",200).contentType(MediaType.APPLICATION_JSON).content(jsonString));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.capacity",is(200)));
    }

    @Test
    public void Story2ac1() throws Exception {
        ParkingLot parkingLot = new ParkingLot("一号停车场",100,"香洲区");
        parkingLotRepository.save(parkingLot);
        String jsonString="{\n" +
                "\n" +
                "    \"carId\": \"laydd\",\n" +
                "    \"parking_lot_name\":\"一号停车场\"\n" +
                "}";
        ResultActions result = mockMvc.perform(post("/parking-orders?name={name}",parkingLot.getName()).contentType(MediaType.APPLICATION_JSON).content(jsonString));
        result.andExpect(status().isOk()).andExpect(jsonPath("$.carId",is("laydd")));

    }

    @Test
    public void Story2ac2() throws Exception {
        ParkingLot parkingLot = new ParkingLot("一号停车场",100,"香洲区");
        parkingLotRepository.save(parkingLot);
        ParkingOrder parkingOrder = new ParkingOrder("laycc",new Timestamp(new Date().getTime()),null,"close",parkingLot);
        parkingOrderRepository.save(parkingOrder);
        String jsonString="{\n" +
                "\n" +
                "    \"carId\": \"laycc\",\n" +
                "    \"parking_lot_name\":\"一号停车场\"\n" +
                "}";
        ResultActions result = mockMvc.perform(put("/parking-orders").contentType(MediaType.APPLICATION_JSON).content(jsonString));
        result.andExpect(status().isOk());

        assertThat(parkingOrderRepository.findByCarId("laycc").getOrderStatus()).isEqualTo("close");
    }

    @Test
    public void Story2ac3() throws Exception {
        ParkingLot parkingLot = new ParkingLot("一号停车场",1,"香洲区");
        parkingLotRepository.save(parkingLot);
        ParkingOrder parkingOrder = new ParkingOrder("layss",new Timestamp(new Date().getTime()),null,"close",parkingLot);
        parkingOrderRepository.save(parkingOrder);
        String jsonString="{\n" +
                "\n" +
                "    \"carId\": \"layss\",\n" +
                "    \"parking_lot_name\":\"一号停车场\"\n" +
                "}";

        ResultActions result = mockMvc.perform(post("/parking-orders?name={name}",parkingLot.getName()).contentType(MediaType.APPLICATION_JSON).content(jsonString));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.error",is("停车场已满")));
    }
}
