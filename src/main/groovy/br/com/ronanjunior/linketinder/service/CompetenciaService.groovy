package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.CompetenciaDao
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.utils.MapperUtils

class CompetenciaService {
    private final ConexaoRepository conexao
    private final MapperUtils mapperUtils
    private final CompetenciaDao competenciaDao

    CompetenciaService(ConexaoRepository conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.competenciaDao = new CompetenciaDao(conexao, mapperUtils)
    }

    CompetenciaService(ConexaoRepository conexao, MapperUtils mapperUtils, CompetenciaDao competenciaDao) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.competenciaDao = competenciaDao
    }

    List<Competencia> listarTodasCompetencias() {
        try {
            conexao.abrirConexao()

            List<Competencia> competencias = this.montarListaTodasCompetencias()

            conexao.commitTransacao()
            return competencias
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    protected List<Competencia> montarListaTodasCompetencias() {
        try {
            List<Competencia> competencias = []

            List<Map> competenciasMap = competenciaDao.listarTodasCompetencias()
            competenciasMap.forEach { Map competenciaMap ->
                competencias.push(new Competencia(competenciaMap))
            }
            return competencias
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar lista de todas as competências: ${e.message}", e)
        }
    }
}
