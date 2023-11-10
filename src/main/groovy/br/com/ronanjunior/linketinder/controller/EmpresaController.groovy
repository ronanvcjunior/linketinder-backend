package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.dao.EmpresaDao
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.service.EmpresaService
import br.com.ronanjunior.linketinder.utils.Conexao
import io.github.cdimascio.dotenv.Dotenv

class EmpresaController {
    EmpresaService empresaService = new EmpresaService()

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
