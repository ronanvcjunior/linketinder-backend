package br.com.ronanjunior.linketinder.view

import br.com.ronanjunior.linketinder.controller.CandidatoController
import br.com.ronanjunior.linketinder.controller.CompetenciaController
import br.com.ronanjunior.linketinder.dto.CandidatoListaDaEmpresaDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.DataUtils

class CandidatoView {
    CandidatoController candidatoController = new CandidatoController()
    Scanner scanner = new Scanner(System.in)
    List<Competencia> competenciasCadastradas = []
    DataUtils manipulacaoData = new DataUtils()

    CompetenciaController competenciaController

    CandidatoView() { }

    CandidatoView(List<Competencia> competenciasCadastradas) {
        this.competenciasCadastradas = competenciasCadastradas
        this.competenciaController = new CompetenciaController(this.competenciasCadastradas)
    }

    void exibirCandidato(Candidato candidato) {
    println """   |Nome: ${candidato.nome}
                |Sobrenome: ${candidato.sobrenome}
                |CPF: ${candidato.cpf}
                |Data de nascimento: ${candidato.dataNascimento}
                |Pais: ${candidato.pais}
                |Estado: ${candidato.estado}
                |CEP: ${candidato.cep}
                |Competências: ${candidato.competencias}
                |Descrição: ${candidato.descricao}
        |""".stripMargin()
    }

    Candidato alterarNome(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato)

        String nome

        print "Nome Atual: ${candidato.nome}\n"
        while (true) {
            print "Nome Novo: "
            nome = scanner.nextLine()
            nome = nome.trim()
            if (nome)
                break
            else
                println "Nome Inválido"
        }

        candidatoAlterado.nome = nome

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado)

        if (alterado)
            return candidatoAlterado
        else
            return candidato
    }

    Candidato alterarSobrenome(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato)

        String sobrenome

        print "Sobrenome Atual: ${candidato.sobrenome}\n"
        while (true) {
            print "Sobrenome novo: "
            sobrenome = scanner.nextLine()
            sobrenome = sobrenome.trim()
            if (sobrenome)
                break
            else
                println "Sobrenome Inválido"
        }

        candidatoAlterado.sobrenome = sobrenome

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado)

        if (alterado)
            return candidatoAlterado
        else
            return candidato
    }

    Candidato alterarCPF(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato)

        String cpf

        print "CPF Atual: ${candidato.cpf}\n"
        while (true) {
            print "CPF novo: "
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

        candidatoAlterado.cpf = cpf

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado)

        if (alterado)
            return candidatoAlterado
        else
            return candidato
    }

    Candidato alterarDataNascimento(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato)

        String dataNascimento

        print "Data de nascimento Atual: ${candidato.dataNascimento}\n"
        while (true) {
            print "Data de nascimento nova: "
            dataNascimento = scanner.nextLine()
            dataNascimento = dataNascimento.trim()
            String regexDataNascimento = /^(0[1-9]|[12][0-9]|3[01])\\/(0[1-9]|1[0-2])\\/\d{4}/
            if (dataNascimento ==~ regexDataNascimento)
                break
            else
                println "Data de nascimento Inválida"
        }

        candidatoAlterado.dataNascimento = manipulacaoData.stringParaDate(dataNascimento)

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado)

        if (alterado)
            return candidatoAlterado
        else
            return candidato
    }

    Candidato alterarPais(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato)

        String pais

        print "País Atual: ${candidato.pais}\n"
        while (true) {
            print "Pais novo: "
            pais = scanner.nextLine()
            pais = pais.trim()
            if (pais)
                break
            else
                println "País Inválido"
        }

        candidatoAlterado.pais = pais

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado)

        if (alterado)
            return candidatoAlterado
        else
            return candidato
    }

    Candidato alterarCEP(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato)

        String cep

        print "CEP Atual: ${candidato.cep}\n"
        while (true) {
            print "CEP novo: "
            cep = scanner.nextLine()
            cep = cep.trim().replaceAll(/[^0-9]/, "")
            String regexCEP = /^\d{5}\-?\d{3}$/
            if (cep ==~ regexCEP)
                break
            else
                println "CEP Inválido"
        }

        candidatoAlterado.cep = cep

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado)

        if (alterado)
            return candidatoAlterado
        else
            return candidato
    }

    Candidato alterarEstado(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato)

        String estado

        print "Estado Atual: ${candidato.estado}\n"
        while (true) {
            print "Estado novo: "
            estado = scanner.nextLine()
            estado = estado.trim()
            if (estado)
                break
            else
                println "Estado Inválido"
        }

        candidatoAlterado.estado = estado

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado)

        if (alterado)
            return candidatoAlterado
        else
            return candidato
    }

    Candidato alterarDescricao(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato)

        print "Descricao Atual: ${candidato.descricao}\n"
        print "Descricao Novo: "
        String descricao = scanner.nextLine()

        candidatoAlterado.descricao = descricao

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado)

        if (alterado)
            return candidatoAlterado
        else
            return candidato
    }

    Candidato adicionarCompetenciasCandidato(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato)


        this.competenciasCadastradas.forEach {Competencia competencia -> {
            println "${competencia.id}: ${competencia.nome}"
        }}
        print "Competências Atuais: ${candidato.competencias}\n"
        List<Competencia> competencias = candidato.competencias
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

        candidatoAlterado.competencias = competencias

        Boolean alterado = candidatoController.adicionarCompetenciaCandidato(candidatoAlterado)

        if (alterado)
            return candidatoAlterado
        else
            return candidato
    }

    Candidato removerCompetenciasCandidato(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato)



        print "Competências Atuais: ${candidatoAlterado.competencias}\n"
        List<Competencia> competencias = candidatoAlterado.competencias
        while (true) {
            print "Nome da competência (ou deixe em branco para encerrar): "
            String nomeCompetencia = scanner.nextLine()
            if (nomeCompetencia.isEmpty()) {
                break
            }
            Competencia competencia = competenciaController.procurarPorNome(nomeCompetencia)
            if (competencias.contains(competencia)) {
                competencias.remove(competencia)
            } else {
                println "a competência ${nomeCompetencia} não está presente na lista de suas competências\n" +
                        "${competencias}"
            }

        }

        Boolean alterado = candidatoController.removerCompetenciaCandidato(candidatoAlterado, candidato)

        if (alterado)
            return candidatoAlterado
        else
            return candidato
    }


    List<CandidatoListaDaEmpresaDto> listarCandidatosParaEmpresa(Empresa empresa) {
        List<CandidatoListaDaEmpresaDto> candidatos = candidatoController.listarCandidatosParaEmpresa(empresa)

        candidatos.forEach {CandidatoListaDaEmpresaDto candidato -> {
            println "" +
                    "Candidato de número ${candidato.id}: " +
                    "[Nome Completo: ${candidato.nomeCompleto}, " +
                    "Competências: ${candidato.competencias}]" +
                    ""
        }}

        return candidatos
    }
}
