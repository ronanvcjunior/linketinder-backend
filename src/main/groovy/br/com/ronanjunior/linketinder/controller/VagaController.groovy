package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.dto.VagaListaDoCandidatoDto
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.service.VagaService
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class VagaController {
    private final Conexao conexao = new Conexao()
    private final MapperUtils mapperUtils = new MapperUtils()
    VagaService vagaService = new VagaService(this.conexao, this.mapperUtils)

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

    Vaga buscarVagaPorId(Integer idVaga) {
        try {
            return vagaService.buscarVagaPorId(idVaga)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Vaga cadastrarVaga(Vaga vaga) {
        try {
            return vagaService.inserirVaga(vaga)
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

    Boolean deletarVaga(Integer idVaga) {
        try {
            return vagaService.excluirVaga(idVaga)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

}
