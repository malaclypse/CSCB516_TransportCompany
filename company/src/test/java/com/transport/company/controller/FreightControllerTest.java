package com.transport.company.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transport.company.controller.FreightController;
import com.transport.company.service.FreightService;
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
@WebMvcTest(controllers = FreightController.class)
class FreightControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FreightService service;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FreightController freightController;

    @Test
    public void controllerInitializedCorrectly() {
        Assertions.assertThat(freightController).isNotNull();
    }

    @Test
    void showFreightList() throws Exception {
        mockMvc.perform(get("/freights"))
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> result.getResponse().getRedirectedUrl().equals("/freights/page/1?sort-field=id&sort-dir=asc)"));
    }
}