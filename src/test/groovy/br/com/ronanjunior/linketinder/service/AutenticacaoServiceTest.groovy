package br.com.ronanjunior.linketinder.service


import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
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

class AutenticacaoServiceTest extends GroovyTestCase {
    @InjectMocks
    private AutenticacaoService autenticacaoService

    @Mock
    private Conexao conexao

    @Mock
    private ContaService contaService

    @Mock
    private CandidatoService candidatoService

    @Mock
    private EmpresaService empresaService

    @Mock
    private CandidatoCompetenciaService candidatoCompetenciaService

    @Mock
    private MapperUtils mapperUtils

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void testInserirUsuarioCandidato() {
        //given
        List<Competencia> competencias = [
                new Competencia(1, "Java"),
                new Competencia(2, "Groovy")
        ]
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", competencias)
        Conta contaEnvio = new Conta(null, "teste@gmail.com", "teste", candidato, null)
        Conta contaEsperada = new Conta(1, "teste@gmail.com", "teste", candidato, null)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoCompetenciaService.montarInserirCompeteciaParaCandidato(Mockito.any(Integer), Mockito.any(Integer))).thenReturn(true)
        Mockito.when(candidatoService.montarInserirCandidato(Mockito.any(Candidato))).thenReturn(candidato)
        Mockito.when(contaService.montarInserirConta(Mockito.any(Conta))).thenReturn(contaEsperada)

        //when
        Conta conta = autenticacaoService.registrarUsuario(contaEnvio)

        //then
        assertEquals(contaEsperada, conta)
    }

    @Test
    void testInserirUsuarioEmpresa() {
        //given
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Conta contaEnvio = new Conta(null, "teste@gmail.com", "teste", null, empresa)
        Conta contaEsperada = new Conta(1, "teste@gmail.com", "teste", null, empresa)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(candidatoCompetenciaService.montarInserirCompeteciaParaCandidato(Mockito.any(Integer), Mockito.any(Integer))).thenReturn(true)
        Mockito.when(empresaService.montarInserirEmpresa(Mockito.any(Empresa))).thenReturn(empresa)
        Mockito.when(contaService.montarInserirConta(Mockito.any(Conta))).thenReturn(contaEsperada)

        //when
        Conta conta = autenticacaoService.registrarUsuario(contaEnvio)

        //then
        assertEquals(contaEsperada, conta)
    }

    @Test
    void testLogin() {
        //given
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Conta contaEnvio = new Conta(null, "teste@gmail.com", "teste", null, null)
        Conta contaEsperada = new Conta(1, "teste@gmail.com", "teste", null, empresa)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(contaService.montarBuscarContaPorEmailSenha(Mockito.any(String), Mockito.any(String))).thenReturn(contaEsperada)

        //when
        Conta conta = autenticacaoService.login(contaEnvio.email, contaEnvio.senha)

        //then
        assertEquals(contaEsperada, conta)
    }
}
