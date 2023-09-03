package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.model.Empresa

class EmpresaController {
    List<Empresa> empresas = [];

    void adicionarEmpresa(Empresa empresa) {
        empresas << empresa;
    }
}
