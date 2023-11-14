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
        //given
        Competencia retornoEsperado = new Competencia(1, "Java")
        Candidato candidato = new Candidato(1, null, null, null, null, null, null, null, null, null)
        Map competenciaMap = [id: 1, nome: "Java"]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()

        Mockito.when(mapperUtils.converterMapToObject(Mockito.any(Map), Mockito.any(Object))).thenReturn(retornoEsperado)

        Mockito.when(candidatoCompetenciaDao.buscarCompetenciaCandidato(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(competenciaMap)

        //when
        Competencia retorno = candidatoCompetenciaService.buscarCompetenciaDoCandidato(candidato, retornoEsperado)

        //then
        assertEquals(retornoEsperado, retorno)
    }

    @Test
    void testListarCompetenciasDoCandidato() {
        //given
        List<Competencia> retornoEsperado = [new Competencia(1, "Java")]
        Candidato candidato = new Candidato(1, null, null, null, null, null, null, null, null, null)
        List<Map> competenciasMap = [[id: 1, nome: "Java"]]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()

        Mockito.when(mapperUtils.converterMapToObject(Mockito.any(Map), Mockito.any(Object))).thenReturn(retornoEsperado[0])

        Mockito.when(candidatoCompetenciaDao.listarCompetenciasPorCandidatoID(Mockito.any(Integer.class))).thenReturn(competenciasMap)

        //when
        List<Competencia> retorno = candidatoCompetenciaService.listarCompetenciasDoCandidato(candidato)

        //then
        assertEquals(retornoEsperado.sort(), retorno.sort())
    }

    @Test
    void testInserirCompetenciaParaCandidato() {
        //given
        List<Competencia> competencias = [
                new Competencia(1, "Java")
        ]
        Candidato candidato = new Candidato(1, null, null, null, null, null, null, null, null, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()

        Mockito.when(mapperUtils.converterMapToObject(Mockito.any(Map), Mockito.any(Object))).thenReturn(competencias[0])

        Mockito.when(candidatoCompetenciaDao.cadastrarCompetenciaCandidato(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(true)

        //when
        Boolean inserido = candidatoCompetenciaService.inserirCompetenciasParaCandidato(candidato.id, competencias.collect { it.id })

        //then
        assertTrue(inserido)
    }

    @Test
    void testExcluirCompetenciaParaCandidato() {
        //given
        List<Competencia> competencias = [
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy")
        ]
        Candidato candidato = new Candidato(1, null, null, null, null, null, null, null, null, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoCompetenciaDao.excluirCompetenciaCandidato(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(true)

        //when
        Boolean inserido = candidatoCompetenciaService.excluirCompetenciaDoCandidato(candidato.id, competencias.collect { it.id })

        //then
        assertTrue(inserido)
    }
}
