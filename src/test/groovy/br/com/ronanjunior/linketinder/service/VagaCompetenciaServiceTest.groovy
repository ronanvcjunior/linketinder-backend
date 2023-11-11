package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.VagaCompetenciaDao
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.test.GroovyTestCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class VagaCompetenciaServiceTest extends GroovyTestCase {
    @InjectMocks
    private VagaCompetenciaService vagaCompetenciaService

    @Mock
    private Conexao conexao

    @Mock
    private VagaCompetenciaDao vagaCompetenciaDao

    @Mock
    private MapperUtils mapperUtils

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void testBuscarCompetenciaDaVaga() {
        //given
        Competencia retornoEsperado = new Competencia(1, "Java")
        Vaga vaga = new Vaga(1, null, null, null, null, null, null)
        Map competenciaMap = [id_competencia: 1, nome: "Java"]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(vagaCompetenciaDao.buscarCompetenciaVaga(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(competenciaMap)

        //when
        Competencia retorno = vagaCompetenciaService.buscarCompetenciaDaVaga(vaga.id, retornoEsperado.id)

        //then
        assertEquals(retornoEsperado, retorno)
    }

    @Test
    void testListarCompetenciasDoVaga() {
        //given
        List<Competencia> retornoEsperado = [new Competencia(1, "Java"), new Competencia(2, "Groovy")]
        Vaga vaga = new Vaga(1, null, null, null, null, null, null)
        List<Map> competenciasMap = [[id_competencia: 1, nome: "Java"], [id_competencia: 2, nome: "Groovy"]]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(vagaCompetenciaDao.listarCompetenciasPorVagaID(Mockito.any(Integer.class))).thenReturn(competenciasMap)

        //when
        List<Competencia> retorno = vagaCompetenciaService.listarCompetenciasDoVaga(vaga.id)

        //then
        assertEquals(retornoEsperado.sort(), retorno.sort())
    }

    @Test
    void testInserirCompetenciasParaVaga() {
        //given
        List<Competencia> competencias = [
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy")
        ]
        Vaga vaga = new Vaga(1, null, null, null, null, null, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(vagaCompetenciaDao.cadastrarCompetenciaVaga(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(true)

        //when
        Boolean inserido = vagaCompetenciaService.inserirCompetenciasParaVaga(vaga.id, competencias.collect { it.id })

        //then
        assertTrue(inserido)
    }

    @Test
    void testExcluirCompetenciasParaVaga() {
        //given
        List<Competencia> competencias = [
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy")
        ]
        Vaga vaga = new Vaga(1, null, null, null, null, null, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(vagaCompetenciaDao.excluirCompetenciaVaga(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(true)

        //when
        Boolean inserido = vagaCompetenciaService.excluirCompetenciasDoVaga(vaga.id, competencias.collect { it.id })

        //then
        assertTrue(inserido)
    }
}
