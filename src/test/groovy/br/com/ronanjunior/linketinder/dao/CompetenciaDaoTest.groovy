package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.test.GroovyTestCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import static org.junit.jupiter.api.Assertions.assertEquals

class CompetenciaDaoTest extends GroovyTestCase {
    @Mock
    private Conexao conexao

    @Mock
    private MapperUtils mapperUtils

    private CompetenciaDao competenciaDao

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
        competenciaDao = new CompetenciaDao(conexao, mapperUtils)
    }

    @Test
    void testListarCompetenciasParaEmpresa() {
        //given
        List<Map> resultadoEsperado = [[id_competencia: 1, nome: "Java"], [id_competencia: 2, nome: "Groovy"]]
        String sSQLEsperdao = """
            SELECT 
                id_competencia id, 
                nome 
            FROM Competencia
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(conexao.obterLinhas(Mockito.anyString())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)

            return resultadoEsperado
        }

        //when
        List<Map> competencias = competenciaDao.listarTodasCompetencias()

        //then
        assertEquals(resultadoEsperado, competencias)
    }
}
