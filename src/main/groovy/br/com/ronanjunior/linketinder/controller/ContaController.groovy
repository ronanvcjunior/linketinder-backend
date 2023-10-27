package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.dao.ContaDao
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.utils.Conexao
import io.github.cdimascio.dotenv.Dotenv

class ContaController {
    Dotenv dotenv = Dotenv.configure().load()
    ContaDao contaDao = new ContaDao(new Conexao(dotenv))

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
