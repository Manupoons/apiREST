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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
    void assertThatCompraWithoutNameFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente(null);
        compraDTO.setNumero_entradas(3);
        compraDTO.setFecha_compra("2002-02-15");
        mockMvc.perform(post("/api/compras/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("The client name can't be null"));
    }

    @Test
    void assertThatCompraWithoutTicketsFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente("Manu");
        compraDTO.setNumero_entradas(null);
        compraDTO.setFecha_compra("2002-02-15");
        mockMvc.perform(post("/api/compras/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("The number of tickets can't be null"));
    }

    @Test
    void assertThatCompraWithLessThan1TicketFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente("Manu");
        compraDTO.setNumero_entradas(0);
        compraDTO.setFecha_compra("2002-02-15");
        mockMvc.perform(post("/api/compras/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("The number of tickets can't be less than 1"));
    }

    @Test
    void assertThatCompraWithMoreThan20TicketsFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente("Manu");
        compraDTO.setNumero_entradas(21);
        compraDTO.setFecha_compra("2002-02-15");
        mockMvc.perform(post("/api/compras/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("The number of tickets can't be more than 20"));
    }

    @Test
    void assertThatCompraWithInvalidDateFails() throws Exception{
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente("Manu");
        compraDTO.setNumero_entradas(3);
        compraDTO.setFecha_compra("200-02-15");
        mockMvc.perform(post("/api/compras/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("Invalid date format. Expected format is yyyy-MM-dd"));
    }

    @Test
    void assertThatEditionWithLessThan1TicketFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNumero_entradas(0);
        mockMvc.perform(put("/api/compras/{idCompra}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.editionError").value("The number of edited tickets can't be less than 1"));
    }

    @Test
    void assertThatEditionWithMoreThan20TicketsFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNumero_entradas(21);
        mockMvc.perform(put("/api/compras/{idCompra}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.editionError").value("The number of edited tickets can't be more than 20"));
    }

    @Test
    void assertThatEditionWithInvalidDateFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNumero_entradas(3);
        compraDTO.setFecha_compra("200-02-15");
        mockMvc.perform(put("/api/compras/{idCompra}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.editionError").value("Invalid date format. Expected format is yyyy-MM-dd"));
    }
}
