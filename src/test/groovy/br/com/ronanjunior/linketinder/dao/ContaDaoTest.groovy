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

class ContaDaoTest extends GroovyTestCase {
    @Mock
    private Conexao conexao

    @Mock
    private MapperUtils mapperUtils

    private ContaDao contaDao

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
        contaDao = new ContaDao(conexao, mapperUtils)
    }

    @Test
    void testBuscarContaPorEmailSenha() {
        // Defina a consulta diretamente no teste
        Conta conta = new Conta(null, "teste@gmail.com", "teste", null, null)
        Map retornoEsperado = [id_conta: 1, email: "teste@gmail.com", senha: "teste", id_candidato: 1, id_empresa: null]
        String sSQLEsperdao = """
            SELECT * FROM Conta
            WHERE email = :email
            AND senha = :senha
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Map parametrosEsperados = [
                "email"     : "teste@gmail.com",
                "senha"     : "teste"
        ]

        Mockito.when(conexao.obterPrimeiraLinha(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, Integer> parametros = args[1]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
            assertEquals(parametrosEsperados, parametros)

            return retornoEsperado
        }

        // Chamar o método que está sendo testado
        Map retorno = contaDao.buscarContaPorEmailSenha(conta.email, conta.senha)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(retornoEsperado, retorno)
    }

    @Test
    void testBuscarContaPorId() {
        // Defina a consulta diretamente no teste
        Map resultadoEsperado = [id_conta: 1, email: "teste@gmail.com", senha: "teste", id_candidato: 1, id_empresa: null]
        String sSQLEsperdao = """
            SELECT * FROM Conta
            WHERE id_conta = :idConta
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.obterPrimeiraLinha(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, Integer> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([idConta: 1], parametros)

            return resultadoEsperado
        }

        // Chamar o método que está sendo testado
        Map resultado = contaDao.buscarContaPorId(1)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(resultadoEsperado, resultado)
    }

    @Test
    void testBuscarCandidatoPorEmail() {
        // Defina a consulta diretamente no teste
        Map resultadoEsperado = [id_conta: 1, email: "teste@gmail.com", senha: "teste", id_candidato: 1, id_empresa: null]
        String sSQLEsperdao = """
            SELECT * FROM Conta
            WHERE email = :email
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.obterPrimeiraLinha(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([email: "teste@gmail.com"], parametros)

            return resultadoEsperado
        }

        // Chamar o método que está sendo testado
        Map resultado = contaDao.buscarContaPorEmail("teste@gmail.com")

        // Verificar se os resultados correspondem ao esperado
        assertEquals(resultadoEsperado, resultado)
    }

    @Test
    void testInserirContaCandidato() {
        // Defina a consulta diretamente no teste
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Conta conta = new Conta(null, "teste@gmail.com", "teste", candidato, null)
        String sSQLEsperdao = """
            INSERT INTO Conta (email, senha, id_candidato, id_empresa)
            VALUES (:email, :senha, :idCandidato, :idEmpresa)
        """
        Map parametrosEsperados = [
                email: conta.email,
                senha: conta.senha,
                idCandidato: conta.candidato?.id,
                idEmpresa: conta.empresa?.id
        ]

        List<String> inserirEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(conexao.inserir(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> inserir = sSQL.split("\n").collect { it.trim()}

            assertEquals(inserirEsperado, inserir)
            assertEquals(parametrosEsperados, parametros)

            return 1
        }

        // Chamar o método que está sendo testado
        Integer idConta = contaDao.inserirConta(conta)

        // Verificar se os resultados correspondem ao esperado
        assertEquals(1, idConta)
    }
}
