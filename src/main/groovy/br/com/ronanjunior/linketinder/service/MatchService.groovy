package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.MatchDao
import br.com.ronanjunior.linketinder.dto.MatchComIdVagaEIdCandidatoDto
import br.com.ronanjunior.linketinder.model.Candidato
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

    MatchService(Conexao conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
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

            conexao.commitTransacao()
            return match
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean curtirVaga(Integer idCandidato, Integer idVaga) {
        try {
            conexao.abrirConexao()

            MatchComIdVagaEIdCandidatoDto match = this.montarBuscarMatchPorIdCandidatoIdVaga(idCandidato, idVaga)
            match.setDataCurtidaVaga(LocalDate.now())

            switch (match.idMatch) {
                case null:
                    match.setIdCandidato(idCandidato)
                    match.setIdVaga(idVaga)
                    this.montarInserirMatch(match)
                    break
                default:
                    this.montaAtualizarMatch(match)
            }

            conexao.commitTransacao()
            return true
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean curtirCandidato(Integer idCandidato, Integer idVaga) {
        try {
            conexao.abrirConexao()

            MatchComIdVagaEIdCandidatoDto match = this.montarBuscarMatchPorIdCandidatoIdVaga(idCandidato, idVaga)
            match.setDataCurtidaCandidato(LocalDate.now())

            switch (match.idMatch) {
                case null:
                    match.setIdCandidato(idCandidato)
                    match.setIdVaga(idVaga)
                    this.montarInserirMatch(match)
                    break
                default:
                    this.montaAtualizarMatch(match)
            }

            conexao.commitTransacao()
            return true
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    protected MatchComIdVagaEIdCandidatoDto montarInserirMatch(MatchComIdVagaEIdCandidatoDto match) {
        try {
            Integer idMatch = matchDao.inserirMatch(match)

            match.setIdMatch(idMatch)

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
