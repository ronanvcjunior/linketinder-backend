package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.sql.Sql
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.utils.Conexao

class CompetenciaDao {
    private final Conexao conexao
    private final MapperUtils mapperUtils

    CompetenciaDao(Conexao conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    List<Map> listarTodasCompetencias() {
        try {
            String sSQL = construirConsultaCandidatosParaEmpresa()
            return conexao.obterLinhas(sSQL)
        } catch (Exception e) {
            throw new Exception("Erro ao listar competências: ${e.message}", e)
        }
    }

    private String construirConsultaCandidatosParaEmpresa() {
        String sSQL = """
            SELECT id_competencia, nome FROM Competencia
        """
        return sSQL
    }
}
