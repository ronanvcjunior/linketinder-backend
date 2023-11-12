package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.service.VagaCompetenciaService
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class VagaCompetenciaController {
    private final ConexaoRepository conexao = new Conexao()
    private final MapperUtils mapperUtils = new MapperUtils()
    private final VagaCompetenciaService vagaCompetenciaService = new VagaCompetenciaService(this.conexao, this.mapperUtils)

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
