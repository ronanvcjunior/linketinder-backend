package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.service.CandidatoCompetenciaService
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class CandidatoCompetenciaController {
    private final Conexao conexao = new Conexao()
    private final MapperUtils mapperUtils = new MapperUtils()
    private final CandidatoCompetenciaService candidatoCompetenciaService = new CandidatoCompetenciaService(this.conexao, this.mapperUtils)

    List<Competencia> listarCompetenciasDoCandidato(Integer idCandidato) {
        try {
            return candidatoCompetenciaService.listarCompetenciasDoCandidato(idCandidato)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Boolean cadastrarCompetenciasParaCandidato(Integer idCandidato, List<Integer> idCompetencias) {
        try {
            return candidatoCompetenciaService.inserirCompetenciasParaCandidato(idCandidato, idCompetencias)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Boolean excluirCompetenciasParaCandidato(Integer idCandidato, List<Integer> idCompetencias) {
        try {
            return candidatoCompetenciaService.excluirCompetenciaDoCandidato(idCandidato, idCompetencias)
        } catch (Exception e) {
            println e.message
            return null
        }
    }
}
