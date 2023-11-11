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

class VagaCompetenciaDaoTest extends GroovyTestCase {
    @Mock
    private Conexao conexao

    @Mock
    private MapperUtils mapperUtils

    private VagaCompetenciaDao vagaCompetenciaDao

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
        vagaCompetenciaDao = new VagaCompetenciaDao(conexao, mapperUtils)
    }

    @Test
    void testCadastrarCompetenciaVaga() {
        // Defina a consulta diretamente no teste
        String sSQLEsperdao = """
            INSERT INTO Vaga_Competencia (id_vaga, id_competencia)
            VALUES (:idVaga, :idCompetencia)
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(conexao.executar(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, Integer> parametros = args[1]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
            assertEquals([idVaga: 1, idCompetencia: 1], parametros)
        }

        // Chamar o método que está sendo testado
        Boolean atualizado = vagaCompetenciaDao.cadastrarCompetenciaVaga(1, 1)

        // Verificar se os resultados correspondem ao esperado
        assertTrue(atualizado)
    }

    @Test
    void testExcluirCompetenciaVaga() {
        // Defina a consulta diretamente no teste
        String sSQLEsperdao = """
            DELETE FROM Vaga_Competencia
            WHERE id_vaga = :idVaga
            AND id_competencia = :idCompetencia
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.executar(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([idVaga: 1, idCompetencia: 1], parametros)
        }

        // Chamar o método que está sendo testado
        Boolean resultado = vagaCompetenciaDao.excluirCompetenciaVaga(1, 1)

        // Verificar se os resultados correspondem ao esperado
        assertTrue(resultado)
    }

    @Test
    void testBuscarCompetenciaVaga() {
        // Defina a consulta diretamente no teste
        Map retornoEsperado = [id_vaga_competencia: 1]
        String sSQLEsperdao = """
            SELECT c.id_competencia, c.nome FROM Vaga_Competencia vc
            INNER JOIN Competencia c ON c.id_competencia = vc.id_competencia
            WHERE vc.id_vaga = :idVaga
            AND vc.id_competencia = :idCompetencia
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(conexao.obterPrimeiraLinha(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
            assertEquals([idVaga: 1, idCompetencia: 1], parametros)

            return retornoEsperado
        }

        // Chamar o método que está sendo testado
        Map retorno = vagaCompetenciaDao.buscarCompetenciaVaga(1, 1)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(retornoEsperado, retorno)
    }

    @Test
    void testListarCompetenciasPorVagaID() {
        // Defina a consulta diretamente no teste
        List<Map> retornoEsperado = [[id_vaga_competencia: 1, nome: "Java"], [id_vaga_competencia: 2, nome: "Groovy"]]
        String sSQLEsperdao = """
            SELECT con.id_competencia, con.nome FROM Vaga_Competencia cc
            LEFT JOIN Competencia con ON con.id_competencia = cc.id_competencia
            WHERE cc.id_vaga = :idVaga
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(conexao.obterLinhas(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
            assertEquals([idVaga: 1], parametros)

            return retornoEsperado
        }

        // Chamar o método que está sendo testado
        List<Map> retorno = vagaCompetenciaDao.listarCompetenciasPorVagaID(1)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(retornoEsperado, retorno)
    }
}
