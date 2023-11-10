package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.EmpresaDao
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.test.GroovyTestCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EmpresaServiceTest extends GroovyTestCase {
    @InjectMocks
    private EmpresaService empresaService

    @Mock
    private Conexao conexao

    @Mock
    private EmpresaDao empresaDao

    @Mock
    private MapperUtils mapperUtils

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void testBuscarEmpresaPorId() {
        Empresa retornoEsperado = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Map empresaMap = [id_empresa: 1, nome: "Empresa", cnpj: "012345678901234", pais: "Brasil", cep: "12345678", descricao: ""]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.buscarEmpresaPorId(Mockito.any(Integer))).thenReturn(empresaMap)

        Empresa retorno = empresaService.buscarEmpresaPorId(retornoEsperado)

        assertEquals(retornoEsperado, retorno)
    }

    @Test
    void testVerificarExistenciaEmpresaPorCnpjExiste() {
        Empresa retornoEsperado = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Map empresaMap = [id_empresa: 1, nome: "Empresa", cnpj: "012345678901234", pais: "Brasil", cep: "12345678", descricao: ""]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.buscarEmpresaPorCnpj(Mockito.any(String))).thenReturn(empresaMap)

        Boolean existe = empresaService.verificarExistenciaEmpresaPorCnpj(retornoEsperado.cnpj)

        assertTrue(existe)
    }

    @Test
    void testVerificarExistenciaEmpresaPorCnpjNaoExiste() {
        Empresa retornoEsperado = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.buscarEmpresaPorCnpj(Mockito.any(String))).thenReturn([:])

        Boolean existe = empresaService.verificarExistenciaEmpresaPorCnpj(retornoEsperado.cnpj)

        assertFalse(existe)
    }

    @Test
    void testInserirEmpresa() {
        Empresa empresaEsperado = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.inserirEmpresa(Mockito.any(Empresa.class))).thenReturn(1)

        Empresa empresa = empresaService.inserirEmpresa(empresaEsperado)

        assertEquals(empresaEsperado, empresa)
    }

    @Test
    void testAlterarEmpresa() {
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.atualizarEmpresa(Mockito.any(Empresa.class))).thenReturn(true)

        Boolean atualizado = empresaService.alterarEmpresa(empresa)

        assertTrue(atualizado)
    }

    @Test
    void testExcluirEmpresa() {
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.excluirEmpresa(Mockito.any(Integer.class))).thenReturn(true)

        Boolean excluido = empresaService.excluirEmpresa(empresa)

        assertTrue(excluido)
    }
}
