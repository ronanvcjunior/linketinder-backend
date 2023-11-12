package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.CandidatoCompetenciaDao
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.utils.MapperUtils

class CandidatoCompetenciaService {
    private final ConexaoRepository conexao
    private final MapperUtils mapperUtils
    private final CandidatoCompetenciaDao candidatoCompetenciaDao

    CandidatoCompetenciaService(ConexaoRepository conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.candidatoCompetenciaDao = new CandidatoCompetenciaDao(conexao, mapperUtils)
    }

    CandidatoCompetenciaService(ConexaoRepository conexao, MapperUtils mapperUtils, CandidatoCompetenciaDao candidatoCompetenciaDao) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.candidatoCompetenciaDao = candidatoCompetenciaDao
    }

    Competencia buscarCompetenciaDoCandidato(Candidato candidato, Competencia competencia) {
        try {
            conexao.abrirConexao()

            competencia = this.montarBuscarCompetenciaDoCandidato(candidato.id, competencia.id)

            conexao.commitTransacao()
            return competencia
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    List<Competencia> listarCompetenciasDoCandidato(Candidato candidato) {
        try {
            conexao.abrirConexao()

            List<Competencia> competencias = this.montarListaCompetenciaParaCandidato(candidato.id)

            conexao.commitTransacao()
            return competencias
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean inserirCompetenciasParaCandidato(Integer idCandidato, List<Integer> idCompetencias) {
        try {
            conexao.abrirConexao()

            idCompetencias.each { Integer idCompetencia ->
                Competencia competencia = this.montarBuscarCompetenciaDoCandidato(idCandidato, idCompetencia)
                if (!competencia.id)
                    this.montarInserirCompeteciaParaCandidato(idCandidato, idCompetencia)
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

    Boolean excluirCompetenciaDoCandidato(Integer idCandidato, List<Integer> idCompetencias) {
        try {
            conexao.abrirConexao()

            idCompetencias.each { Integer idCompetencia ->
                this.montarExcluirCompeteciaDoCandidato(idCandidato, idCompetencia)
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

    protected Competencia montarBuscarCompetenciaDoCandidato(Integer idCandidato, Integer idCompetencia) {
        try {
            Map competenciaMap = candidatoCompetenciaDao.buscarCompetenciaCandidato(idCandidato, idCompetencia)

            return new Competencia(competenciaMap)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar busca de competência do candidado: ${e.message}", e)
        }
    }

    protected List<Competencia> montarListaCompetenciaParaCandidato(Integer idCandidato) {
        try {
            List<Competencia> competencias = []
            List<Map> competenciasDoCandidato = candidatoCompetenciaDao.listarCompetenciasPorCandidatoID(idCandidato)
            competenciasDoCandidato.forEach { Map competenciaMap ->
                competencias.push(new Competencia(competenciaMap))
            }

            return competencias;
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar lista de competências para candidado: ${e.message}", e)
        }
    }

    protected Boolean montarInserirCompeteciaParaCandidato(Integer idCandidato, Integer idCompetencia) {
        try {
            return candidatoCompetenciaDao.cadastrarCompetenciaCandidato(idCandidato, idCompetencia)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar inserir nova competência para o candidato: ${e.message}", e)
        }
    }

    protected Boolean montarExcluirCompeteciaDoCandidato(Integer idCandidato, Integer idCompetencia) {
        try {
            return candidatoCompetenciaDao.excluirCompetenciaCandidato(idCandidato, idCompetencia)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar excluir competência do candidato: ${e.message}", e)
        }
    }
}
