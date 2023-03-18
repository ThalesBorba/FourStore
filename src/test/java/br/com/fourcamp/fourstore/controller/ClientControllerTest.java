package br.com.fourcamp.fourstore.controller;

import br.com.fourcamp.fourstore.constants.Constants;
import br.com.fourcamp.fourstore.dto.request.CreateClientDTO;
import br.com.fourcamp.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.dto.response.ReturnClientDTO;
import br.com.fourcamp.fourstore.entities.Client;
import br.com.fourcamp.fourstore.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

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
        when(clientService.createClient(any())).thenReturn(MessageResponseDTO.builder().message("Criado cliente com o cpf " + Constants.CPF).build());

        ResponseEntity<MessageResponseDTO> response = clientController.createClient(createClientDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(MessageResponseDTO.class, response.getBody().getClass());
        Assertions.assertEquals("Criado cliente com o cpf " + Constants.CPF, response.getBody().getMessage());
    }

    @Test
    void whenListAllShouldReturnAListOfReturnClientDTOs() {
        when(clientService.listAll()).thenReturn(List.of(client));

        ResponseEntity<List<ReturnClientDTO>> response = clientController.listAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnClientDTO.class, response.getBody().get(Constants.INDEX).getClass());
        assertEquals(returnClientDTO, response.getBody().get(Constants.INDEX));
    }

    @Test
    void whenFindByCpfThenReturnAReturnClientDTO() {
        when(clientService.findByCpf(Constants.CPF)).thenReturn(client);

        ResponseEntity<ReturnClientDTO> response = clientController.findByCpf(Constants.CPF);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnClientDTO.class, response.getBody().getClass());
        assertEquals(returnClientDTO, response.getBody());
        Assertions.assertEquals(Constants.CPF, response.getBody().getCpf());
        assertEquals("Jose", response.getBody().getName());
    }

    @Test
    void whenUpdateByCpfThenReturnAResponse() {
        when(clientService.updateByCpf(Constants.CPF, 5, Constants.PIX)).thenReturn(MessageResponseDTO.builder().message(
                "Atualizado cliente com o cpf " + Constants.CPF).build());

        ResponseEntity<MessageResponseDTO> response = clientController.updatePaymentInfoByCpf(Constants.CPF, 5, Constants.PIX);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(MessageResponseDTO.class, response.getBody().getClass());
        Assertions.assertEquals("Atualizado cliente com o cpf " + Constants.CPF, response.getBody().getMessage());
    }

    @Test
    void shouldDeleteAClientByCpfThenReturnAResponse() {
        when(clientService.delete(Constants.CPF)).thenReturn(MessageResponseDTO.builder().message("Deletado cliente com o cpf "
                + Constants.CPF).build());

        ResponseEntity<MessageResponseDTO> response = clientController.deleteByCpf(Constants.CPF);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(MessageResponseDTO.class, response.getBody().getClass());
        Assertions.assertEquals("Deletado cliente com o cpf " + Constants.CPF, response.getBody().getMessage());
    }

    private void startClient () {
        client = new Client(Constants.UUID_ID, Constants.CPF, "Jose", 6, "Cash", null);
    }

    private void startCreateClientDTO () {
        createClientDTO = new CreateClientDTO(Constants.CPF, "Jose", 6, "Cash");
    }

    private void startReturnClientDTO () {
        returnClientDTO = new ReturnClientDTO(Constants.CPF, "Jose", null);
    }
}