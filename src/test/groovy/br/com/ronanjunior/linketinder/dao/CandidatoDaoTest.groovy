package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Empresa
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

class CandidatoDaoTest extends GroovyTestCase {
    @Mock
    private Conexao conexao

    @Mock
    private MapperUtils mapperUtils

    private CandidatoDao candidatoDao

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
        candidatoDao = new CandidatoDao(conexao, mapperUtils)
    }

    @Test
    void testListarCandidatosParaEmpresa() {
        // Defina a consulta diretamente no teste
        List<Map> resultadoEsperado = [[id_candidato: 1, nome: "Anônimo", match: 1], [id_candidato: 2, nome: "Maria Roberta", match: 0]]
        String sSQLEsperdao = """
                SELECT
                    can.id_candidato,
                    CASE
                        WHEN MAX(
                            CASE 
                                WHEN data_curtida_candidato IS NOT NULL 
                                AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END
                        ) = 1 THEN CONCAT(can.nome, ' ', can.sobrenome)
                        ELSE 'Anônimo'
                    END AS nome_completo,
                    MAX(
                        CASE 
                            WHEN data_curtida_candidato IS NOT NULL 
                            AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END
                    ) AS match
                FROM Candidato can
                LEFT JOIN (
                    SELECT * FROM Match WHERE id_vaga IN (
                        SELECT va.id_vaga FROM Vaga va
                        RIGHT JOIN Empresa em ON em.id_empresa = va.id_empresa
                        WHERE em.id_empresa = :idEmpresa
                    )
                    ) ma ON ma.id_candidato = can.id_candidato
                GROUP BY can.id_candidato
                ORDER BY match DESC, nome_completo ASC, can.id_candidato ASC
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

        // Chamar o método que está sendo testado
        List<Map> candidatos = candidatoDao.listarCandidatosParaEmpresa(1)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(resultadoEsperado, candidatos)
    }

    @Test
    void testListarCandidatosParaEmpresaErro() {
        Mockito.when(conexao.obterLinhas(Mockito.anyString(), Mockito.anyMap())).then {
            throw new Exception("Erro na consulta com rows: ")
        }

        // Chamar o método que está sendo testado
        Exception exception = assertThrows(Exception.class, () -> {
            candidatoDao.listarCandidatosParaEmpresa(1)
        })

        // Verificar se a exceção foi lançada com a mensagem de erro esperada
        assertEquals("Erro ao listar candidatos para a empresa: Erro na consulta com rows: ", exception.getMessage())
    }

    @Test
    void testAtualizarCandidato() {
        // Defina a consulta diretamente no teste
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        String sSQLEsperdao = """
            UPDATE Candidato
            SET nome = :nome,
                sobrenome = :sobrenome,
                cpf = :cpf,
                data_nascimento = :dataNascimento,
                pais = :pais,
                cep = :cep,
                estado = :estado,
                descricao = :descricao
            WHERE id_candidato = :id
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(mapperUtils.converterObjectToMap(Mockito.any(Empresa.class))).thenReturn([
                "id"       : 1,
                "nome"     : "Candi",
                "sobrenome"     : "Dato",
                "cpf"     : "01234567890",
                "dataNascimento"     : "01/01/1970",
                "pais"     : "Brasil",
                "estado"     : "GO",
                "cep"      : "12345678",
                "descricao": "",
                "competencias": []
        ])

        Mockito.when(conexao.executar(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
        }

        // Chamar o método que está sendo testado
        Boolean atualizado = candidatoDao.atualizarCandidato(candidato)

        // Verificar se os resultados correspondem ao esperado
        assertTrue(atualizado)
    }

    @Test
    void testBuscarCandidatoPorCpf() {
        // Defina a consulta diretamente no teste
        Map resultadoEsperado = [id_candidato: 1, nome: "João", sobrenome: "Rezende", cpf: "01234567890", data_nascimento: "1995-02-04", pais: "Brasil", estado: "SP", cep: "69206729", descricao: ""]
        String sSQLEsperdao = """
            SELECT * FROM Candidato
            WHERE cpf = :cpf
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.obterPrimeiraLinha(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([cpf: "01234567890"], parametros)

            return resultadoEsperado
        }

        // Chamar o método que está sendo testado
        Map resultado = candidatoDao.buscarCandidatoPorCpf("01234567890")

        // Verificar se os resultados correspondem ao esperado
        assertEquals(resultadoEsperado, resultado)
    }

    @Test
    void testBuscarCandidatoPorId() {
        // Defina a consulta diretamente no teste
        Map resultadoEsperado = [id_candidato: 1, nome: "João", sobrenome: "Rezende", cpf: "01234567890", data_nascimento: "1995-02-04", pais: "Brasil", estado: "SP", cep: "69206729", descricao: ""]
        String sSQLEsperdao = """
            SELECT * FROM Candidato
            WHERE id_candidato = :idCandidato
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.obterPrimeiraLinha(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([idCandidato: 1], parametros)

            return resultadoEsperado
        }

        // Chamar o método que está sendo testado
        Map resultado = candidatoDao.buscarCandidatoPorId(1)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(resultadoEsperado, resultado)
    }

    @Test
    void testExcluirCandidato() {
        // Defina a consulta diretamente no teste
        String sSQLEsperdao = """
            DELETE FROM Candidato
            WHERE id_candidato = :idCandidato
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.executar(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([idCandidato: 1], parametros)
        }

        // Chamar o método que está sendo testado
        Boolean resultado = candidatoDao.excluirCandidato(1)

        // Verificar se os resultados correspondem ao esperado
        assertTrue(resultado)
    }

    @Test
    void testInserirCandidato() {
        // Defina a consulta diretamente no teste
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        String sSQLEsperdao = """
            INSERT INTO Candidato (nome, sobrenome, cpf, data_nascimento, pais, cep, estado, descricao)
            VALUES (:nome, :sobrenome, :cpf, :dataNascimento, :pais, :cep, :estado, :descricao)
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(mapperUtils.converterObjectToMap(Mockito.any(Empresa.class))).thenReturn([
                "id"       : null,
                "nome"     : "Candi",
                "sobrenome"     : "Dato",
                "cpf"     : "01234567890",
                "dataNascimento"     : "01/01/1970",
                "pais"     : "Brasil",
                "estado"     : "GO",
                "cep"      : "12345678",
                "descricao": "",
                "competencias": []
        ])

        Mockito.when(conexao.inserir(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)

            return 1
        }

        // Chamar o método que está sendo testado
        Integer idCandidato = candidatoDao.inserirCandidato(candidato)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(1, idCandidato)
    }
}
