package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.service.MatchService
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class MatchController {
    private final ConexaoRepository conexao = new Conexao()
    private final MapperUtils mapperUtils = new MapperUtils()
    MatchService matchService = new MatchService(this.conexao, this.mapperUtils)

    Boolean curtirVaga(Integer idCandidato, Integer idVaga) {
        try {
            return matchService.curtirVaga(idCandidato, idVaga)
        } catch (Exception e) {
            println e.message
            return false
        }
    }

    Boolean curtirCandidato(Integer idCandidato, Integer idVaga) {
        try {
            return matchService.curtirCandidato(idCandidato, idVaga)
        } catch (Exception e) {
            println e.message
            return false
        }
    }
}
