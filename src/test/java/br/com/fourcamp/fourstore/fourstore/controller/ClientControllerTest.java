package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateClientDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnClientDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Client;
import br.com.fourcamp.fourstore.fourstore.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static br.com.fourcamp.fourstore.fourstore.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClientControllerTest {

    @InjectMocks
    ClientController clientController;

    @Mock
    ClientService clientService;

    private Client client;
    private CreateClientDTO createClientDTO;
    private ReturnClientDTO returnClientDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startClient();
        startCreateClientDTO();
        startReturnClientDTO();
    }

    @Test
    void shouldCreateClientThenReturnAResponse() {
        when(clientService.createClient(any())).thenReturn(MessageResponseDTO.builder().message("Criado cliente com o cpf " + CPF).build());

        ResponseEntity<MessageResponseDTO> response = clientController.createClient(createClientDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(MessageResponseDTO.class, response.getBody().getClass());
        assertEquals("Criado cliente com o cpf " + CPF, response.getBody().getMessage());
    }

    @Test
    void whenListAllShouldReturnAListOfReturnClientDTOs() {
        when(clientService.listAll()).thenReturn(List.of(client));

        ResponseEntity<List<ReturnClientDTO>> response = clientController.listAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnClientDTO.class, response.getBody().get(INDEX).getClass());
        assertEquals(returnClientDTO, response.getBody().get(INDEX));
    }

    @Test
    void whenFindByCpfThenReturnAReturnClientDTO() {
        when(clientService.findByCpf(CPF)).thenReturn(client);

        ResponseEntity<ReturnClientDTO> response = clientController.findByCpf(CPF);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnClientDTO.class, response.getBody().getClass());
        assertEquals(returnClientDTO, response.getBody());
        assertEquals(CPF, response.getBody().getCpf());
        assertEquals("Jose", response.getBody().getName());
    }

    @Test
    void whenUpdateByCpfThenReturnAResponse() {
        when(clientService.updateByCpf(CPF, 5, PIX)).thenReturn(MessageResponseDTO.builder().message(
                "Atualizado cliente com o cpf " + CPF).build());

        ResponseEntity<MessageResponseDTO> response = clientController.updatePaymentInfoByCpf(CPF, 5, PIX);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(MessageResponseDTO.class, response.getBody().getClass());
        assertEquals("Atualizado cliente com o cpf " + CPF, response.getBody().getMessage());
    }

    @Test
    void shouldDeleteAClientByCpfThenReturnAResponse() {
        when(clientService.delete(CPF)).thenReturn(MessageResponseDTO.builder().message("Deletado cliente com o cpf "
                + CPF).build());

        ResponseEntity<MessageResponseDTO> response = clientController.deleteByCpf(CPF);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(MessageResponseDTO.class, response.getBody().getClass());
        assertEquals("Deletado cliente com o cpf " + CPF, response.getBody().getMessage());
    }

    private void startClient () {
        client = new Client(LONG_ID, CPF, "Jose", 6, "Cash", null);
    }

    private void startCreateClientDTO () {
        createClientDTO = new CreateClientDTO(CPF, "Jose", 6, "Cash");
    }

    private void startReturnClientDTO () {
        returnClientDTO = new ReturnClientDTO(CPF, "Jose", null);
    }
}