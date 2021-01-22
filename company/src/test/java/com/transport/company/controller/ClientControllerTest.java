package com.transport.company.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transport.company.service.ClientService;
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
@WebMvcTest(controllers = ClientController.class)
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClientService service;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ClientController clientController;
    @Test
    public void controllerInitializedCorrectly() {
        Assertions.assertThat(clientController).isNotNull();
    }

    @Test
    void showClientList() throws Exception {
        mockMvc.perform(get("/clients"))
                .andExpect(status().is3xxRedirection())
                .andExpect(result -> result.getResponse().getRedirectedUrl().equals("/clients/page/1?sort-field=id&sort-dir=asc)"));
    }

    @Test
    void testShowClientList() {
    }

    @Test
    void showCreateForm() {
    }

    @Test
    void showUpdateForm() {
    }

    @Test
    void saveClient() {
    }

    @Test
    void updateClient() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void downloadCsv() {
    }
}