package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.service.ContaService
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class ContaController {
    private final ConexaoRepository conexao = new Conexao()
    private final MapperUtils mapperUtils = new MapperUtils()
    private final ContaService contaService = new ContaService(this.conexao, this.mapperUtils)

    Boolean verificarEmail(String email) {
        try {
            return contaService.verificarExistenciaContaComEmail(email)
        } catch (Exception e) {
            println e.message
            return null
        }
    }
}
