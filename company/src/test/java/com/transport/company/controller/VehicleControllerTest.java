package com.transport.company.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transport.company.controller.VehicleController;
import com.transport.company.service.VehicleService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = VehicleController.class)
class VehicleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VehicleService service;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private VehicleController vehicleController;
    @Test
    public void controllerInitializedCorrectly() {
        Assertions.assertThat(vehicleController).isNotNull();
    }

    @Test
    void showVehicleList() throws Exception {
        mockMvc.perform(get("/vehicles"))
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> result.getResponse().getRedirectedUrl().equals("/vehicles/page/1?sort-field=id&sort-dir=asc)"));
    }

}