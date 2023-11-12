package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.utils.MapperUtils

class CompetenciaDao {
    private final ConexaoRepository conexao
    private final MapperUtils mapperUtils

    CompetenciaDao(ConexaoRepository conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    List<Map> listarTodasCompetencias() {
        try {
            String sSQL = construirConsultaCompetenicas()
            return conexao.obterLinhas(sSQL)
        } catch (Exception e) {
            throw new Exception("Erro ao listar competências: ${e.message}", e)
        }
    }

    private String construirConsultaCompetenicas() {
        String sSQL = """
            SELECT id_competencia, nome FROM Competencia
        """
        return sSQL
    }
}
