package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import com.thoughtworks.parking_lot.service.ParkingOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingOrderController.class)
public class ParkingOrderControllerTest {

    @MockBean
    ParkingOrderService parkingOrderService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_add_and_return_parking_order() throws Exception {
        ParkingOrder parkingOrder = new ParkingOrder("sfdsf",new Timestamp(new Date().getTime()),null,"open",new ParkingLot("ddddd",100,"ddddssss"));
        when(parkingOrderService.addParkingOrder(any())).thenReturn(parkingOrder);
        String jsonString="{\n" +
                "\n" +
                "    \"carId\": \"laydd\",\n" +
                "    \"parking_lot_name\":\"ddddd\"\n" +
                "}";

        ResultActions result = mockMvc.perform(post("/parking-orders").contentType(MediaType.APPLICATION_JSON).content(jsonString));

        result.andExpect(status().isOk()).andExpect(jsonPath("$.carId",is("sfdsf")));
    }

    @Test
    public void should_close_order_when_put_parking_orders_given_a_car_live() throws Exception {
        ParkingOrder parkingOrder = new ParkingOrder("sfdsf",new Timestamp(new Date().getTime()),null,"close",new ParkingLot("ddddd",100,"ddddssss"));
        when(parkingOrderService.updateParkingOrderStatus(any())).thenReturn(parkingOrder);
        String jsonString="{\n" +
                "\n" +
                "    \"carId\": \"laydd\",\n" +
                "    \"parking_lot_name\":\"ddddd\"\n" +
                "}";
        ResultActions result = mockMvc.perform(put("/parking-orders").contentType(MediaType.APPLICATION_JSON).content(jsonString));
        result.andExpect(status().isOk());
    }

}
