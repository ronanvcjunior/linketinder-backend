package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.service.CompetenciaService
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class CompetenciaController {
    private final ConexaoRepository conexao = new Conexao()
    private final MapperUtils mapperUtils = new MapperUtils()
    CompetenciaService competenciaService = new CompetenciaService(this.conexao, this.mapperUtils)

    List<Competencia> listarTodasCompetencias() {
        try {
            return competenciaService.listarTodasCompetencias()
        } catch (Exception e) {
            println e.message
            return null
        }
    }
}
