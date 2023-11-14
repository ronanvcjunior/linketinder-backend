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
        //given
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

        //when
        Boolean atualizado = vagaCompetenciaDao.cadastrarCompetenciaVaga(1, 1)

        //then
        assertTrue(atualizado)
    }

    @Test
    void testExcluirCompetenciaVaga() {
        //given
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

        //when
        Boolean resultado = vagaCompetenciaDao.excluirCompetenciaVaga(1, 1)

        //then
        assertTrue(resultado)
    }

    @Test
    void testBuscarCompetenciaVaga() {
        //given
        Map retornoEsperado = [id_vaga_competencia: 1]
        String sSQLEsperdao = """
            SELECT  
                c.id_competencia AS id, 
                c.nome
            FROM Vaga_Competencia vc
            INNER JOIN Competencia c ON c.id_competencia = vc.id_competencia
            WHERE vc.id_vaga = :idVaga
            AND vc.id_competencia = :idCompetencia
        """
        return

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

        //when
        Map retorno = vagaCompetenciaDao.buscarCompetenciaVaga(1, 1)

        //then
        assertEquals(retornoEsperado, retorno)
    }

    @Test
    void testListarCompetenciasPorVagaID() {
        //given
        List<Map> retornoEsperado = [[id_vaga_competencia: 1, nome: "Java"], [id_vaga_competencia: 2, nome: "Groovy"]]
        String sSQLEsperdao = """
            SELECT  
                c.id_competencia AS id, 
                c.nome
            FROM Vaga_Competencia vc
            LEFT JOIN Competencia c ON c.id_competencia = vc.id_competencia
            WHERE vc.id_vaga = :idVaga
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

        //when
        List<Map> retorno = vagaCompetenciaDao.listarCompetenciasPorVagaID(1)

        //then
        assertEquals(retornoEsperado, retorno)
    }
}
