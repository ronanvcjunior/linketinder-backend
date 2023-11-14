package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.model.Match
import br.com.ronanjunior.linketinder.model.Vaga
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
import static org.junit.jupiter.api.Assertions.assertEquals

class VagaDaoTest extends GroovyTestCase {
    @Mock
    private Conexao conexao

    @Mock
    private MapperUtils mapperUtils

    private VagaDao vagaDao

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
        vagaDao = new VagaDao(conexao, mapperUtils)
    }

    @Test
    void testListarVagasParaCandidato() {
        //given
        List<Map> resultadoEsperado = [[id_vaga: 1, nome: "Vaga 1", descricao: "", empresa: "Anônimo", match: 0], [id_vaga: 2, nome: "Vaga 2", descricao: "", empresa: "Empresa", match: 1]]
        String sSQLEsperdao = """
                SELECT
                    va.id_vaga AS id,
                    va.nome,
                    va.descricao,
                    CASE
                        WHEN MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) = 1 THEN em.nome
                        ELSE 'Anônimo'
                    END AS nomeEmpresa,
                    MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) AS match
                FROM Vaga va
                LEFT JOIN (
                    SELECT * FROM Match WHERE id_candidato = :idCandidato
                ) ma ON ma.id_vaga = va.id_vaga
                INNER JOIN Empresa em ON em.id_empresa = va.id_empresa
                GROUP BY va.id_vaga, em.nome
                ORDER BY match DESC, va.nome ASC, va.id_vaga ASC
            """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(conexao.obterLinhas(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, Integer> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([idCandidato: 1], parametros)

            return resultadoEsperado
        }

        //when
        List<Map> candidatos = vagaDao.listarVagasParaCandidato(1)

        //then
        assertEquals(resultadoEsperado, candidatos)
    }

    @Test
    void testBuscarVagasPorIdEmpresa() {
        //given
        List<Map> resultadoEsperado = [[id_vaga: 1, nome: "Vaga 1", descricao: "", id_empresa: 1], [id_vaga: 2, nome: "Vaga 2", descricao: "", id_empresa: 2]]
        String sSQLEsperdao = """
                SELECT 
                    id_vaga AS id,
                    nome,
                    descricao,
                    estado,
                    cidade,
                    id_empresa
                FROM Vaga
                WHERE id_empresa = :idEmpresa
            """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(conexao.obterLinhas(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, Integer> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([idEmpresa: 1], parametros)

            return resultadoEsperado
        }

        //when
        List<Map> candidatos = vagaDao.buscarVagasPorIdEmpresa(1)

        //then
        assertEquals(resultadoEsperado, candidatos)
    }

    @Test
    void testInserirVaga() {
        //given
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Vaga vaga = new Vaga(null, "Vaga 1", "", "GO", "Goiânia", empresa, [])
        String sSQLEsperdao = """
            INSERT INTO Vaga (nome, descricao, estado, cidade, id_empresa)
            VALUES (:nome, :descricao, :estado, :cidade, :idEmpresa)
        """

        List<String> inserirEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Map<String, Object> parametrosEsperado = [
                nome: vaga.nome,
                descricao: vaga.descricao,
                estado: vaga.estado,
                cidade: vaga.cidade,
                idEmpresa: vaga.empresa.id
        ]

        Mockito.when(conexao.inserir(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> inserir = sSQL.split("\n").collect { it.trim()}

            assertEquals(inserirEsperado, inserir)
            assertEquals(parametrosEsperado, parametros)

            return 1
        }

        //when
        Integer idVaga = vagaDao.inserirVaga(vaga)

        //then
        assertEquals(1, idVaga)
    }

    @Test
    void testAtualizarVaga() {
        //given
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Vaga vaga = new Vaga(1, "Vaga", "", "GO", "Goiânia", empresa, [])

        String sSQLEsperdao = """
                UPDATE Vaga
                SET nome = :nome,
                    descricao = :descricao,
                    estado = :estado,
                    cidade = :cidade
                WHERE id_vaga = :idVaga
            """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(mapperUtils.converterObjectToMap(Mockito.any(Vaga.class))).thenReturn([
                "id"       : null,
                "nome"     : "Vaga",
                "descricao"     : "",
                "estado"     : "GO",
                "cidade"     : "Goiânia",
                "cep"      : "12345678",
                "empresa": []
        ])

        Mockito.when(conexao.executar(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
        }

        //when
        Boolean atualizado = vagaDao.atualizarVaga(vaga)

        //then
        assertTrue(atualizado)
    }

    @Test
    void testExcluirVaga() {
        //given
        String sSQLEsperdao = """
            DELETE FROM Vaga
            WHERE id_vaga = :idVaga
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.executar(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([idVaga: 1], parametros)
        }

        //when
        Boolean resultado = vagaDao.excluirVaga(1)

        //then
        assertTrue(resultado)
    }

    @Test
    void testBuscarVagaPorId() {
        //given
        Map resultadoEsperado = [id_vaga: 1, nome: "Vaga", descricao: "", estado: "SP", cidade: "SP", id_empresa: 1]
        String sSQLEsperdao = """
            SELECT 
                    id_vaga AS id,
                    nome,
                    descricao,
                    estado,
                    cidade,
                    id_empresa
                FROM Vaga
            WHERE id_vaga = :idVaga
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.obterPrimeiraLinha(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([idVaga: 1], parametros)

            return resultadoEsperado
        }

        //when
        Map resultado = vagaDao.buscarVagaPorId(1)

        //then
        assertEquals(resultadoEsperado, resultado)
    }
}
