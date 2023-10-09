package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.dao.MatchDao
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Vaga
import main.br.com.ronanjunior.linketinder.utils.Conexao

class MatchController {
    MatchDao matchDao = new MatchDao(new Conexao());

    Boolean curtirVaga(Candidato candidato, Vaga vaga) {
        return matchDao.curtirVaga(candidato, vaga);
    }

    Boolean curtirCandidato(Candidato candidato, Vaga vaga) {
        return matchDao.curtirCandidato(candidato, vaga);
    }
}
