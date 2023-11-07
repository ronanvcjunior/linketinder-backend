package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.model.Match
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.test.GroovyTestCase

import java.time.LocalDate

import static org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchDaoTest extends GroovyTestCase {
    @Mock
    private Conexao conexao

    @Mock
    private MapperUtils mapperUtils

    private MatchDao matchDao

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
        matchDao = new MatchDao(conexao, mapperUtils)
    }

    @Test
    void testInserirMatch() {
        // Defina a consulta diretamente no teste
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Vaga vaga = new Vaga(1, "Vaga", "", "GO", "Goiânia", empresa, [])
        Match match = new Match(null, LocalDate.now(), null, candidato, vaga)
        String sSQLEsperdao = """
            INSERT INTO Match (id_candidato, id_vaga, data_curtida_candidato, data_curtida_vaga)
            VALUES (:idCandidato, :idVaga, :dataCurtidaCandidato, :dataCurtidaVaga)
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Map parametrosEsperados = [
                idCandidato: match.candidato.id,
                idVaga: match.vaga.id,
                dataCurtidaCandidato: match.dataCurtidaCandidato,
                dataCurtidaVaga: match.dataCurtidaVaga
        ]

        Mockito.doNothing().when(conexao).abrirConexao()

        Mockito.when(conexao.inserir(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
            assertEquals(parametrosEsperados, parametros)

            return 1
        }

        // Chamar o método que está sendo testado
        Integer idMatch = matchDao.inserirMatch(match)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(1, idMatch)
    }

    @Test
    void testAtualizarMatch() {
        // Defina a consulta diretamente no teste
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Vaga vaga = new Vaga(1, "Vaga", "", "GO", "Goiânia", empresa, [])
        Match match = new Match(1, LocalDate.now(), LocalDate.now(), candidato, vaga)
        String sSQLEsperdao = """
                UPDATE Match
                SET data_curtida_candidato = :dataCurtidaCandidato,
                    data_curtida_vaga = :dataCurtidaVaga
                WHERE id_match = :idMatch
            """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Map parametrosEsperados = [
                dataCurtidaCandidato: match.dataCurtidaCandidato,
                dataCurtidaVaga: match.dataCurtidaVaga,
                idMatch: match.id
        ]

        Mockito.doNothing().when(conexao).abrirConexao()

        Mockito.when(conexao.executar(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
            assertEquals(parametrosEsperados, parametros)
        }

        // Chamar o método que está sendo testado
        Boolean atualizado = matchDao.atualizarMatch(match)

        // Verificar se os resultados correspondem ao esperado
        assertTrue(atualizado)
    }

    @Test
    void testBuscarMatchPorIdCandidatoIdVaga() {
        // Defina a consulta diretamente no teste
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Vaga vaga = new Vaga(1, "Vaga", "", "GO", "Goiânia", empresa, [])
        Match match = new Match(null, null, null, candidato, vaga)
        Map retornoEsperado = [id_match: 1, data_curtida_candidato: "2023-11-02", data_curtida_vaga: "2023-11-07", id_candidato: 1, id_vaga: 1]
        String sSQLEsperdao = """
            SELECT * FROM Match
            WHERE id_candidato = :idCandidato
            AND id_vaga = :idVaga
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Map parametrosEsperados = [
                idCandidato: match.candidato.id,
                idVaga: match.vaga.id,
        ]

        Mockito.doNothing().when(conexao).abrirConexao()

        Mockito.when(conexao.obterPrimeiraLinha(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
            assertEquals(parametrosEsperados, parametros)

            return retornoEsperado
        }

        // Chamar o método que está sendo testado
        Map retorno = matchDao.buscarMatchPorIdCandidatoIdVaga(match)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(retornoEsperado, retorno)
    }
}
