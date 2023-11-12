package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.service.EmpresaService
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class EmpresaController {
    private final Conexao conexao = new Conexao()
    private final MapperUtils mapperUtils = new MapperUtils()
    EmpresaService empresaService = new EmpresaService(this.conexao, this.mapperUtils)

    Boolean alterarEmpresa(Empresa empresa) {
        try {
            return empresaService.alterarEmpresa(empresa)
        } catch (Exception e) {
            println e.message
            return false
        }
    }

    Boolean verificarCnpj(String cnpj) {
        try {
            return empresaService.verificarExistenciaEmpresaPorCnpj(cnpj)
        } catch (Exception e) {
            println e.message
            return null
        }
    }
}
