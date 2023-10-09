package br.com.ronanjunior.linketinder.view

import br.com.ronanjunior.linketinder.controller.CandidatoController
import br.com.ronanjunior.linketinder.controller.CompetenciaController
import br.com.ronanjunior.linketinder.controller.ContaController
import br.com.ronanjunior.linketinder.controller.EmpresaController
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.ManipulacaoData

class ContaView {
    ContaController contaController = new ContaController()
    CandidatoController candidatoController = new CandidatoController()
    EmpresaController empresaController = new EmpresaController()
    Scanner scanner = new Scanner(System.in)
    List<Competencia> competenciasCadastradas = []
    ManipulacaoData manipulacaoData = new ManipulacaoData()

    CompetenciaController competenciaController

    ContaView(List<Competencia> competenciasCadastradas) {
        this.competenciasCadastradas = competenciasCadastradas
        this.competenciaController = new CompetenciaController(this.competenciasCadastradas)
    }

    Conta cadastrarContaCandidato() {
        println("Cadastro de um novo usuário:")

        String nome
        String sobrenome
        String cpf
        String dataNascimento
        String pais
        String estado
        String cep
        String email
        String senha

        while (true) {
            print "Nome: "
            nome = scanner.nextLine()
            nome = nome.trim()
            if (nome)
                break
            else
                println "Nome Inválido"
        }

        while (true) {
            print "Sobrenome: "
            sobrenome = scanner.nextLine()
            sobrenome = sobrenome.trim()
            if (sobrenome)
                break
            else
                println "Sobrenome Inválido"
        }

        while (true) {
            print "CPF: "
            cpf = scanner.nextLine()
            cpf = cpf.trim().replaceAll(/[^0-9]/, "")
            String regexCPF = /^\d{3}\.?\d{3}\.?\d{3}\-?\d{2}$/
            if (cpf ==~ regexCPF) {
                Boolean cpfJaCadastrado = candidatoController.verificarCpf(cpf)
                if (cpfJaCadastrado)
                    println "Email já cadastrado no sistema"
                else
                    break
            } else
                println "CPF Inválido"
        }

        while (true) {
            print "Data de nascimento: "
            dataNascimento = scanner.nextLine()
            dataNascimento = dataNascimento.trim()
            String regexDataNascimento = /^(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[0-2])\\/\d{4}/
            if (dataNascimento ==~ regexDataNascimento)
                break
            else
                println "Data de nascimento Inválida"
        }

        while (true) {
            print "Pais: "
            pais = scanner.nextLine()
            pais = pais.trim()
            if (pais)
                break
            else
                println "País Inválido"
        }

        while (true) {
            print "Estado: "
            estado = scanner.nextLine()
            estado = estado.trim()
            if (estado)
                break
            else
                println "Estado Inválido"
        }

        while (true) {
            print "CEP: "
            cep = scanner.nextLine()
            cep = cep.trim().replaceAll(/[^0-9]/, "")
            String regexCEP = /^\d{5}\-?\d{3}$/
            if (cep ==~ regexCEP)
                break
            else
                println "CEP Inválido"
        }

        while (true) {
            print "Email: "
            email = scanner.nextLine()
            email = email.trim()
            String regexEmail = /^\S+@\w+\.\w{2,6}(\.\w{2})?$/
            if (email ==~ regexEmail) {
                Boolean emailJaCadastrado = contaController.verificarEmail(email)
                if(emailJaCadastrado)
                    println "Email já cadastrado no sistema"
                else
                    break
            } else
                println "Email Inválido"
        }

        while (true) {
            print "Senha: "
            senha = scanner.nextLine()
            senha = senha.trim()
            if (senha) 
                break
            else
                println "Senha Inválida"
        }

        this.competenciasCadastradas.forEach {Competencia competencia -> {
            println "${competencia.id}: ${competencia.nome}"
        }}
        List<Competencia> competencias = []
        while (true) {
            print "Nome da competência (ou deixe em branco para encerrar): "
            String nomeCompetencia = scanner.nextLine()
            if (nomeCompetencia.isEmpty()) {
                break
            }
            Competencia competencia = competenciaController.procurarPorNome(nomeCompetencia)
            if (competencias.contains(competencia)) {
                println "a competência ${nomeCompetencia} já está na sua lista de competências\n"
            } else if (competenciasCadastradas.contains(competencia)) {
                competencias.add(competencia)
            } else {
                println "a competência ${nomeCompetencia} não está presente na lista de competências\n" +
                        "${competenciasCadastradas}"
            }

        }

        print "Descrição: "
        String descricao = scanner.nextLine()
        descricao = descricao.trim()

        Conta novaContaCandidato = new Conta(
                null,
                email,
                senha,
                new Candidato(
                        null,
                        nome,
                        sobrenome,
                        cpf,
                        manipulacaoData.stringParaDate(dataNascimento),
                        pais,
                        estado,
                        cep,
                        descricao,
                        competencias
                ),
                null
        )

        return contaController.registrarCandidato(novaContaCandidato)
    }

    Conta cadastrarContaEmpresa() {
        println("Cadastro de um nova Empresa:")

        String nome
        String cnpj
        String pais
        String cep
        String email
        String senha

        while (true) {
            print "Nome: "
            nome = scanner.nextLine()
            nome = nome.trim()
            if (nome)
                break
            else
                println "Nome Inválido"
        }

        while (true) {
            print "CNPJ: "
            cnpj = scanner.nextLine()
            cnpj = cnpj.trim().replaceAll(/[^0-9]/, "")
            String regexCnpj = /^\d{2}\.?\d{3}\.?\d{3}\/?\d{4}\-?\d{2}$/
            if (cnpj ==~ regexCnpj) {
                Boolean cnpjJaCadastrado = empresaController.verificarCnpj(cnpj)
                if (cnpjJaCadastrado)
                    println "CNPJ já cadastrado no sistema"
                else
                    break
            } else
                println "CPF Inválido"
        }

        while (true) {
            print "Pais: "
            pais = scanner.nextLine()
            pais = pais.trim()
            if (pais)
                break
            else
                println "País Inválido"
        }

        while (true) {
            print "CEP: "
            cep = scanner.nextLine()
            cep = cep.trim().replaceAll(/[^0-9]/, "")
            String regexCEP = /^\d{5}\-?\d{3}$/
            if (cep ==~ regexCEP)
                break
            else
                println "CEP Inválido"
        }

        while (true) {
            print "Email: "
            email = scanner.nextLine()
            email = email.trim()
            String regexEmail = /^\S+@\w+\.\w{2,6}(\.\w{2})?$/
            if (email ==~ regexEmail) {
                Boolean emailJaCadastrado = contaController.verificarEmail(email)
                if(emailJaCadastrado)
                    println "Email já cadastrado no sistema"
                else
                    break
            } else
                println "Email Inválido"
        }

        while (true) {
            print "Senha: "
            senha = scanner.nextLine()
            senha = senha.trim()
            if (senha)
                break
            else
                println "Senha Inválida"
        }

        print "Descrição: "
        String descricao = scanner.nextLine()

        Conta novaContaEmpresa = new Conta(
                null,
                email,
                senha,
                null,
                new Empresa(
                        null,
                        nome,
                        cnpj,
                        pais,
                        cep,
                        descricao
                )
        )

        return contaController.registrarEmpresa(novaContaEmpresa)
    }

    Conta fazerLogin() {
        println("Login:")

        String email
        String senha

        while (true) {
            print "Email: "
            email = scanner.nextLine()
            email = email.trim()
            String regexEmail = /^\S+@\w+\.\w{2,6}(\.\w{2})?$/
            if (email ==~ regexEmail) {
                Boolean emailJaCadastrado = contaController.verificarEmail(email)
                if(!emailJaCadastrado)
                    println "Email não cadastrado no sistema"
                else
                    break
            } else
                println "Email Inválido"
        }

        while (true) {
            print "Senha: "
            senha = scanner.nextLine()
            senha = senha.trim()
            if (senha)
                break
            else
                println "Senha Inválida"
        }

        Conta login = contaController.realizarLogin(email, senha)

        if (!login)
            println "Senha incorreta"
        else
            println "Login efetuado com sucesso!\n"

        return login
    }
}
