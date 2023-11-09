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
    void testBuscarVagaCompetenciaPorId() {
        Competencia retornoEsperado = new Competencia(1, "Java")
        Vaga vaga = new Vaga(1, null, null, null, null, null, null)
        Map competenciaMap = [id_competencia: 1, nome: "Java"]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(vagaCompetenciaDao.buscarCompetenciaVaga(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(competenciaMap)

        Competencia retorno = vagaCompetenciaService.buscarCompetenciaDoVaga(vaga, retornoEsperado)

        assertEquals(retornoEsperado, retorno)
    }

    @Test
    void testListarCompetenciasDoVaga() {
        List<Competencia> retornoEsperado = [new Competencia(1, "Java"), new Competencia(2, "Groovy")]
        Vaga vaga = new Vaga(1, null, null, null, null, null, null)
        List<Map> competenciasMap = [[id_competencia: 1, nome: "Java"], [id_competencia: 2, nome: "Groovy"]]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(vagaCompetenciaDao.listarCompetenciasPorVagaID(Mockito.any(Integer.class))).thenReturn(competenciasMap)

        List<Competencia> retorno = vagaCompetenciaService.listarCompetenciasDoVaga(vaga)

        assertEquals(retornoEsperado.sort(), retorno.sort())
    }

    @Test
    void testInserirCompetenciaParaVaga() {
        Competencia competencia = new Competencia(1, "Java")
        Vaga vaga = new Vaga(1, null, null, null, null, null, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(vagaCompetenciaDao.cadastrarCompetenciaVaga(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(true)

        Boolean inserido = vagaCompetenciaService.inserirCompetenciaParaVaga(vaga, competencia)

        assertTrue(inserido)
    }

    @Test
    void testExcluirCompetenciaParaVaga() {
        Competencia competencia = new Competencia(1, "Java")
        Vaga vaga = new Vaga(1, null, null, null, null, null, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(vagaCompetenciaDao.excluirCompetenciaVaga(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(true)

        Boolean inserido = vagaCompetenciaService.excluirCompetenciaDoVaga(vaga, competencia)

        assertTrue(inserido)
    }
}