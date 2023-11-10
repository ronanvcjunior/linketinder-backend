package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.service.VagaCompetenciaService

class VagaCompetenciaController {
    private final VagaCompetenciaService vagaCompetenciaService = new VagaCompetenciaService()

    List<Competencia> listarCompetenciasDoCandidato(Integer idVaga) {
        try {
            return vagaCompetenciaService.listarCompetenciasDoVaga(idVaga)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Boolean cadastrarCompetenciasParaVaga(Integer idVaga, List<Integer> idCompetencias) {
        try {
            return vagaCompetenciaService.inserirCompetenciasParaVaga(idVaga, idCompetencias)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Boolean excluirCompetenciasParaVaga(Integer idVaga, List<Integer> idCompetencias) {
        try {
            return vagaCompetenciaService.excluirCompetenciasDoVaga(idVaga, idCompetencias)
        } catch (Exception e) {
            println e.message
            return null
        }
    }
}
