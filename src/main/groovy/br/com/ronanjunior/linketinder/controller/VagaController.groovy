package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.dao.VagaDao
import br.com.ronanjunior.linketinder.dto.VagaListaDoCandidatoDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.service.VagaService
import br.com.ronanjunior.linketinder.utils.Conexao

class VagaController {
    VagaService vagaService = new VagaService()

    List<VagaListaDoCandidatoDto> listarTodasVagasParaCandidato(Integer idCandidato) {
        try {
            return vagaService.listarVagasParaCandidato(idCandidato)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    List<Vaga> listarTodasVagasParaEmpresa(Integer idEmpresa) {
        try {
            return vagaService.listarVagasParaEmpresa(idEmpresa)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Boolean alterarVaga(Vaga vaga) {
        try {
            return vagaService.alterarVaga(vaga)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Boolean deletarVaga(Integer idCandidato) {
        try {
            return vagaService.excluirVaga(idCandidato)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

}
