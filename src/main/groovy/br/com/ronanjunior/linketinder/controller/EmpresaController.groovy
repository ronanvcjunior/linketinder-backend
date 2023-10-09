package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.dao.EmpresaDao
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.Conexao

class EmpresaController {
    EmpresaDao empresaDao = new EmpresaDao(new Conexao())

    Boolean alterarEmpresa(Empresa empresa) {
        return empresaDao.atualizarEmpresa(empresa)
    }

    Empresa copiarEmpresa(Empresa empresa) {
        return new Empresa(
                empresa.id,
                empresa.nome,
                empresa.cnpj,
                empresa.pais,
                empresa.cep,
                empresa.descricao
        )
    }

    Boolean verificarCnpj(String cnpj) {
        return empresaDao.verificarExistenciaCnpjCadastrado(cnpj)
    }
}