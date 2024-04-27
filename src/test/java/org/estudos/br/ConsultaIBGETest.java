package org.estudos.br;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class ConsultaIBGETest {
    private static final String ESTADOS_API_URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/";


    @Test
    @DisplayName("Teste para verificar se a consulta de estado retorna informações válidas")
    public void testConsultaEstadoInformacoesValidas() {
        // Arrange
        String uf = "SP"; // Estado de São Paulo

        // Act
        String resposta = null;
        try {
            resposta = ConsultaIBGE.consultarEstado(uf);
        } catch (IOException e) {
            fail("O teste falhou devido a uma exceção: " + e.getMessage());
        }

        // Assert
        assertTrue(resposta.contains("São Paulo"), "A resposta deve conter informações sobre São Paulo");
        assertTrue(resposta.contains("SP"), "A resposta deve conter a sigla do estado SP");
        assertTrue(resposta.contains("id"), "A resposta deve conter informações sobre o id do estado");
        assertTrue(resposta.contains("regiao"), "A resposta deve conter informações sobre a região do estado");
        assertTrue(resposta.contains("SE"), "A resposta deve conter a sigla da região");

    }

    @Test
    @DisplayName("Teste para resposta vazia em caso de erro de conexão ao consultar distrito")
    public void testRespostaVazia() {
        // Arrange
        int idDistrito = 00000000; // Distrito não existente

        // Act
        String retorno = null;
        try {
            retorno = ConsultaIBGE.consultarDistrito(idDistrito);
        } catch (IOException e) {
            retorno = "";
        }

        // Assert
        assertEquals("[]", retorno, "O retorno deve ser vazio");
    }

}