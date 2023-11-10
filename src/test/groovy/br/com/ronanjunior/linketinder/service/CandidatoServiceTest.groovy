package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.CandidatoDao
import br.com.ronanjunior.linketinder.dto.CandidatoListaDaEmpresaDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.test.GroovyTestCase
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks

import java.time.LocalDate

import static org.junit.jupiter.api.Assertions.*


import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CandidatoServiceTest extends GroovyTestCase {
    @InjectMocks
    private CandidatoService candidatoService

    @Mock
    private Conexao conexao

    @Mock
    private CandidatoDao candidatoDao

    @Mock
    private CandidatoCompetenciaService candidatoCompetenciaService

    @Mock
    private MapperUtils mapperUtils

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void testListarCandidatosParaEmpresa() {
        List<CandidatoListaDaEmpresaDto> retornoEsperado = [
                new CandidatoListaDaEmpresaDto(1, "Anônimo", []),
                new CandidatoListaDaEmpresaDto(2, "Ana Julia", [])
        ]
        List<Map> candidatoMap = [
                [id_candidato: 1, nome_completo: "Anônimo", match: 0],
                [id_candidato: 2, nome_completo: "Ana Julia", match: 1]
        ]
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(candidatoDao.listarCandidatosParaEmpresa(Mockito.any(Integer))).thenReturn(candidatoMap)
        Mockito.when(candidatoCompetenciaService.montarListaCompetenciaParaCandidato(Mockito.any(Integer))).thenReturn([])

        List<CandidatoListaDaEmpresaDto> retorno = candidatoService.listarCandidatosParaEmpresa(empresa.id)

        assertEquals(retornoEsperado.sort(), retorno.sort())
    }

    @Test
    void testAlterarCandidato() {
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(candidatoDao.atualizarCandidato(Mockito.any(Candidato.class))).thenReturn(true)

        Boolean atualizado = candidatoService.alterarCandidato(candidato)

        assertTrue(atualizado)
    }

    @Test
    void testVerificaraExistenciaCandidatoPorCpfEncontrado() {
        Candidato candidatoEsperado = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Map candidatoMap = [
                id_candidato: 1,
                nome: "Candi",
                sobrenome: "Dato",
                cpf: "01234567890",
                data_nascimento: "1970-01-01",
                pais: "Brasil",
                estado: "GO",
                cep: "12345678",
                descricao: ""
        ]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(candidatoDao.buscarCandidatoPorCpf(Mockito.any(String.class))).thenReturn(candidatoMap)
        Mockito.when(candidatoCompetenciaService.montarListaCompetenciaParaCandidato(Mockito.any(Integer))).thenReturn([])

        Boolean encontrado = candidatoService.verificaraExistenciaCandidatoPorCpf(candidatoEsperado.cpf)

        assertTrue(encontrado)
    }

    @Test
    void testVerificaraExistenciaCandidatoPorCpfNaoEncontrado() {
        Candidato candidatoEsperado = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(candidatoDao.buscarCandidatoPorCpf(Mockito.any(String.class))).thenReturn([:])
        Mockito.when(candidatoCompetenciaService.montarListaCompetenciaParaCandidato(Mockito.any(Integer))).thenReturn([])

        Boolean encontrado = candidatoService.verificaraExistenciaCandidatoPorCpf(candidatoEsperado.cpf)

        assertFalse(encontrado)
    }

    @Test
    void testBuscarCandidatoPorId() {
        Candidato candidatoEsperado = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Map candidatoMap = [
                id_candidato: 1,
                nome: "Candi",
                sobrenome: "Dato",
                cpf: "01234567890",
                data_nascimento: "1970-01-01",
                pais: "Brasil",
                estado: "GO",
                cep: "12345678",
                descricao: ""
        ]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(candidatoDao.buscarCandidatoPorId(Mockito.any(Integer.class))).thenReturn(candidatoMap)
        Mockito.when(candidatoCompetenciaService.montarListaCompetenciaParaCandidato(Mockito.any(Integer))).thenReturn([])

        Candidato candidato = candidatoService.buscarCandidatoPorId(candidatoEsperado.id)

        assertEquals(candidatoEsperado, candidato)
    }

    @Test
    void testExcluirCandidato() {
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(candidatoDao.excluirCandidato(Mockito.any(Integer.class))).thenReturn(true)

        Boolean excluido = candidatoService.excluirCandidato(candidato)

        assertTrue(excluido)
    }

    @Test
    void testInserirCandidato() {
        Candidato candidatoEsperado = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(candidatoDao.inserirCandidato(Mockito.any(Candidato.class))).thenReturn(1)

        Candidato candidato = candidatoService.inserirCandidato(candidatoEsperado)

        assertEquals(candidatoEsperado, candidato)
    }
}
