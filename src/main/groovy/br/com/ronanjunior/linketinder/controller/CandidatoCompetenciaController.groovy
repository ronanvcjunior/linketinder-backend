package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.service.CandidatoCompetenciaService

class CandidatoCompetenciaController {
    private final CandidatoCompetenciaService vagaCompetenciaService = new CandidatoCompetenciaService()

    List<Competencia> listarCompetenciasDoCandidato(Integer idCandidato) {
        try {
            return vagaCompetenciaService.listarCompetenciasDoCandidato(idCandidato)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Boolean cadastrarCompetenciasParaCandidato(Integer idCandidato, List<Integer> idCompetencias) {
        try {
            return vagaCompetenciaService.inserirCompetenciasParaCandidato(idCandidato, idCompetencias)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Boolean excluirCompetenciasParaCandidato(Integer idCandidato, List<Integer> idCompetencias) {
        try {
            return vagaCompetenciaService.excluirCompetenciasDoCandidato(idCandidato, idCompetencias)
        } catch (Exception e) {
            println e.message
            return null
        }
    }
}
