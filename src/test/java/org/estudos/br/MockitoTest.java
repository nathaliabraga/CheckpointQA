package org.estudos.br;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class MockitoTest {
    @Mock
    private HttpURLConnection connectionMock;

    private static final String JSON_RESPONSE = "{\"id\":32,\"sigla\":\"ES\",\"nome\":\"Espirito Santo\",\"regiao\":{\"id\":3,\"sigla\":\"SE\",\"nome\":\"Sudeste\"}}";

    @BeforeEach
    public void setup() throws IOException {
        MockitoAnnotations.openMocks(this);

        InputStream inputStream = new ByteArrayInputStream(JSON_RESPONSE.getBytes(StandardCharsets.UTF_8));
        when(connectionMock.getInputStream()).thenReturn(inputStream);
    }


    @Test
    @DisplayName("Valida as informações do estado")
    public void testRespostaEstado() throws IOException {
        try (MockedStatic consultaIBGE = Mockito.mockStatic(ConsultaIBGE.class)) {consultaIBGE.when(() -> ConsultaIBGE.consultarEstado(anyString()))
                .thenReturn(JSON_RESPONSE);

            String uf = "ES";

            String resposta = ConsultaIBGE.consultarEstado(uf);

            assertTrue(resposta.contains("Espirito Santo"), "Retorna Espirito Santo");
            assertTrue(resposta.contains("ES"), "Retorna Espirito Santo");
            assertTrue(resposta.contains("regiao"), "A resposta deve conter informações sobre a região do estado");
            assertTrue(resposta.contains("id"), "A resposta deve conter informações sobre o id do estado");
            assertTrue(resposta.contains("SE"), "A resposta deve conter a sigla da região");

            System.out.println(resposta);

        }
    }
}