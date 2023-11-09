package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.ContaDao
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.model.Empresa
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

class ContaServiceTest extends GroovyTestCase {
    @InjectMocks
    private ContaService contaService

    @Mock
    private Conexao conexao

    @Mock
    private ContaDao contaDao

    @Mock
    private MapperUtils mapperUtils

    @Mock
    private CandidatoService candidatoService

    @Mock
    private EmpresaService empresaService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void testVerificarExistenciaContaComEmailEncontrado() {
        Conta conta = new Conta(null, "teste@gmail.com", "null", null, null)
        Map contaMap = [id_conta: 1, email: "teste@gmail.com", senha: "teste", id_candidato: 1, id_empresa: null]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(contaDao.buscarContaPorEmail(Mockito.any(String))).thenReturn(contaMap)

        Boolean retorno = contaService.verificarExistenciaContaComEmail(conta)

        assertTrue(retorno)
    }

    @Test
    void testVerificarExistenciaContaComEmailNaoEncontrado() {
        Conta conta = new Conta(null, "teste@gmail.com", null, null, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(contaDao.buscarContaPorEmail(Mockito.any(String))).thenReturn([:])

        Boolean retorno = contaService.verificarExistenciaContaComEmail(conta)

        assertFalse(retorno)
    }

    @Test
    void testMontarBuscarContaPorEmailSenhaEncontrado() {
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Conta conta = new Conta(1, "teste@gmail.com", "teste", candidato, empresa)
        Map contaMap = [id_conta: 1, email: "teste@gmail.com", senha: "teste", id_candidato: 1, id_empresa: 1]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(contaDao.buscarContaPorEmailSenha(Mockito.any(String), Mockito.any(String))).thenReturn(contaMap)
        Mockito.when(candidatoService.montarBuscarCandidatoPorId(Mockito.any(Integer))).thenReturn(candidato)
        Mockito.when(empresaService.montarBuscarEmpresaPorId(Mockito.any(Integer))).thenReturn(empresa)

        Conta retorno = contaService.montarBuscarContaPorEmailSenha(conta.email, conta.senha)

        assertEquals(conta, retorno)
    }

    @Test
    void testMontarBuscarContaPorEmailSenhaNaoEncontrado() {
        Conta conta = new Conta(null, "teste@gmail.com", "teste", null, null)
        Conta contaEsperada = new Conta([:])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(contaDao.buscarContaPorEmailSenha(Mockito.any(String), Mockito.any(String))).thenReturn([:])

        Conta retorno = contaService.montarBuscarContaPorEmailSenha(conta.email, conta.senha)

        assertEquals(contaEsperada, retorno)
    }

    @Test
    void testMontarInserirConta() {
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Conta conta = new Conta(1, "teste@gmail.com", "teste", candidato, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(contaDao.inserirConta(Mockito.any(Conta))).thenReturn(1)

        Conta retorno = contaService.montarInserirConta(conta)

        assertEquals(conta, retorno)
    }
}
