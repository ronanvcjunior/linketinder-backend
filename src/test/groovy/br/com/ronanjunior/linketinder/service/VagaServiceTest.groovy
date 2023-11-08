package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.VagaDao
import br.com.ronanjunior.linketinder.dto.VagaListaDoCandidatoDto
import br.com.ronanjunior.linketinder.model.Candidato
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

import java.time.LocalDate

class VagaServiceTest extends GroovyTestCase {
    @InjectMocks
    private VagaService vagaService

    @Mock
    private Conexao conexao

    @Mock
    private VagaDao vagaDao

    @Mock
    private VagaCompetenciaService vagaCompetenciaService

    @Mock
    private MapperUtils mapperUtils

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void testListarVagasParaCandidato() {
        List<VagaListaDoCandidatoDto> retornoEsperado = [
                new VagaListaDoCandidatoDto(1, "Vaga 1", "", "Anônimo", []),
                new VagaListaDoCandidatoDto(2, "Vaga 2", "", "Empresa", [])
        ]
        List<Map> vagaMap = [
                [id_vaga: 1, nome: "Vaga 1", descricao: "", empresa: "Anônimo", match: 0],
                [id_vaga: 2, nome: "Vaga 2", descricao: "", empresa: "Empresa", match: 1]
        ]
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(vagaDao.listarVagasParaCandidato(Mockito.any(Integer))).thenReturn(vagaMap)
        Mockito.when(vagaCompetenciaService.montarListaCompetenciaParaVaga(Mockito.any(Integer))).thenReturn([])

        List<VagaListaDoCandidatoDto> retorno = vagaService.listarVagasParaCandidato(candidato)

        assertEquals(retornoEsperado.sort(), retorno.sort())
    }

    @Test
    void testListarVagasParaEmpresa() {
        List<Vaga> retornoEsperado = [
                new Vaga(1, "Vaga 1", "", "GO", "Goiânia", null, []),
                new Vaga(2, "Vaga 2", "", "SP", "SP", null, [])
        ]
        List<Map> vagaMap = [
                [id_vaga: 1, nome: "Vaga 1", descricao: "", estado: "GO", cidade: "Goiânia", id_empresa: 1],
                [id_vaga: 2, nome: "Vaga 2", descricao: "", estado: "SP", cidade: "SP", id_empresa: 2]
        ]
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(vagaDao.listarVagasParaCandidato(Mockito.any(Integer))).thenReturn(vagaMap)
        Mockito.when(vagaCompetenciaService.montarListaCompetenciaParaVaga(Mockito.any(Integer))).thenReturn([])

        List<Vaga> retorno = vagaService.listarVagasParaEmpresa(empresa)

        assertEquals(retornoEsperado.sort(), retorno.sort())
    }

    @Test
    void testBuscarVagaPorId() {
        Vaga retornoEsperado = new Vaga(1, "Vaga 1", "", "GO", "Goiânia", null, [])
        Map vagaMap = [id_vaga: 1, nome: "Vaga 1", descricao: "", estado: "GO", cidade: "Goiânia", id_empresa: 1]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(vagaDao.buscarVagaPorId(Mockito.any(Integer))).thenReturn(vagaMap)
        Mockito.when(vagaCompetenciaService.montarListaCompetenciaParaVaga(Mockito.any(Integer))).thenReturn([])

        Vaga retorno = vagaService.buscarVagaPorId(retornoEsperado)

        assertEquals(retornoEsperado, retorno)
    }

    @Test
    void testInserirVaga() {
        Vaga vagaEsperado = new Vaga(1, "Vaga 1", "", "GO", "Goiânia", null, [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(vagaDao.inserirVaga(Mockito.any(Vaga.class))).thenReturn(1)

        Vaga vaga = vagaService.inserirVaga(vagaEsperado)

        assertEquals(vagaEsperado, vaga)
    }

    @Test
    void testAlterarVaga() {
        Vaga vaga = new Vaga(1, "Vaga 1", "", "GO", "Goiânia", null, [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(vagaDao.atualizarVaga(Mockito.any(Vaga.class))).thenReturn(true)

        Boolean atualizado = vagaService.alterarVaga(vaga)

        assertTrue(atualizado)
    }

    @Test
    void testExcluirVaga() {
        Vaga vaga = new Vaga(1, "Vaga 1", "", "GO", "Goiânia", null, [])

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(vagaDao.excluirVaga(Mockito.any(Integer.class))).thenReturn(true)

        Boolean excluido = vagaService.excluirVaga(vaga)

        assertTrue(excluido)
    }
}