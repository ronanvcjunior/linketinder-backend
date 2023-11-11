package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.test.GroovyTestCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

import java.time.LocalDate

import static org.junit.jupiter.api.Assertions.assertEquals

class CandidatoCompetenciaDaoTest extends GroovyTestCase {
    @Mock
    private Conexao conexao

    @Mock
    private MapperUtils mapperUtils

    private CandidatoCompetenciaDao candidatoCompetenciaDao

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
        candidatoCompetenciaDao = new CandidatoCompetenciaDao(conexao, mapperUtils)
    }

    @Test
    void testCadastrarCompetenciaCandidato() {
        // Defina a consulta diretamente no teste
        String sSQLEsperdao = """
            INSERT INTO Candidato_Competencia (id_candidato, id_competencia)
            VALUES (:idCandidato, :idCompetencia)
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(conexao.executar(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, Integer> parametros = args[1]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
            assertEquals([idCandidato: 1, idCompetencia: 1], parametros)
        }

        // Chamar o método que está sendo testado
        Boolean atualizado = candidatoCompetenciaDao.cadastrarCompetenciaCandidato(1, 1)

        // Verificar se os resultados correspondem ao esperado
        assertTrue(atualizado)
    }

    @Test
    void testExcluirCompetenciaCandidato() {
        // Defina a consulta diretamente no teste
        String sSQLEsperdao = """
            DELETE FROM Candidato_Competencia
            WHERE id_candidato = :idCandidato
            AND id_competencia = :idCompetencia
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.executar(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([idCandidato: 1, idCompetencia: 1], parametros)
        }

        // Chamar o método que está sendo testado
        Boolean resultado = candidatoCompetenciaDao.excluirCompetenciaCandidato(1, 1)

        // Verificar se os resultados correspondem ao esperado
        assertTrue(resultado)
    }

    @Test
    void testBuscarCompetenciaCandidato() {
        // Defina a consulta diretamente no teste
        Map retornoEsperado = [id_candidato_competencia: 1]
        String sSQLEsperdao = """
            SELECT c.id_competencia, c.nome FROM Candidato_Competencia cc
            INNER JOIN Competencia c ON c.id_competencia = cc.id_competencia
            WHERE cc.id_candidato = :idCandidato
            AND cc.id_competencia = :idCompetencia
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(conexao.obterPrimeiraLinha(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
            assertEquals([idCandidato: 1, idCompetencia: 1], parametros)

            return retornoEsperado
        }

        // Chamar o método que está sendo testado
        Map retorno = candidatoCompetenciaDao.buscarCompetenciaCandidato(1, 1)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(retornoEsperado, retorno)
    }

    @Test
    void testListarCompetenciasPorCandidatoID() {
        // Defina a consulta diretamente no teste
        List<Map> retornoEsperado = [[id_candidato_competencia: 1, nome: "Java"], [id_candidato_competencia: 2, nome: "Groovy"]]
        String sSQLEsperdao = """
            SELECT con.id_competencia, con.nome FROM Candidato_Competencia cc
            LEFT JOIN Competencia con ON con.id_competencia = cc.id_competencia
            WHERE cc.id_candidato = :idCandidato
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(conexao.obterLinhas(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
            assertEquals([idCandidato: 1], parametros)

            return retornoEsperado
        }

        // Chamar o método que está sendo testado
        List<Map> retorno = candidatoCompetenciaDao.listarCompetenciasPorCandidatoID(1)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(retornoEsperado, retorno)
    }
}
