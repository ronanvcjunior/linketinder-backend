package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.dto.CandidatoListaDaEmpresaDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.service.CandidatoService
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class CandidatoController {
    private final ConexaoRepository conexao = Conexao.obterInstancia()
    private final MapperUtils mapperUtils = new MapperUtils()
    private final CandidatoService candidatoService = new CandidatoService(this.conexao, this.mapperUtils)

    List<CandidatoListaDaEmpresaDto> listarCandidatosParaEmpresa(Integer idEmpresa) {
        try {
            return candidatoService.listarCandidatosParaEmpresa(idEmpresa)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Boolean alterarCandidato(Candidato candidato) {
        try {
            return candidatoService.alterarCandidato(candidato)
        } catch (Exception e) {
            println e.message
            return false
        }
    }

    Candidato procurarCandidatoPorId(Integer idCandidato) {
        try {
            return candidatoService.buscarCandidatoPorId(idCandidato)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Boolean verificarCpf(String cpf) {
        try {
            return candidatoService.verificaraExistenciaCandidatoPorCpf(cpf)
        } catch (Exception e) {
            println e.message
            return null
        }
    }
}
