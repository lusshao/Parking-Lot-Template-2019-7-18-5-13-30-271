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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        result.andExpect(status().isOk()).andExpect(jsonPath("$.name",is("第一停车场")));
    }

    @Test
    public void should_return_null_when_find_a_deleted_parking_lots() throws Exception {
        ParkingLot parkingLot = new ParkingLot("第一停车场",100,"香洲区");
        when(parkingLotService.addParkingLot(any())).thenReturn(parkingLot);
        String jsonString ="{\n" +
                "\n" +
                "    \"name\": \"lay\",\n" +
                "    \"capacity\":100,\n" +
                "    \"address\":\"ddddd\"\n" +
                "}";

        mockMvc.perform(post("/parking-lots").contentType(MediaType.APPLICATION_JSON).content(jsonString));
        mockMvc.perform(delete("/parking-lots").contentType(MediaType.APPLICATION_JSON).content(jsonString));

        verify(parkingLotService).deleteParkingLots(any());
    }

    @Test
    public void should_return_a_list_when_get_to_parking_lots_given_page() throws Exception {
        List<ParkingLot> parkingLotList = new ArrayList<>();
        for(int i=0;i<15;i++){
            parkingLotList.add(new ParkingLot("第"+i+"停车场",100,"香洲区"));
        }
        when(parkingLotService.findParkingLostByPage(anyInt())).thenReturn(parkingLotList);

        ResultActions result = mockMvc.perform(get("/parking-lots?pageInt={pageInt}",0));

        result.andExpect(status().isOk()).andExpect(jsonPath("$[5].name",is("第5停车场")));
    }


}
