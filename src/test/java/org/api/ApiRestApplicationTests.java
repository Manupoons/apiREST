package org.api;

import org.api.domain.CompraDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@TestExecutionListeners(
        listeners = {TransactionalTestExecutionListener.class},
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
class ApiRestApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void assertThatCompraWithoutTicketsFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNumero_entradas(null);  // Invalid input
        mockMvc.perform(post("/api/compras/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void assertThatCompraWithLessThan1TicketFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNumero_entradas(0);
        mockMvc.perform(post("/api/compras/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void assertThatCompraWithMoreThan20TicketsFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNumero_entradas(21);
        mockMvc.perform(post("/api/compras/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void assertThatEdicionWithLessThan1TicketFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNumero_entradas(0);
        mockMvc.perform(put("/api/compras/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void assertThatEdicionWithMoreThan20TicketsFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNumero_entradas(21);
        mockMvc.perform(put("/api/compras/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest());
    }
}
