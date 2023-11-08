package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.CompetenciaDao
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class CompetenciaService {
    private final Conexao conexao
    private final MapperUtils mapperUtils
    private final CompetenciaDao competenciaDao

    CompetenciaService() {
        this.conexao = new Conexao()
        this.mapperUtils = new MapperUtils()
        this.competenciaDao = new CompetenciaDao(conexao, mapperUtils)
    }

    CompetenciaService(Conexao conexao, MapperUtils mapperUtils, CompetenciaDao competenciaDao) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.competenciaDao = competenciaDao
    }

    List<Competencia> listarTodasCompetencias() {
        try {
            conexao.abrirConexao()
            return this.montarListaTodasCompetencias()
        } catch (Exception e) {
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
            throw new Exception("Houve um erro ao montar lista de competencias para candidato: ${e.message}", e)
        }
    }
}
