package org.api;

import org.api.domain.CompraDTO;
import org.api.domain.EventoDTO;
import org.api.domain.PersonaDTO;
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
        compraDTO.setIdEvento(1L);
        compraDTO.setIdPersona(1L);
        mockMvc.perform(post("/api/compras/guardar/{idEvento}/{idPersona}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("The client name can't be null"));
    }

    @Test
    void assertThatCompraWithInvalidNameFails() throws Exception{
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente("-");
        compraDTO.setNumero_entradas(3);
        compraDTO.setFecha_compra("2002-02-15");
        compraDTO.setIdEvento(1L);
        compraDTO.setIdPersona(1L);
        mockMvc.perform(post("/api/compras/guardar/{idEvento}/{idPersona}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("The client name can only contain letters and spaces"));
    }

    @Test
    void assertThatCompraWithInvalidNameLengthFails() throws Exception{
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente("lucialucialucialucialucialucialucialucialucialucialucia");
        compraDTO.setNumero_entradas(3);
        compraDTO.setFecha_compra("2002-02-15");
        compraDTO.setIdEvento(1L);
        compraDTO.setIdPersona(1L);
        mockMvc.perform(post("/api/compras/guardar/{idEvento}/{idPersona}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("The client name can't be longer than 50 characters"));
    }

    @Test
    void assertThatCompraWithoutTicketsFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente("Manu");
        compraDTO.setNumero_entradas(null);
        compraDTO.setFecha_compra("2002-02-15");
        compraDTO.setIdEvento(1L);
        compraDTO.setIdPersona(1L);
        mockMvc.perform(post("/api/compras/guardar/{idEvento}/{idPersona}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("The number of tickets can't be null"));
    }

    @Test
    void assertThatCompraWithInvalidQuantityTicketsFails() throws Exception{
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente("Manu");
        compraDTO.setNumero_entradas(0);
        compraDTO.setFecha_compra("2002-02-15");
        compraDTO.setIdEvento(1L);
        compraDTO.setIdPersona(1L);
        mockMvc.perform(post("/api/compras/guardar/{idEvento}/{idPersona}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("The number of tickets has to be between 1 and 20"));
    }

    @Test
    void assertThatCompraWithoutDateFails() throws Exception{
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente("Manu");
        compraDTO.setNumero_entradas(3);
        compraDTO.setFecha_compra(null);
        compraDTO.setIdEvento(1L);
        compraDTO.setIdPersona(1L);
        mockMvc.perform(post("/api/compras/guardar/{idEvento}/{idPersona}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("The purchase date can't be null"));
    }

    @Test
    void assertThatCompraWithInvalidDateFails() throws Exception{

        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente("Manu");
        compraDTO.setNumero_entradas(3);
        compraDTO.setFecha_compra("200-02-15");
        compraDTO.setIdEvento(1L);
        compraDTO.setIdPersona(1L);
        mockMvc.perform(post("/api/compras/guardar/{idEvento}/{idPersona}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("Invalid date format. Expected format is yyyy-MM-dd"));
    }

    @Test
    void assertThatCompraWithInvalidFutureDateFails() throws Exception{
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNombre_cliente("Manu");
        compraDTO.setNumero_entradas(3);
        compraDTO.setFecha_compra("5000-02-15");
        compraDTO.setIdEvento(1L);
        compraDTO.setIdPersona(1L);
        mockMvc.perform(post("/api/compras/guardar/{idEvento}/{idPersona}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.compraError").value("The purchase date can't be in the future"));
    }

//
//    @Test
//    void assertThatCompraWithoutIdEventoFails() throws Exception{
//        CompraDTO compraDTO = new CompraDTO();
//        compraDTO.setNombre_cliente("Manu");
//        compraDTO.setNumero_entradas(3);
//        compraDTO.setFecha_compra("2024-02-15");
//        mockMvc.perform(post("/api/compras/guardar/{idEvento}/{idPersona}", "", 1)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(compraDTO)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.compraError").value("The evento id can't be null"));
//    }
//
//    @Test
//    void assertThatCompraWithoutIdPersonaFails() throws Exception{
//        CompraDTO compraDTO = new CompraDTO();
//        compraDTO.setNombre_cliente("Manu");
//        compraDTO.setNumero_entradas(3);
//        compraDTO.setFecha_compra("2024-02-15");
//        mockMvc.perform(post("/api/compras/guardar/{idEvento}/{idPersona}", 1, "")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(compraDTO)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.compraError").value("The persona id can't be null"));
//    }

    @Test
    void assertThatEditionCompraWithInvalidTicketFails() throws Exception {
        CompraDTO compraDTO = new CompraDTO();
        compraDTO.setNumero_entradas(0);
        mockMvc.perform(put("/api/compras/{idCompra}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(compraDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.editionCompraError").value("The number of edited tickets has to be between 1 and 20"));
    }

    @Test
    void assertThatEventoWithoutNameFails() throws Exception {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setNombre_evento(null);
        eventoDTO.setEmpresa_evento("cuatroochenta");
        mockMvc.perform(post("/api/evento/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.eventoError").value("The event name can't be empty"));
    }

    @Test
    void assertThatEventoWithInvalidNameFails() throws Exception {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setNombre_evento("º");
        eventoDTO.setEmpresa_evento("cuatroochenta");
        mockMvc.perform(post("/api/evento/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.eventoError").value("The event name can only contain letters, certain special characters and spaces"));
    }

    @Test
    void assertThatEventoWitInvalidTimeFails() throws Exception {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setNombre_evento("Arenal");
        eventoDTO.setHora_evento("64:00");
        eventoDTO.setEmpresa_evento("cuatroochenta");
        mockMvc.perform(post("/api/evento/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.eventoError").value("Invalid time format. Expected format is HH:mm"));
    }

    @Test
    void assertThatEventoWitInvalidDateFails() throws Exception {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setNombre_evento("Arenal");
        eventoDTO.setFecha_evento("20-02-15");
        eventoDTO.setEmpresa_evento("cuatroochenta");
        mockMvc.perform(post("/api/evento/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.eventoError").value("Invalid date format. Expected format is yyyy-MM-dd"));
    }

    @Test
    void assertThatEventoWitInvalidFutureDateFails() throws Exception {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setNombre_evento("Arenal");
        eventoDTO.setFecha_evento("5000-02-15");
        eventoDTO.setEmpresa_evento("cuatroochenta");
        mockMvc.perform(post("/api/evento/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.eventoError").value("The purchase date can't be in the future"));
    }

    @Test
    void assertThatEventoWithoutEmpresaFails() throws Exception{
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setNombre_evento("Estatus");
        eventoDTO.setEmpresa_evento(null);
        mockMvc.perform(post("/api/evento/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.eventoError").value("The business name can't be empty"));
    }

    @Test
    void assertThatEventoWithInvalidEmpresaFails() throws Exception{
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setNombre_evento("Estatus");
        eventoDTO.setEmpresa_evento("ººº");
        mockMvc.perform(post("/api/evento/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.eventoError").value("The business name can only contain letters and spaces"));
    }

    @Test
    void assertThatEditionEventoWithInvalidNameFails() throws Exception {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setNombre_evento("*****");
        mockMvc.perform(put("/api/evento/{idEvento}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.editionEventoError").value("The event name can only contain letters, certain special characters and spaces"));
    }

    @Test
    void assertThatEditionEventoWithInvalidTimeFails() throws Exception {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setHora_evento("1:65");
        mockMvc.perform(put("/api/evento/{idEvento}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.editionEventoError").value("Invalid time format. Expected format is HH:mm"));
    }

    @Test
    void assertThatEditionEventoWithInvalidDateFails() throws Exception {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setFecha_evento("200-02-15");
        mockMvc.perform(put("/api/evento/{idEvento}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.editionEventoError").value("Invalid date format. Expected format is yyyy-MM-dd"));
    }

    @Test
    void assertThatEditionEventoWithInvalidFutureDateFails() throws Exception {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setFecha_evento("5000-02-15");
        mockMvc.perform(put("/api/evento/{idEvento}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventoDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.editionEventoError").value("The date can't be in the future"));
    }

    @Test
    void assertThatPersonaWithoutNameFails() throws Exception {
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setCorreo_persona("mpons@gmail.com");
        mockMvc.perform(post("/api/persona/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.personaError").value("The persona name can't be empty"));
    }

    @Test
    void assertThatPersonaWithInvalidNameFails() throws Exception {
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setNombre_persona("98");
        personaDTO.setCorreo_persona("mpons@gmail.com");
        mockMvc.perform(post("/api/persona/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.personaError").value("The name of the person can only contain letters and spaces"));
    }

    @Test
    void assertThatPersonaWithoutCorreoFails() throws Exception {
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setNombre_persona("Manu");
        mockMvc.perform(post("/api/persona/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.personaError").value("The persona email can't be empty"));
    }

    @Test
    void assertThatPersonaWithInvalidCorreoFails() throws Exception {
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setNombre_persona("Manu");
        personaDTO.setCorreo_persona("__________");
        mockMvc.perform(post("/api/persona/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.personaError").value("The email of the person is not valid"));
    }

    @Test
    void assertThatPersonaWithInvalidTelefonoFails() throws Exception {
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setNombre_persona("Manu");
        personaDTO.setCorreo_persona("mpons@gmail.com");
        personaDTO.setTelefono_persona("666666666*");
        mockMvc.perform(post("/api/persona/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.personaError").value("The phone number of the person is not valid. It must be exactly 9 digits long"));
    }

    @Test
    void assertThatEditionPersonaWithInvalidNameFails() throws Exception{
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setNombre_persona("------");
        mockMvc.perform(put("/api/persona/{idPersona}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.editionPersonaError").value("The persona name can only contain letters and spaces"));
    }

    @Test
    void assertThatEditionPersonaWithInvalidTelefonoFails() throws Exception{
        PersonaDTO personaDTO = new PersonaDTO();
        personaDTO.setTelefono_persona("666666666*");
        mockMvc.perform(put("/api/persona/{idPersona}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personaDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.editionPersonaError").value("The phone number of the person is not valid"));
    }
}
