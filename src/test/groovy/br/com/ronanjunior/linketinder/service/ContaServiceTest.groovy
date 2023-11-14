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
        //given
        Conta conta = new Conta(null, "teste@gmail.com", null, null, null)
        Map contaMap = [id: 1, email: "teste@gmail.com", senha: "teste", id_candidato: 1, id_empresa: null]
        Conta contaEsperado = new Conta(1, "teste@gmail.com", "teste", null, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()

        Mockito.when(mapperUtils.converterMapToObject(Mockito.any(Map), Mockito.any(Object))).thenReturn(contaEsperado)

        Mockito.when(contaDao.buscarContaPorEmail(Mockito.any(String))).thenReturn(contaMap)

        //when
        Boolean retorno = contaService.verificarExistenciaContaComEmail(conta.email)

        //then
        assertTrue(retorno)
    }

    @Test
    void testVerificarExistenciaContaComEmailNaoEncontrado() {
        //given
        Conta conta = new Conta(null, "teste@gmail.com", null, null, null)
        Conta contaEsperado = new Conta(null, null, null, null, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()

        Mockito.when(mapperUtils.converterMapToObject(Mockito.any(Map), Mockito.any(Object))).thenReturn(contaEsperado)

        Mockito.when(contaDao.buscarContaPorEmail(Mockito.any(String))).thenReturn([:])

        //when
        Boolean retorno = contaService.verificarExistenciaContaComEmail(conta.email)

        //then
        assertFalse(retorno)
    }

    @Test
    void testMontarBuscarContaPorEmailSenhaEncontrado() {
        //given
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Conta conta = new Conta(1, "teste@gmail.com", "teste", candidato, empresa)
        Map contaMap = [id: 1, email: "teste@gmail.com", senha: "teste", id_candidato: 1, id_empresa: 1]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(contaDao.buscarContaPorEmailSenha(Mockito.any(String), Mockito.any(String))).thenReturn(contaMap)
        Mockito.when(candidatoService.montarBuscarCandidatoPorId(Mockito.any(Integer))).thenReturn(candidato)
        Mockito.when(empresaService.montarBuscarEmpresaPorId(Mockito.any(Integer))).thenReturn(empresa)

        //when
        Conta retorno = contaService.montarBuscarContaPorEmailSenha(conta.email, conta.senha)

        //then
        assertEquals(conta, retorno)
    }

    @Test
    void testMontarBuscarContaPorEmailSenhaNaoEncontrado() {
        //given
        Conta conta = new Conta(null, "teste@gmail.com", "teste", null, null)
        Conta contaEsperada = new Conta([:])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(contaDao.buscarContaPorEmailSenha(Mockito.any(String), Mockito.any(String))).thenReturn([:])

        //when
        Conta retorno = contaService.montarBuscarContaPorEmailSenha(conta.email, conta.senha)

        //then
        assertEquals(contaEsperada, retorno)
    }

    @Test
    void testMontarInserirConta() {
        //given
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Conta conta = new Conta(1, "teste@gmail.com", "teste", candidato, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(contaDao.inserirConta(Mockito.any(Conta))).thenReturn(1)

        //when
        Conta retorno = contaService.montarInserirConta(conta)

        //then
        assertEquals(conta, retorno)
    }
}
