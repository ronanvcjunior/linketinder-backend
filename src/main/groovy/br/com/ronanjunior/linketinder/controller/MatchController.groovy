package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.dao.MatchDao
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.utils.Conexao
import io.github.cdimascio.dotenv.Dotenv

class MatchController {
    MatchDao matchDao = new MatchDao(new Conexao())

    Boolean curtirVaga(Candidato candidato, Vaga vaga) {
        return matchDao.curtirVaga(candidato, vaga)
    }

    Boolean curtirCandidato(Candidato candidato, Vaga vaga) {
        return matchDao.curtirCandidato(candidato, vaga)
    }
}
