package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.dao.MatchDao
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.service.MatchService
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils
import io.github.cdimascio.dotenv.Dotenv

class MatchController {
    private final Conexao conexao = new Conexao()
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
