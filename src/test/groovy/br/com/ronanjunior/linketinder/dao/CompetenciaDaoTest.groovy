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
        // Defina a consulta diretamente no teste
        List<Map> resultadoEsperado = [[id_competencia: 1, nome: "Java"], [id_competencia: 2, nome: "Groovy"]]
        String sSQLEsperdao = """
            SELECT id_competencia, nome FROM Competencia
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(conexao.obterLinhas(Mockito.anyString())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)

            return resultadoEsperado
        }

        // Chamar o método que está sendo testado
        List<Map> competencias = competenciaDao.listarTodasCompetencias()

        // Verificar se os resultados correspondem ao esperado
        assertEquals(resultadoEsperado, competencias)
    }
}
