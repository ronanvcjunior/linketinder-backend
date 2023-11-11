package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Empresa
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
import static org.junit.jupiter.api.Assertions.assertEquals

class EmpresaDaoTest extends GroovyTestCase {
    @Mock
    private Conexao conexao

    @Mock
    private MapperUtils mapperUtils

    private EmpresaDao empresaDao

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
        empresaDao = new EmpresaDao(conexao, mapperUtils)
    }

    @Test
    void testInserirEmpresa() {
        //given
        Empresa empresa = new Empresa(null, "Empresa", "012345678901234", "Brasil", "12345678", "")
        String sSQLEsperdao = """
            INSERT INTO Empresa (nome, cnpj, descricao, pais, cep)
            VALUES (:nome, :cnpj, :descricao, :pais, :cep)
        """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(mapperUtils.converterObjectToMap(Mockito.any(Empresa.class))).thenReturn([
                "id"       : null,
                "nome"     : "Empresa",
                "cnpj"     : "012345678901234",
                "pais"     : "Brasil",
                "cep"     : "12345678",
                "descricao"     : ""
        ])

        Mockito.when(conexao.inserir(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)

            return 1
        }

        //when
        Integer idEmpresa = empresaDao.inserirEmpresa(empresa)

        //then
        assertEquals(1, idEmpresa)
    }

    @Test
    void testAtualizarEmpresa() {
        //given
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        String sSQLEsperdao = """
                UPDATE Empresa
                SET nome = :nome,
                    cnpj = :cnpj,
                    pais = :pais,
                    cep = :cep,
                    descricao = :descricao
                WHERE id_empresa = :id
            """

        List<String> atualizaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}

        Mockito.when(mapperUtils.converterObjectToMap(Mockito.any(Empresa.class))).thenReturn([
                "id"       : 1,
                "nome"     : "Empresa",
                "cnpj"     : "012345678901234",
                "pais"     : "Brasil",
                "cep"     : "12345678",
                "descricao"     : ""
        ])

        Mockito.when(conexao.executar(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]

            List<String> atualiza = sSQL.split("\n").collect { it.trim()}

            assertEquals(atualizaEsperado, atualiza)
        }

        //when
        Boolean atualizado = empresaDao.atualizarEmpresa(empresa)

        //then
        assertTrue(atualizado)
    }

    @Test
    void testExcluirEmpresa() {
        //given
        String sSQLEsperdao = """
            DELETE FROM Empresa
            WHERE id_empresa = :idEmpresa
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.executar(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([idEmpresa: 1], parametros)
        }

        //when
        Boolean resultado = empresaDao.excluirEmpresa(1)

        //then
        assertTrue(resultado)
    }

    @Test
    void testBuscarEmpresaPorId() {
        //given
        Map resultadoEsperado = [id_empresa: 1, nome: "Empresa", cnpj: "012345678901234", pais: "Brasil", cep: "12345678", descricao: ""]
        String sSQLEsperdao = """
            SELECT * FROM Empresa
            WHERE id_empresa = :idEmpresa
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.obterPrimeiraLinha(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([idEmpresa: 1], parametros)

            return resultadoEsperado
        }

        //when
        Map resultado = empresaDao.buscarEmpresaPorId(1)

        //then
        assertEquals(resultadoEsperado, resultado)
    }

    @Test
    void testBuscarEmpresaPorCnpj() {
        //given
        Map resultadoEsperado = [id_empresa: 1, nome: "Empresa", cnpj: "012345678901234", pais: "Brasil", cep: "12345678", descricao: ""]
        String sSQLEsperdao = """
            SELECT * FROM Empresa
            WHERE cnpj = :cnpj
        """

        List<String> consultaEsperado = sSQLEsperdao.split("\n").collect { it.trim()}
        Mockito.when(conexao.obterPrimeiraLinha(Mockito.anyString(), Mockito.anyMap())).thenAnswer { invocation ->
            List<Object> args = invocation.getArguments()
            String sSQL = args[0]
            Map<String, String> parametros = args[1]

            List<String> consulta = sSQL.split("\n").collect { it.trim()}

            assertEquals(consultaEsperado, consulta)
            assertEquals([cnpj: "01234567890"], parametros)

            return resultadoEsperado
        }

        //when
        Map resultado = empresaDao.buscarEmpresaPorCnpj("01234567890")

        //then
        assertEquals(resultadoEsperado, resultado)
    }
}
