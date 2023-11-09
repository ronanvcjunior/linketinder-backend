package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.MatchDao
import br.com.ronanjunior.linketinder.dto.MatchComIdVagaEIdCandidatoDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.model.Match
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

class MatchServiceTest extends GroovyTestCase {
    @InjectMocks
    private MatchService matchService

    @Mock
    private Conexao conexao

    @Mock
    private MatchDao matchDao

    @Mock
    private MapperUtils mapperUtils

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    void testBuscarMatchPorIdCandidatoIdVaga() {
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Vaga vaga = new Vaga(1, "Vaga", "", "GO", "Goiânia", empresa, [])
        MatchComIdVagaEIdCandidatoDto retornoEsperado = new MatchComIdVagaEIdCandidatoDto(1, LocalDate.parse("2023-11-02"), null, 1, 1)
        Map matchMap = [id_match: 1, data_curtida_candidato: "2023-11-02", data_curtida_vaga: "", id_candidato: 1, id_vaga: 1]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(matchDao.buscarMatchPorIdCandidatoIdVaga(Mockito.any(Integer), Mockito.any(Integer))).thenReturn(matchMap)

        MatchComIdVagaEIdCandidatoDto retorno = matchService.buscarMatchPorIdCandidatoIdVaga(candidato, vaga)

        assertEquals(retornoEsperado.id, retorno.id)
        assertEquals(retornoEsperado.idCandidato, retorno.idCandidato)
        assertEquals(retornoEsperado.idVaga, retorno.idVaga)
        assertEquals(retornoEsperado.dataCurtidaCandidato, retorno.dataCurtidaCandidato)
        assertEquals(retornoEsperado.dataCurtidaVaga, retorno.dataCurtidaVaga)
    }

    @Test
    void testCurtirVagaMatchNovo() {
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Vaga vaga = new Vaga(1, "Vaga", "", "GO", "Goiânia", empresa, [])
        MatchComIdVagaEIdCandidatoDto retornoEsperado = new MatchComIdVagaEIdCandidatoDto(1, null, LocalDate.now(), 1, 1)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(matchDao.buscarMatchPorIdCandidatoIdVaga(Mockito.any(Integer), Mockito.any(Integer))).thenReturn([:])
        Mockito.when(matchDao.inserirMatch(Mockito.any(MatchComIdVagaEIdCandidatoDto.class))).thenReturn(1)

        MatchComIdVagaEIdCandidatoDto retorno = matchService.curtirVaga(candidato, vaga)

        assertEquals(retornoEsperado.id, retorno.id)
        assertEquals(retornoEsperado.idCandidato, retorno.idCandidato)
        assertEquals(retornoEsperado.idVaga, retorno.idVaga)
        assertEquals(retornoEsperado.dataCurtidaCandidato, retorno.dataCurtidaCandidato)
        assertEquals(retornoEsperado.dataCurtidaVaga, retorno.dataCurtidaVaga)
    }

    @Test
    void testCurtirVagaMatchAntigo() {
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Vaga vaga = new Vaga(1, "Vaga", "", "GO", "Goiânia", empresa, [])
        MatchComIdVagaEIdCandidatoDto retornoEsperado = new MatchComIdVagaEIdCandidatoDto(1, LocalDate.parse("2023-11-02"), LocalDate.now(), 1, 1)
        Map matchMap = [id_match: 1, data_curtida_candidato: "2023-11-02", data_curtida_vaga: "", id_candidato: 1, id_vaga: 1]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(matchDao.buscarMatchPorIdCandidatoIdVaga(Mockito.any(Integer), Mockito.any(Integer))).thenReturn(matchMap)
        Mockito.when(matchDao.atualizarMatch(Mockito.any(MatchComIdVagaEIdCandidatoDto.class))).thenReturn(true)

        MatchComIdVagaEIdCandidatoDto retorno = matchService.curtirVaga(candidato, vaga)

        assertEquals(retornoEsperado.id, retorno.id)
        assertEquals(retornoEsperado.idCandidato, retorno.idCandidato)
        assertEquals(retornoEsperado.idVaga, retorno.idVaga)
        assertEquals(retornoEsperado.dataCurtidaCandidato, retorno.dataCurtidaCandidato)
        assertEquals(retornoEsperado.dataCurtidaVaga, retorno.dataCurtidaVaga)
    }

    @Test
    void testCurtirCandidatoMatchNovo() {
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Vaga vaga = new Vaga(1, "Vaga", "", "GO", "Goiânia", empresa, [])
        MatchComIdVagaEIdCandidatoDto retornoEsperado = new MatchComIdVagaEIdCandidatoDto(1, LocalDate.now(), null, 1, 1)

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(matchDao.buscarMatchPorIdCandidatoIdVaga(Mockito.any(Integer), Mockito.any(Integer))).thenReturn([:])
        Mockito.when(matchDao.inserirMatch(Mockito.any(MatchComIdVagaEIdCandidatoDto.class))).thenReturn(1)

        MatchComIdVagaEIdCandidatoDto retorno = matchService.curtirCandidato(candidato, vaga)

        assertEquals(retornoEsperado.id, retorno.id)
        assertEquals(retornoEsperado.idCandidato, retorno.idCandidato)
        assertEquals(retornoEsperado.idVaga, retorno.idVaga)
        assertEquals(retornoEsperado.dataCurtidaCandidato, retorno.dataCurtidaCandidato)
        assertEquals(retornoEsperado.dataCurtidaVaga, retorno.dataCurtidaVaga)
    }

    @Test
    void testCurtirCandidatoMatchAntigo() {
        Candidato candidato = new Candidato(1, "Candi", "Dato", "01234567890", LocalDate.of(1970, 1, 1), "Brasil", "GO", "12345678", "", [])
        Empresa empresa = new Empresa(1, "Empresa", "012345678901234", "Brasil", "12345678", "")
        Vaga vaga = new Vaga(1, "Vaga", "", "GO", "Goiânia", empresa, [])
        MatchComIdVagaEIdCandidatoDto retornoEsperado = new MatchComIdVagaEIdCandidatoDto(1, LocalDate.now(), LocalDate.parse("2023-11-02"), 1, 1)
        Map matchMap = [id_match: 1, data_curtida_candidato: null, data_curtida_vaga: "2023-11-02", id_candidato: 1, id_vaga: 1]

        Mockito.doNothing().when(conexao).abrirConexao()
        Mockito.doNothing().when(conexao).fecharConexao()
        Mockito.when(matchDao.buscarMatchPorIdCandidatoIdVaga(Mockito.any(Integer), Mockito.any(Integer))).thenReturn(matchMap)
        Mockito.when(matchDao.atualizarMatch(Mockito.any(MatchComIdVagaEIdCandidatoDto.class))).thenReturn(true)

        MatchComIdVagaEIdCandidatoDto retorno = matchService.curtirCandidato(candidato, vaga)

        assertEquals(retornoEsperado.id, retorno.id)
        assertEquals(retornoEsperado.idCandidato, retorno.idCandidato)
        assertEquals(retornoEsperado.idVaga, retorno.idVaga)
        assertEquals(retornoEsperado.dataCurtidaCandidato, retorno.dataCurtidaCandidato)
        assertEquals(retornoEsperado.dataCurtidaVaga, retorno.dataCurtidaVaga)
    }
}
