package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.MatchDao
import br.com.ronanjunior.linketinder.dto.MatchComIdVagaEIdCandidatoDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Match
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

import java.time.LocalDate

class MatchService {
    private final Conexao conexao
    private final MapperUtils mapperUtils
    private final MatchDao matchDao

    MatchService() {
        this.conexao = new Conexao()
        this.mapperUtils = new MapperUtils()
        this.matchDao = new MatchDao(conexao, mapperUtils)
    }

    MatchService(Conexao conexao, MapperUtils mapperUtils, MatchDao matchDao) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.matchDao = matchDao
    }

    MatchComIdVagaEIdCandidatoDto buscarMatchPorIdCandidatoIdVaga(Candidato candidato, Vaga vaga) {
        try {
            conexao.abrirConexao()
            MatchComIdVagaEIdCandidatoDto match =  this.montarBuscarMatchPorIdCandidatoIdVaga(candidato.id, vaga.id)

            return match
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    MatchComIdVagaEIdCandidatoDto curtirVaga(Candidato candidato, Vaga vaga) {
        try {
            conexao.abrirConexao()

            MatchComIdVagaEIdCandidatoDto match = this.montarBuscarMatchPorIdCandidatoIdVaga(candidato.id, vaga.id)
            match.setDataCurtidaVaga(LocalDate.now())

            switch (match.id) {
                case null:
                    match.setIdCandidato(candidato.id)
                    match.setIdVaga(vaga.id)
                    match = this.montarInserirMatch(match)
                    break
                default:
                    this.montaAtualizarMatch(match)
            }

            return match
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    MatchComIdVagaEIdCandidatoDto curtirCandidato(Candidato candidato, Vaga vaga) {
        try {
            conexao.abrirConexao()

            MatchComIdVagaEIdCandidatoDto match = this.montarBuscarMatchPorIdCandidatoIdVaga(candidato.id, vaga.id)
            match.setDataCurtidaCandidato(LocalDate.now())

            switch (match.id) {
                case null:
                    match.setIdCandidato(candidato.id)
                    match.setIdVaga(vaga.id)
                    match = this.montarInserirMatch(match)
                    break
                default:
                    this.montaAtualizarMatch(match)
            }
            return match
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    protected MatchComIdVagaEIdCandidatoDto montarInserirMatch(MatchComIdVagaEIdCandidatoDto match) {
        try {
            Integer idMatch = matchDao.inserirMatch(match)

            match.setId(idMatch)

            return match
        } catch (Exception e) {
            throw new Exception("Houve um erro ao inserir novo match: ${e.message}", e)
        }
    }

    protected Boolean montaAtualizarMatch(MatchComIdVagaEIdCandidatoDto match) {
        try {
            return matchDao.atualizarMatch(match)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao inserir novo match: ${e.message}", e)
        }
    }

    protected MatchComIdVagaEIdCandidatoDto montarBuscarMatchPorIdCandidatoIdVaga(Integer icCandidato, Integer idVaga) {
        try {
            Map matchMap = matchDao.buscarMatchPorIdCandidatoIdVaga(icCandidato, idVaga)

            return new MatchComIdVagaEIdCandidatoDto(matchMap)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao buscar match por id de candidato e competência: ${e.message}", e)
        }
    }
}
