package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.VagaCompetenciaDao
import br.com.ronanjunior.linketinder.dao.VagaCompetenciaDao
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class VagaCompetenciaService {
    private final Conexao conexao
    private final MapperUtils mapperUtils
    private final VagaCompetenciaDao vagaCompetenciaDao

    VagaCompetenciaService() {
        this.conexao = new Conexao()
        this.mapperUtils = new MapperUtils()
        this.vagaCompetenciaDao = new VagaCompetenciaDao(conexao, mapperUtils)
    }

    VagaCompetenciaService(Conexao conexao, MapperUtils mapperUtils, VagaCompetenciaDao vagaCompetenciaDao) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.vagaCompetenciaDao = vagaCompetenciaDao
    }

    Competencia buscarCompetenciaDaVaga(Integer idVaga, Integer idCompetencia) {
        try {
            conexao.abrirConexao()
            return this.montarBuscarCompetenciaDoVaga(idVaga, idCompetencia)
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    List<Competencia> listarCompetenciasDoVaga(Integer idVaga) {
        try {
            conexao.abrirConexao()
            return this.montarListaCompetenciaParaVaga(idVaga)
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean inserirCompetenciasParaVaga(Integer idVaga, List<Integer> idCompetencias) {
        try {
            conexao.abrirConexao()

            idCompetencias.each { Integer idCompetencia ->
                this.montarInserirCompeteciaParaVaga(idVaga, idCompetencia)
            }

            return true
        } catch (Exception e) {
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

            return true
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    protected Competencia montarBuscarCompetenciaDoVaga(Integer idVaga, Integer idCompetencia) {
        try {
            Map competenciaMap = vagaCompetenciaDao.buscarCompetenciaVaga(idVaga, idCompetencia)

            return new Competencia(competenciaMap)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar busca de competência do candidado: ${e.message}", e)
        }
    }

    protected List<Competencia> montarListaCompetenciaParaVaga(Integer idVaga) {
        try {
            List<Competencia> competencias = []
            List<Map> competenciasDoVaga = vagaCompetenciaDao.listarCompetenciasPorVagaID(idVaga)
            competenciasDoVaga.forEach { Map competenciaMap ->
                competencias.push(new Competencia(competenciaMap))
            }

            return competencias;
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar lista de competências para candidado: ${e.message}", e)
        }
    }

    protected Boolean montarInserirCompeteciaParaVaga(Integer idVaga, Integer idCompetencia) {
        try {
            return vagaCompetenciaDao.cadastrarCompetenciaVaga(idVaga, idCompetencia)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao inserir um nova competência para o vaga: ${e.message}", e)
        }
    }

    protected Boolean montarExcluirCompeteciaDoVaga(Integer idVaga, Integer idCompetencia) {
        try {
            return vagaCompetenciaDao.excluirCompetenciaVaga(idVaga, idCompetencia)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao excluir a competência do vaga: ${e.message}", e)
        }
    }
}
