package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Empresa

class EmpresaView {
    List<Empresa> empresas;
    Scanner scanner = new Scanner(System.in);

    void exibirEmpresas() {
        empresas.each {empresa ->
            print   "(" +
                    " nome: " + empresa.nome + "," +
                    " cnpj: " + empresa.cnpj + "," +
                    " pais: " + empresa.pais + "," +
                    " cep: " + empresa.cep + "," +
                    " descrição: " + empresa.descricao +
                    ")\n";
        }
    }

    Empresa cadastrarEmpresa() {
        println("Cadastro de uma nova empresa:")

        print "Nome: ";
        String nome = scanner.nextLine();
        print "CNPJ: ";
        String cnpj = scanner.nextLine();
        print "País: ";
        String pais = scanner.nextLine();
        print "CEP: ";
        String cep = scanner.nextLine();

        print "Descrição: ";
        String descricao = scanner.nextLine();

        return new Empresa(
                nome: nome,
                cnpj: cnpj,
                pais: pais,
                cep: cep,
                descricao: descricao
        );
    }
}
