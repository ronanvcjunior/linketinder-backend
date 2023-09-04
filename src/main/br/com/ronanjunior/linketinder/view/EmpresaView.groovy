package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Empresa

class EmpresaView {
    List<Empresa> empresas;

    void exibirEmpresas() {
        empresas.each {empresa ->
            print   "(" +
                    " nome: " + empresa.nome + "," +
                    " email: " + empresa.email + "," +
                    " cnpj: " + empresa.cnpj + "," +
                    " pais: " + empresa.pais + "," +
                    " estado: " + empresa.estado + "," +
                    " cep: " + empresa.cep + "," +
                    " competências Esperadas: " + empresa.competenciasEsperadas + "," +
                    " descrição: " + empresa.descricao +
                    ")\n";
        }
    }
}
