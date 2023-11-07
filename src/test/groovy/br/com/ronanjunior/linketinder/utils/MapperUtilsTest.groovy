package br.com.ronanjunior.linketinder.utils

import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.model.Empresa
import groovy.test.GroovyTestCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import java.time.LocalDate

class MapperUtilsTest extends GroovyTestCase {

    private MapperUtils mapperUtils

    @BeforeEach
    void setUp() {
        mapperUtils = new MapperUtils()
    }

    @Test
    void testConverterObjectToMapParaConta() {
        Map dados = [
                "id": 1,
                "email": "teste@gmail.com",
                "senha": "teste",
                "candidato": [
                        "id": 1,
                        "nome": "Candi",
                        "sobrenome": "Dato",
                        "cpf": "12345678901",
                        "dataNascimento": "1970-01-01",
                        "pais": "BR",
                        "estado": "GO",
                        "cep": "12522030",
                        "descricao": "",
                        "competencias": [
                                ["id": 1, "nome": "Java"],
                                ["id": 2, "nome": "Groovy"]
                        ]
                ],
                "empresa": [
                        "id": 1,
                        "nome": "empresa",
                        "cnpj": "01234567890123",
                        "pais": "BR",
                        "cep": "13245678",
                        "descricao": ""
                ]
        ]

        Map resultadoEsperado = [
                "id": 1,
                "email": "teste@gmail.com",
                "senha": "teste",
                "candidato": dados["candidato"],
                "empresa": dados["empresa"]
        ]

        Competencia competencia1 = new Competencia(1, "Java")
        Competencia competencia2 = new Competencia(2, "Groovy")

        Candidato candidato = new Candidato(1, "Candi", "Dato", "12345678901", LocalDate.of(1970, 1, 1), "BR", "GO", "12522030", "", [competencia1, competencia2])
        Empresa empresa = new Empresa(1, "empresa", "01234567890123", "BR", "13245678", "")
        Conta conta = new Conta(1, "teste@gmail.com", "teste", candidato, empresa)

        Map resultado = mapperUtils.converterObjectToMap(conta)

        assertEquals(resultadoEsperado.toString(), resultado.toString())
    }

}
