package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.VagaCompetenciaDao
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.utils.MapperUtils

class VagaCompetenciaService {
    private final ConexaoRepository conexao
    private final MapperUtils mapperUtils
    private final VagaCompetenciaDao vagaCompetenciaDao

    VagaCompetenciaService(ConexaoRepository conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.vagaCompetenciaDao = new VagaCompetenciaDao(conexao, mapperUtils)
    }

    VagaCompetenciaService(ConexaoRepository conexao, MapperUtils mapperUtils, VagaCompetenciaDao vagaCompetenciaDao) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.vagaCompetenciaDao = vagaCompetenciaDao
    }

    Competencia buscarCompetenciaDaVaga(Integer idVaga, Integer idCompetencia) {
        try {
            conexao.abrirConexao()

            Competencia competencia = this.montarBuscarCompetenciaDoVaga(idVaga, idCompetencia)

            conexao.commitTransacao()
            return competencia
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    List<Competencia> listarCompetenciasDoVaga(Integer idVaga) {
        try {
            conexao.abrirConexao()

            List<Competencia> competencias = this.montarListaCompetenciaParaVaga(idVaga)

            conexao.commitTransacao()
            return competencias
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean inserirCompetenciasParaVaga(Integer idVaga, List<Integer> idCompetencias) {
        try {
            conexao.abrirConexao()

            idCompetencias.each { Integer idCompetencia ->
                Competencia competencia = this.montarBuscarCompetenciaDoVaga(idVaga, idCompetencia)
                if (!competencia.id)
                    this.montarInserirCompeteciaParaVaga(idVaga, idCompetencia)
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

    Boolean excluirCompetenciasDoVaga(Integer idVaga, List<Integer> idCompetencias) {
        try {
            conexao.abrirConexao()

            idCompetencias.each { Integer idCompetencia ->
                this.montarExcluirCompeteciaDoVaga(idVaga, idCompetencia)
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

    protected Competencia montarBuscarCompetenciaDoVaga(Integer idVaga, Integer idCompetencia) {
        try {
            Map competenciaMap = vagaCompetenciaDao.buscarCompetenciaVaga(idVaga, idCompetencia)

            return mapperUtils.converterMapToObject(competenciaMap, Competencia)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar busca de competência da vaga: ${e.message}", e)
        }
    }

    protected List<Competencia> montarListaCompetenciaParaVaga(Integer idVaga) {
        try {
            List<Competencia> competencias = []
            List<Map> competenciasDoVaga = vagaCompetenciaDao.listarCompetenciasPorVagaID(idVaga)
            competenciasDoVaga.forEach { Map competenciaMap ->
                competencias.push(mapperUtils.converterMapToObject(competenciaMap, Competencia))
            }

            return competencias;
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar lista de competências da vaga: ${e.message}", e)
        }
    }

    protected Boolean montarInserirCompeteciaParaVaga(Integer idVaga, Integer idCompetencia) {
        try {
            return vagaCompetenciaDao.cadastrarCompetenciaVaga(idVaga, idCompetencia)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar inserir nova competência para a vaga: ${e.message}", e)
        }
    }

    protected Boolean montarExcluirCompeteciaDoVaga(Integer idVaga, Integer idCompetencia) {
        try {
            return vagaCompetenciaDao.excluirCompetenciaVaga(idVaga, idCompetencia)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar excluir competência da vaga: ${e.message}", e)
        }
    }
}
