package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.CompetenciaDao
import br.com.ronanjunior.linketinder.model.Candidato
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

import java.time.LocalDate

class CompetenciaServiceTest extends GroovyTestCase {
    @InjectMocks
    private CompetenciaService competenciaService

    @Mock
    private Conexao conexao

    @Mock
    private CompetenciaDao competenciaDao

    @Mock
    private MapperUtils mapperUtils

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void testListarTodasCompetencias() {
        //given
        List<Competencia> retornoEsperado = [
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy")
        ]
        List<Map> competenciasMap = [[id_competencia: 1, nome: "Java"], [id_competencia: 2, nome: "Groovy"]]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(competenciaDao.listarTodasCompetencias()).thenReturn(competenciasMap)

        //when
        List<Competencia> retorno = competenciaService.listarTodasCompetencias()

        //then
        assertEquals(retornoEsperado.sort(), retorno.sort())
    }
}
