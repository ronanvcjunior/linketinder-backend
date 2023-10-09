package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.dao.ContaDao
import main.br.com.ronanjunior.linketinder.model.Conta
import main.br.com.ronanjunior.linketinder.utils.Conexao

class ContaController {
    ContaDao contaDao = new ContaDao(new Conexao())

    Conta registrarCandidato(Conta novoCandidato) {
        Integer idContaCandidato =  contaDao.registrarCandidato(novoCandidato)

        return contaDao.buscarContaPorId(idContaCandidato)
    }

    Conta registrarEmpresa(Conta novaEmpresa) {
        Integer idContaEmpresa =  contaDao.registrarEmpresa(novaEmpresa)

        return contaDao.buscarContaPorId(idContaEmpresa)
    }

    Conta realizarLogin(String email, String senha) {
        return contaDao.realizarLogin(email, senha)
    }

    Boolean verificarEmail(String email) {
        return contaDao.verificarExistenciaEmailCadastrado(email)
    }
}
