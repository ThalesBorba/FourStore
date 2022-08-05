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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.fourcamp.fourstore.fourstore.constants.Constants.*;
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
        startClients();
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
        when(clientRepository.findByCpf(CPF)).thenReturn(Optional.ofNullable(client));

        MessageResponseDTO response = clientService.updateByCpf(CPF, 5, PIX);

        assertNotNull(response);
        assertEquals(MessageResponseDTO.class, response.getClass());
        assertEquals("Atualizado cliente com o cpf " + CPF, response.getMessage());
    }

    @Test
    void whenListAllShouldReturnAListOfClients() {
        when(clientRepository.findAll()).thenReturn(new ArrayList<>(List.of(client)));

        List<Client> response = clientService.listAll();

        assertNotNull(response);
        assertNotNull(response.get(INDEX));
        assertEquals(ArrayList.class, response.getClass());
        assertEquals(Client.class, response.get(INDEX).getClass());
        assertEquals(CPF, response.get(INDEX).getCpf());
        assertEquals(LONG_ID, response.get(INDEX).getId());
        assertEquals("Jose", response.get(INDEX).getName());
        assertEquals(6, response.get(INDEX).getPaymentMethod());
        assertEquals("Cash", response.get(INDEX).getPaymentData());

    }

    @Test
    void ShouldDeleteClientThenReturnAMessage() {
        when(clientRepository.findByCpf(CPF)).thenReturn(Optional.ofNullable(client));

        MessageResponseDTO response = clientService.delete(CPF);

        assertNotNull(response);
        assertEquals(MessageResponseDTO.class, response.getClass());
        assertEquals("Deletado cliente com o cpf " + CPF, response.getMessage());
    }

    @Test
    void shouldFindByCpfThenReturnAClient() {
        when(clientRepository.findByCpf(CPF)).thenReturn(Optional.ofNullable(client));

        Client response = clientService.findByCpf(CPF);

        assertNotNull(response);
        assertEquals(Client.class, response.getClass());
        assertEquals(CPF, response.getCpf());
        assertEquals(LONG_ID, response.getId());
        assertEquals("Jose", response.getName());
        assertEquals(6, response.getPaymentMethod());
        assertEquals("Cash", response.getPaymentData());
    }

    private void startClients() {
        client = new Client(LONG_ID, CPF, "Jose", 6,
                "Cash", null);
    }
}