package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Client;
import br.com.fourcamp.fourstore.fourstore.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static br.com.fourcamp.fourstore.fourstore.constants.Constants.CPF;
import static br.com.fourcamp.fourstore.fourstore.constants.Constants.LONG_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startClient();
    }

    @Test
    void shouldCreateAClientThenReturnAMessageDTO() {
        MessageResponseDTO response = clientService.createClient(client);

        assertNotNull(response);
        assertEquals(MessageResponseDTO.class, response.getClass());
        assertEquals("Criado cliente com o cpf " + CPF, response.getMessage());
    }

    @Test
    void shouldUpdateAClientByIdThenReturnAMessageDTO() {
        MessageResponseDTO response = clientService.updateById(client);

        assertNotNull(response);
        assertEquals(MessageResponseDTO.class, response.getClass());
        assertEquals("Criado cliente com o cpf " + CPF, response.getMessage());
    }

    @Test
    void listAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void shouldFindByCpfThenReturnAClient() {
        when(clientRepository.findByCpf(CPF)).thenReturn(Optional.ofNullable(client));

        Client response = clientService.findByCpf(CPF);

        assertNotNull(response);
        assertEquals(Client.class, response.getClass());
        assertEquals(CPF, client.getCpf());
        assertEquals(LONG_ID, client.getId());
        assertEquals("Jose", response.getName());
        assertEquals(6, response.getPaymentMethod());
        assertEquals("Cash", response.getPaymentData());
    }

    private void startClient() {
        client = new Client(LONG_ID, "562.738.720-31", "Jose", 6,
                "Cash", null);
    }
}