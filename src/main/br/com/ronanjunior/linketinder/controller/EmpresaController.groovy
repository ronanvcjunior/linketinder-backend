package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.dao.EmpresaDao
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.utils.Conexao

class EmpresaController {
    List<Empresa> empresas = [];
    EmpresaDao empresaDao = new EmpresaDao(new Conexao());

    void adicionarEmpresa(Empresa empresa) {
        empresas << empresa;
    }

    Boolean alterarEmpresa(Empresa empresa) {
        return empresaDao.atualizarEmpresa(empresa);
    }

    Empresa copiarEmpresa(Empresa empresa) {
        return new Empresa(
                empresa.id,
                empresa.nome,
                empresa.cnpj,
                empresa.pais,
                empresa.cep,
                empresa.descricao
        );
    }

    public Boolean verificarCnpj(String cnpj) {
        return empresaDao.verificarExistenciaCnpjCadastrado(cnpj);
    }
}
