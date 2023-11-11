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
        //given
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
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoDao.listarCandidatosParaEmpresa(Mockito.any(Integer))).thenReturn(candidatoMap)
        Mockito.when(candidatoCompetenciaService.montarListaCompetenciaParaCandidato(Mockito.any(Integer))).thenReturn([])

        //when
        List<CandidatoListaDaEmpresaDto> retorno = candidatoService.listarCandidatosParaEmpresa(empresa.id)

        //then
        assertEquals(retornoEsperado.sort(), retorno.sort())
    }

    @Test
    void testAlterarCandidato() {
        //given
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoDao.atualizarCandidato(Mockito.any(Candidato.class))).thenReturn(true)

        //when
        Boolean atualizado = candidatoService.alterarCandidato(candidato)

        //then
        assertTrue(atualizado)
    }

    @Test
    void testVerificaraExistenciaCandidatoPorCpfEncontrado() {
        //given
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
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoDao.buscarCandidatoPorCpf(Mockito.any(String.class))).thenReturn(candidatoMap)
        Mockito.when(candidatoCompetenciaService.montarListaCompetenciaParaCandidato(Mockito.any(Integer))).thenReturn([])

        //when
        Boolean encontrado = candidatoService.verificaraExistenciaCandidatoPorCpf(candidatoEsperado.cpf)

        //then
        assertTrue(encontrado)
    }

    @Test
    void testVerificaraExistenciaCandidatoPorCpfNaoEncontrado() {
        //given
        Candidato candidatoEsperado = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoDao.buscarCandidatoPorCpf(Mockito.any(String.class))).thenReturn([:])
        Mockito.when(candidatoCompetenciaService.montarListaCompetenciaParaCandidato(Mockito.any(Integer))).thenReturn([])

        //when
        Boolean encontrado = candidatoService.verificaraExistenciaCandidatoPorCpf(candidatoEsperado.cpf)

        //then
        assertFalse(encontrado)
    }

    @Test
    void testBuscarCandidatoPorId() {
        //given
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
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoDao.buscarCandidatoPorId(Mockito.any(Integer.class))).thenReturn(candidatoMap)
        Mockito.when(candidatoCompetenciaService.montarListaCompetenciaParaCandidato(Mockito.any(Integer))).thenReturn([])

        //when
        Candidato candidato = candidatoService.buscarCandidatoPorId(candidatoEsperado.id)

        //then
        assertEquals(candidatoEsperado, candidato)
    }

    @Test
    void testExcluirCandidato() {
        //given
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoDao.excluirCandidato(Mockito.any(Integer.class))).thenReturn(true)

        //when
        Boolean excluido = candidatoService.excluirCandidato(candidato)

        //then
        assertTrue(excluido)
    }

    @Test
    void testInserirCandidato() {
        //given
        Candidato candidatoEsperado = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoDao.inserirCandidato(Mockito.any(Candidato.class))).thenReturn(1)

        //when
        Candidato candidato = candidatoService.inserirCandidato(candidatoEsperado)

        //then
        assertEquals(candidatoEsperado, candidato)
    }
}
