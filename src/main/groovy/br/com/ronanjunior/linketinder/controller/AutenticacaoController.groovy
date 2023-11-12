package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.service.AutenticacaoService
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class AutenticacaoController {
    private final ConexaoRepository conexao = new Conexao()
    private final MapperUtils mapperUtils = new MapperUtils()
    private final AutenticacaoService autenticacaoService = new AutenticacaoService(this.conexao, this.mapperUtils)

    Conta cadastrarUsuario(Conta conta) {
        try {
            return autenticacaoService.registrarUsuario(conta)
        } catch (Exception e) {
            println e.message
            return null
        }
    }

    Conta fazerLogin(String email, String senha) {
        try {
            return autenticacaoService.login(email, senha)
        } catch (Exception e) {
            println e.message
            return null
        }
    }
}
