package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
public class ParkingControllerTest {

    @MockBean
    ParkingLotService parkingLotService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_return_parking_lot_message_when_post_to_parking_lost() throws Exception {
        ParkingLot parkingLot = new ParkingLot("第一停车场",100,"香洲区");
        when(parkingLotService.addParkingLot(any())).thenReturn(parkingLot);
        String jsonString ="{\n" +
                "\n" +
                "    \"name\": \"lay\",\n" +
                "    \"capacity\":100,\n" +
                "    \"address\":\"ddddd\"\n" +
                "}";

        ResultActions result = mockMvc.perform(post("/parking-lots").contentType(MediaType.APPLICATION_JSON).content(jsonString));

        result.andExpect(status().isOk());
    }

}
