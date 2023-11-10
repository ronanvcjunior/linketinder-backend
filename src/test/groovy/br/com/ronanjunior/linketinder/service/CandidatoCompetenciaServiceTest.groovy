package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.CandidatoCompetenciaDao
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.test.GroovyTestCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CandidatoCompetenciaServiceTest extends GroovyTestCase {
    @InjectMocks
    private CandidatoCompetenciaService candidatoCompetenciaService

    @Mock
    private Conexao conexao

    @Mock
    private CandidatoCompetenciaDao candidatoCompetenciaDao

    @Mock
    private MapperUtils mapperUtils

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void testBuscarCandidatoCompetenciaPorId() {
        Competencia retornoEsperado = new Competencia(1, "Java")
        Candidato candidato = new Candidato(1, null, null, null, null, null, null, null, null, null)
        Map competenciaMap = [id_competencia: 1, nome: "Java"]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoCompetenciaDao.buscarCompetenciaCandidato(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(competenciaMap)

        Competencia retorno = candidatoCompetenciaService.buscarCompetenciaDoCandidato(candidato, retornoEsperado)

        assertEquals(retornoEsperado, retorno)
    }

    @Test
    void testListarCompetenciasDoCandidato() {
        List<Competencia> retornoEsperado = [new Competencia(1, "Java"), new Competencia(2, "Groovy")]
        Candidato candidato = new Candidato(1, null, null, null, null, null, null, null, null, null)
        List<Map> competenciasMap = [[id_competencia: 1, nome: "Java"], [id_competencia: 2, nome: "Groovy"]]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoCompetenciaDao.listarCompetenciasPorCandidatoID(Mockito.any(Integer.class))).thenReturn(competenciasMap)

        List<Competencia> retorno = candidatoCompetenciaService.listarCompetenciasDoCandidato(candidato)

        assertEquals(retornoEsperado.sort(), retorno.sort())
    }

    @Test
    void testInserirCompetenciaParaCandidato() {
        List<Competencia> competencias = [
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy")
        ]
        Candidato candidato = new Candidato(1, null, null, null, null, null, null, null, null, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoCompetenciaDao.cadastrarCompetenciaCandidato(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(true)

        Boolean inserido = candidatoCompetenciaService.inserirCompetenciasParaCandidato(candidato.id, competencias.collect { it.id })

        assertTrue(inserido)
    }

    @Test
    void testExcluirCompetenciaParaCandidato() {
        List<Competencia> competencias = [
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy")
        ]
        Candidato candidato = new Candidato(1, null, null, null, null, null, null, null, null, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoCompetenciaDao.excluirCompetenciaCandidato(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(true)

        Boolean inserido = candidatoCompetenciaService.excluirCompetenciaDoCandidato(candidato.id, competencias.collect { it.id })

        assertTrue(inserido)
    }
}
