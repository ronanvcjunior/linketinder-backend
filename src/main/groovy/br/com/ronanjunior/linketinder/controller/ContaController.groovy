package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.dao.ContaDao
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.service.ContaService
import br.com.ronanjunior.linketinder.utils.Conexao
import io.github.cdimascio.dotenv.Dotenv

class ContaController {
    private final ContaService contaService = new ContaService()

    Boolean verificarEmail(String email) {
        try {
            return contaService.verificarExistenciaContaComEmail(email)
        } catch (Exception e) {
            println e.message
            return null
        }
    }
}
