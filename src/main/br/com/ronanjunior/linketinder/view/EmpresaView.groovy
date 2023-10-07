package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.controller.EmpresaController
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Empresa

class EmpresaView {
    EmpresaController empresaController = new EmpresaController();
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

    void exibirEmpresa(Empresa empresa) {
        println """   |Nome: ${empresa.nome}
                |CNPJ: ${empresa.cnpj}
                |País: ${empresa.pais}
                |CEP: ${empresa.cep}
                |Descrição: ${empresa.descricao}
        |""".stripMargin();
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

    Empresa alterarNome(Empresa empresa) {
        Empresa empresaAlterada = empresaController.copiarEmpresa(empresa);

        String nome;

        print "Nome Atual: ${empresa.nome}\n";
        while (true) {
            print "Nome novo: ";
            nome = scanner.nextLine();
            nome = nome.trim();
            if (nome)
                break;
            else
                println "Nome Inválido";
        }

        empresaAlterada.nome = nome;

        Boolean alterado = empresaController.alterarEmpresa(empresaAlterada);

        if (alterado)
            return empresaAlterada;
        else
            return empresa;
    }

    Empresa alterarCnpj(Empresa empresa) {
        Empresa empresaAlterada = empresaController.copiarEmpresa(empresa);

        String cnpj;

        print "CNPJ Atual: ${empresa.cnpj}\n";
        while (true) {
            print "CNPJ novo: ";
            cnpj = scanner.nextLine();
            cnpj = cnpj.trim().replaceAll(/[^0-9]/, "");
            String regexCnpj = /^\d{2}\.?\d{3}\.?\d{3}\/?\d{4}\-?\d{2}$/;
            if (cnpj ==~ regexCnpj) {
                Boolean cnpjJaCadastrado = empresaController.verificarCnpj(cnpj);
                if (cnpjJaCadastrado)
                    println "CNPJ já cadastrado no sistema";
                else
                    break
            } else
                println "CPF Inválido";
        }

        empresaAlterada.cnpj = cnpj;

        Boolean alterado = empresaController.alterarEmpresa(empresaAlterada);

        if (alterado)
            return empresaAlterada;
        else
            return empresa;
    }

    Empresa alterarPais(Empresa empresa) {
        Empresa empresaAlterada = empresaController.copiarEmpresa(empresa);

        String pais;

        print "País Atual: ${empresa.pais}\n";
        while (true) {
            print "Pais novo: ";
            pais = scanner.nextLine();
            pais = pais.trim();
            if (pais)
                break
            else
                println "País Inválido";
        }

        empresaAlterada.pais = pais;

        Boolean alterado = empresaController.alterarEmpresa(empresaAlterada);

        if (alterado)
            return empresaAlterada;
        else
            return empresa;
    }

    Empresa alterarCep(Empresa empresa) {
        Empresa empresaAlterada = empresaController.copiarEmpresa(empresa);

        String cep;

        print "CEP Atual: ${empresa.cep}\n";
        while (true) {
            print "CEP: ";
            cep = scanner.nextLine();
            cep = cep.trim().replaceAll(/[^0-9]/, "");
            String regexCEP = /^\d{5}\-?\d{3}$/;
            if (cep ==~ regexCEP)
                break;
            else
                println "CEP Inválido";
        }

        empresaAlterada.cep = cep;

        Boolean alterado = empresaController.alterarEmpresa(empresaAlterada);

        if (alterado)
            return empresaAlterada;
        else
            return empresa;
    }

    Empresa alterarDescricao(Empresa empresa) {
        Empresa empresaAlterada = empresaController.copiarEmpresa(empresa);

        print "Descrição Atual: ${empresa.descricao}\n";
        print "Descrição Novo: ";
        String descricao = scanner.nextLine();

        empresaAlterada.descricao = descricao;

        Boolean alterado = empresaController.alterarEmpresa(empresaAlterada);

        if (alterado)
            return empresaAlterada;
        else
            return empresa;
    }
}
