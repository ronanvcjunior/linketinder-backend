package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.dao.EmpresaDao
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.utils.Conexao

class EmpresaController {
    List<Empresa> empresas = [];
    EmpresaDao empresaDao = new EmpresaDao(new Conexao());

    void adicionarEmpresa(Empresa empresa) {
        empresas << empresa;
    }

    public Boolean verificarCnpj(String cnpj) {
        return empresaDao.verificarExistenciaCnpjCadastrado(cnpj);
    }
}
