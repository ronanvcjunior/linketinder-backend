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
        //given
        Empresa retornoEsperado = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Map empresaMap = [id_empresa: 1, nome: "Empresa", cnpj: "012345678901234", pais: "Brasil", cep: "12345678", descricao: ""]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.buscarEmpresaPorId(Mockito.any(Integer))).thenReturn(empresaMap)

        //when
        Empresa retorno = empresaService.buscarEmpresaPorId(retornoEsperado)

        //then
        assertEquals(retornoEsperado, retorno)
    }

    @Test
    void testVerificarExistenciaEmpresaPorCnpjExiste() {
        //given
        Empresa retornoEsperado = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Map empresaMap = [id_empresa: 1, nome: "Empresa", cnpj: "012345678901234", pais: "Brasil", cep: "12345678", descricao: ""]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.buscarEmpresaPorCnpj(Mockito.any(String))).thenReturn(empresaMap)

        //when
        Boolean existe = empresaService.verificarExistenciaEmpresaPorCnpj(retornoEsperado.cnpj)

        //then
        assertTrue(existe)
    }

    @Test
    void testVerificarExistenciaEmpresaPorCnpjNaoExiste() {
        //given
        Empresa retornoEsperado = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.buscarEmpresaPorCnpj(Mockito.any(String))).thenReturn([:])

        //when
        Boolean existe = empresaService.verificarExistenciaEmpresaPorCnpj(retornoEsperado.cnpj)

        //then
        assertFalse(existe)
    }

    @Test
    void testInserirEmpresa() {
        //given
        Empresa empresaEsperado = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.inserirEmpresa(Mockito.any(Empresa.class))).thenReturn(1)

        //when
        Empresa empresa = empresaService.inserirEmpresa(empresaEsperado)

        //then
        assertEquals(empresaEsperado, empresa)
    }

    @Test
    void testAlterarEmpresa() {
        //given
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.atualizarEmpresa(Mockito.any(Empresa.class))).thenReturn(true)

        //when
        Boolean atualizado = empresaService.alterarEmpresa(empresa)

        //then
        assertTrue(atualizado)
    }

    @Test
    void testExcluirEmpresa() {
        //given
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.doNothing().when(conexao).commitTransacao()
        Mockito.when(empresaDao.excluirEmpresa(Mockito.any(Integer.class))).thenReturn(true)

        //when
        Boolean excluido = empresaService.excluirEmpresa(empresa)

        //then
        assertTrue(excluido)
    }
}
