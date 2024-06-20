package org.api;

import org.api.domain.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.context.TestExecutionListeners;
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

    @Nested
    class compraTests{

        @Nested
        class crearCompra{
            @Test
            void assertThatCompraWithIdEventoLessThan1Fails() throws Exception {
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("lucia");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}", -5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser mayor que 0"));
            }

            @Test
            void assertThatCompraWithIdEventoMoreThan1000000Fails() throws Exception {
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("lucia");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}", 1000001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id no puede ser tan grande"));
            }

            @Test
            void assertThatCompraWithInvalidIdEventoFails() throws Exception {
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("lucia");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}", "1.5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser un numero entero"));
            }

            @Test
            void assertThatCompraWithoutNameFails() throws Exception {
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente(null);
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}", 1)
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
                mockMvc.perform(post("/api/compra/guardar/{idEvento}", 1)
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
                mockMvc.perform(post("/api/compra/guardar/{idEvento}", 1)
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
                mockMvc.perform(post("/api/compra/guardar/{idEvento}", 1)
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
                mockMvc.perform(post("/api/compra/guardar/{idEvento}", 1)
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
                mockMvc.perform(post("/api/compra/guardar/{idEvento}", 1)
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
                mockMvc.perform(post("/api/compra/guardar/{idEvento}", 1)
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
                mockMvc.perform(post("/api/compra/guardar/{idEvento}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.compraError").value("The purchase date can't be in the future"));
            }
        }

        @Nested
        class editarCompra{
            @Test
            void assertThatEditionCompraWithInvalidTicketFails() throws Exception {
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNumero_entradas(0);
                mockMvc.perform(put("/api/compra/{idCompra}/num_entradas/{value}", 2, 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.editionCompraError").value("The number of edited tickets has to be between 1 and 20"));
            }

            @Test
            void assertThatEditionCompraWithIdNotFoundFails() throws Exception {
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNumero_entradas(5);
                mockMvc.perform(put("/api/compra/{idCompra}/num_entradas/{value}", 2000, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.urlErrors").value("The compra with this id doesn't exist"));
            }

            @Test
            void asserThatEditionCompraWithIdLessThan1Fails() throws Exception {
                CompraDTO compraDTO = new CompraDTO();
                mockMvc.perform(put("/api/compra/{idCompra}/num_entradas/{value}", -5, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser mayor que 0"));
            }

            @Test
            void asserThatEditionCompraWithInvalidIdFails() throws Exception {
                CompraDTO compraDTO = new CompraDTO();
                mockMvc.perform(put("/api/compra/{idCompra}/num_entradas/{value}", "1.5", 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser un numero entero"));
            }

            @Test
            void asserThatEditionCompraWithIdMoreThan1000000Fails() throws Exception {
                CompraDTO compraDTO = new CompraDTO();
                mockMvc.perform(put("/api/compra/{idCompra}/num_entradas/{value}", 1000001, 6)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id no puede ser tan grande"));
            }
        }

        @Nested
        class eliminarCompra{
            @Test
            void assertThatDeleteCompraWithIdNotFoundFails() throws Exception{
                CompraDTO compraDTO = new CompraDTO();
                mockMvc.perform(delete("/api/compra/{idCompra}", 900)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.urlErrors").value("The compra with this id doesn't exist"));
            }
        }
    }

    @Nested
    class compraConPersonaTests{
        @Nested
        class crearCompraConPersona{
            @Test
            void assertThatCompraWithPersonaWithIdPersonaLessThan1Fails() throws Exception{
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("manu");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, -5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser mayor que 0"));
            }

            @Test
            void assertThatCompraWithPersonaWithIdPersonaMoreThan1000000Fails() throws Exception{
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("manu");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, 1000001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id no puede ser tan grande"));
            }

            @Test
            void assertThatCompraWithPersonaWithInvalidIdPersonaFails() throws Exception{
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("manu");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, "1.5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser un numero entero"));
            }

            @Test
            void assertThatCompraWithPersonaWithoutNameFails() throws Exception {
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente(null);
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.compraError").value("The client name can't be null"));
            }

            @Test
            void assertThatCompraWithPersonaWithInvalidNameFails() throws Exception{
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("-");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.compraError").value("The client name can only contain letters and spaces"));
            }

            @Test
            void assertThatCompraWithPersonaWithInvalidNameLengthFails() throws Exception{
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("lucialucialucialucialucialucialucialucialucialucialucia");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.compraError").value("The client name can't be longer than 50 characters"));
            }

            @Test
            void assertThatCompraWithPersonaWithoutTicketsFails() throws Exception {
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("Manu");
                compraDTO.setNumero_entradas(null);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.compraError").value("The number of tickets can't be null"));
            }

            @Test
            void assertThatCompraWithPersonaWithInvalidQuantityTicketsFails() throws Exception{
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("Manu");
                compraDTO.setNumero_entradas(0);
                compraDTO.setFecha_compra("2002-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.compraError").value("The number of tickets has to be between 1 and 20"));
            }

            @Test
            void assertThatCompraWithPersonaWithoutDateFails() throws Exception{
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("Manu");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra(null);
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.compraError").value("The purchase date can't be null"));
            }

            @Test
            void assertThatCompraWithPersonaWithInvalidDateFails() throws Exception{

                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("Manu");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("200-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.compraError").value("Invalid date format. Expected format is yyyy-MM-dd"));
            }

            @Test
            void assertThatCompraWithPersonaWithInvalidFutureDateFails() throws Exception{
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("Manu");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("5000-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.compraError").value("The purchase date can't be in the future"));
            }

            @Test
            void assertThatCompraWithPersonaThatCantSellFails() throws Exception{
                CompraDTO compraDTO = new CompraDTO();
                compraDTO.setNombre_cliente("Manu");
                compraDTO.setNumero_entradas(3);
                compraDTO.setFecha_compra("2024-02-15");
                mockMvc.perform(post("/api/compra/guardar/{idEvento}/{idPersona}", 1, 5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(compraDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.compraError").value("This person can't sell tickets"));
            }
        }
    }

    @Nested
    class eventoTests{

        @Nested
        class crearEvento{
            @Test
            void assertThatEventoWithoutNameFails() throws Exception {
                EventoDTO eventoDTO = new EventoDTO();
                eventoDTO.setNombre(null);
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
                eventoDTO.setNombre("******");
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
                eventoDTO.setNombre("Arenal");
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
                eventoDTO.setNombre("Arenal");
                eventoDTO.setFecha_evento("20-02-15");
                eventoDTO.setEmpresa_evento("cuatroochenta");
                mockMvc.perform(post("/api/evento/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventoDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.eventoError").value("Invalid date format. Expected format is yyyy-MM-dd"));
            }

            @Test
            void assertThatEventoWithoutEmpresaFails() throws Exception{
                EventoDTO eventoDTO = new EventoDTO();
                eventoDTO.setNombre("Estatus");
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
                eventoDTO.setNombre("Estatus");
                eventoDTO.setEmpresa_evento("ººº");
                mockMvc.perform(post("/api/evento/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventoDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.eventoError").value("The business name can only contain letters and spaces"));
            }
        }

        @Nested
        class editarEvento{
            @Test
            void assertThatEditionEventoWithIdLessThan1Fails() throws Exception {
                EventoDTO eventoDTO = new EventoDTO();
                mockMvc.perform(put("/api/evento/{idEvento}", -5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventoDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser mayor que 0"));
            }

            @Test
            void assertThatEditionEventoWithIdMoreThan1000000Fails() throws Exception{
                EventoDTO eventoDTO = new EventoDTO();
                mockMvc.perform(put("/api/evento/{idEvento}", 1000001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventoDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id no puede ser tan grande"));
            }

            @Test
            void assertThatEditionEventoWithInvalidIdFails() throws Exception {
                EventoDTO eventoDTO = new EventoDTO();
                mockMvc.perform(put("/api/evento/{idEvento}", "1.5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventoDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser un numero entero"));
            }

            @Test
            void assertThatEditionEventoWithInvalidNameFails() throws Exception {
                EventoDTO eventoDTO = new EventoDTO();
                eventoDTO.setNombre("*****");
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
            void assertThatEditionEventoWithIdNotFoundFails() throws Exception {
                EventoDTO eventoDTO = new EventoDTO();
                eventoDTO.setNombre("Arenal");
                mockMvc.perform(put("/api/evento/{idEvento}", 2000)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventoDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.urlErrors").value("The evento with this id doesn't exist"));
            }
        }

        @Nested
        class eliminarEvento{
            @Test
            void assertThatDeleteEventoWithIdNotFoundFails() throws Exception{
                EventoDTO eventoDTO = new EventoDTO();
                mockMvc.perform(delete("/api/evento/{idEvento}", 900)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(eventoDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.urlErrors").value("The evento with this id doesn't exist"));
            }
        }
    }

    @Nested
    class personaTests{

        @Nested
        class crearPersona{
            @Test
            void assertThatPersonaWithoutNameFails() throws Exception {
                PersonaDTO personaDTO = new PersonaDTO();
                personaDTO.setCorreo("mpons@gmail.com");
                mockMvc.perform(post("/api/persona/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.personaError").value("The persona name can't be empty"));
            }

            @Test
            void assertThatPersonaWithInvalidNameFails() throws Exception {
                PersonaDTO personaDTO = new PersonaDTO();
                personaDTO.setNombre("98");
                personaDTO.setCorreo("mpons@gmail.com");
                mockMvc.perform(post("/api/persona/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.personaError").value("The name of the person can only contain letters and spaces"));
            }

            @Test
            void assertThatPersonaWithoutCorreoFails() throws Exception {
                PersonaDTO personaDTO = new PersonaDTO();
                personaDTO.setNombre("Manu");
                mockMvc.perform(post("/api/persona/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.personaError").value("The persona email can't be empty"));
            }

            @Test
            void assertThatPersonaWithInvalidCorreoFails() throws Exception {
                PersonaDTO personaDTO = new PersonaDTO();
                personaDTO.setNombre("Manu");
                personaDTO.setCorreo("__________");
                mockMvc.perform(post("/api/persona/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.personaError").value("The email of the person is not valid"));
            }

            @Test
            void assertThatPersonaWithInvalidTelefonoFails() throws Exception {
                PersonaDTO personaDTO = new PersonaDTO();
                personaDTO.setNombre("Manu");
                personaDTO.setCorreo("mpons@gmail.com");
                personaDTO.setTelefono_persona("666666666*");
                mockMvc.perform(post("/api/persona/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.personaError").value("The phone number of the person is not valid. It must be exactly 9 digits long"));
            }
        }

        @Nested
        class editarPersona{
            @Test
            void assertThatEditionPersonaWithIdLessThan1Fails() throws Exception {
                PersonaDTO personaDTO = new PersonaDTO();
                mockMvc.perform(put("/api/persona/{idPersona}", -5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser mayor que 0"));
            }

            @Test
            void assertThatEditionPersonaWithIdMoreThan1000000Fails() throws Exception {
                PersonaDTO personaDTO = new PersonaDTO();
                mockMvc.perform(put("/api/persona/{idPersona}", 1000001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id no puede ser tan grande"));
            }

            @Test
            void assertThatEditionPersonaWithInvalidIdFails() throws Exception {
                PersonaDTO personaDTO = new PersonaDTO();
                mockMvc.perform(put("/api/persona/{idPersona}", "1.5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser un numero entero"));
            }

            @Test
            void assertThatEditionPersonaWithInvalidNameFails() throws Exception{
                PersonaDTO personaDTO = new PersonaDTO();
                personaDTO.setNombre("------");
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

            @Test
            void assertThatEditionPersonaWithIdNotFoundFails() throws Exception{
                PersonaDTO personaDTO = new PersonaDTO();
                personaDTO.setNombre("Manu");
                mockMvc.perform(put("/api/persona/{idPersona}", 2000)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.urlErrors").value("The persona with this id doesn't exist"));
            }

            @Test
            void assertThatEditionPersonaWithInvalidFechaBajaFails() throws Exception{
                PersonaDTO personaDTO = new PersonaDTO();
                personaDTO.setFecha_baja("15-05-15");
                mockMvc.perform(put("/api/persona/{idPersona}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.editionPersonaError").value("The date format is not right"));
            }
        }

        @Nested
        class eliminarPersona{
            @Test
            void assertThatDeletePersonaWithIdNotFoundFails() throws Exception{
                PersonaDTO personaDTO = new PersonaDTO();
                mockMvc.perform(delete("/api/persona/{idPersona}", 900)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personaDTO)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.urlErrors").value("The persona with this id doesn't exist"));
            }
        }


    }

    @Nested
    class relEventoPersona{
        @Nested
        class crearRelEventoPersona{

            @Test
            void assertThatCrearRelEventoPersonaThatAlreadyExistsFails() throws Exception {
                RelEventoPersona relEventoPersona = new RelEventoPersona();
                mockMvc.perform(post("/api/relEventoPersona/{idEvento}/{idPersona}", 1, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relEventoPersona)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.relEventoPersonaError").value("Relation evento persona already exits"));
            }

            @Test
            void assertThatCrearRelEventoPersonaWithIdEventoNotFoundFails() throws Exception {
                RelEventoPersona relEventoPersona = new RelEventoPersona();
                mockMvc.perform(post("/api/relEventoPersona/{idEvento}/{idPersona}", 500, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relEventoPersona)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.relEventoPersonaError").value("Evento id not found"));
            }

            @Test
            void assertThatCrearRelEventoPersonaWithIdPersonaNotFoundFails() throws Exception {
                RelEventoPersona relEventoPersona = new RelEventoPersona();
                mockMvc.perform(post("/api/relEventoPersona/{idEvento}/{idPersona}", 1, 500)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relEventoPersona)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.relEventoPersonaError").value("Persona id not found"));
            }

            @Test
            void assertThatCrearRelEventoPersonaWithIdEventoLessThan1Fails() throws Exception {
                RelEventoPersona relEventoPersona = new RelEventoPersona();
                mockMvc.perform(post("/api/relEventoPersona/{idEvento}/{idPersona}", -5, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relEventoPersona)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser mayor que 0"));
            }

            @Test
            void assertThatCrearRelEventoPersonaWithIdEventoMoreThan1000000Fails() throws Exception {
                RelEventoPersona relEventoPersona = new RelEventoPersona();
                mockMvc.perform(post("/api/relEventoPersona/{idEvento}/{idPersona}", 1000001, 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relEventoPersona)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id no puede ser tan grande"));
            }

            @Test
            void assertThatCrearRelEventoPersonaWithInvalidIdEventoFails() throws Exception {
                RelEventoPersona relEventoPersona = new RelEventoPersona();
                mockMvc.perform(post("/api/relEventoPersona/{idEvento}/{idPersona}", "1.5", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relEventoPersona)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser un numero entero"));
            }

            @Test
            void assertThatCrearRelEventoPersonaWithIdPersonaLessThan1Fails() throws Exception{
                RelEventoPersona relEventoPersona = new RelEventoPersona();
                mockMvc.perform(post("/api/relEventoPersona/{idEvento}/{idPersona}", 1, -5)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relEventoPersona)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser mayor que 0"));
            }

            @Test
            void assertThatCrearRelEventoPersonaWithIdPersonaMoreThan1000000Fails() throws Exception {
                RelEventoPersona relEventoPersona = new RelEventoPersona();
                mockMvc.perform(post("/api/relEventoPersona/{idEvento}/{idPersona}", 1, 1000001)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relEventoPersona)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id no puede ser tan grande"));
            }

            @Test
            void assertThatCrearRelEventoPersonaWithInvalidIdPersonaFails() throws Exception {
                RelEventoPersona relEventoPersona = new RelEventoPersona();
                mockMvc.perform(post("/api/relEventoPersona/{idEvento}/{idPersona}", 1, "1.5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relEventoPersona)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.idError").value("El id debe ser un numero entero"));
            }
        }

        @Nested
        class eliminarRelEventoPersona{

            @Test
            void assertThatEliminarRelEventoPersonaWithIdRelEventoPersonaNotFoundFails() throws Exception {
                RelEventoPersona relEventoPersona = new RelEventoPersona();
                mockMvc.perform(delete("/api/relEventoPersona/{idEventoPersona}", 500)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(relEventoPersona)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.relEventoPersonaError").value("Persona id not found"));
            }
        }
    }
}
