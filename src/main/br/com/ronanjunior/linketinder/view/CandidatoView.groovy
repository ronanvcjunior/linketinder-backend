package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.controller.CandidatoController
import main.br.com.ronanjunior.linketinder.controller.CompetenciaController
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.utils.ManipulacaoData

class CandidatoView {
    List<Candidato> candidatos;
    CandidatoController candidatoController = new CandidatoController();
    Scanner scanner = new Scanner(System.in);
    List<Competencia> competenciasCadastradas = [];
    ManipulacaoData manipulacaoData = new ManipulacaoData();

    CompetenciaController competenciaController;

    CandidatoView(List<Competencia> competenciasCadastradas) {
        this.competenciasCadastradas = competenciasCadastradas;
        this.competenciaController = new CompetenciaController(this.competenciasCadastradas);
    }

    void exibirCandidatos() {
        candidatos.each {candidato ->
            print   "(" +
                    " nome: " + candidato.getNome() + "," +
                    " cpf: " + candidato.getCpf() + "," +
                    " Data de nascimento: " + manipulacaoData.dateParaString(candidato.dataNascimento) + "," +
                    " pais: " + candidato.getPais() + "," +
                    " estado: " + candidato.getEstado() + "," +
                    " cep: " + candidato.getCep() + "," +
                    " competências: " + candidato.getCompetencias() + "," +
                    " descrição: " + candidato.getDescricao() +
                    ")\n";
        }
    }

    void exibirCandidato(Candidato candidato) {
    print """   |Nome: ${candidato.nome}
                |Sobrenome: ${candidato.sobrenome}
                |CPF: ${candidato.cpf}
                |Data de nascimento: ${candidato.dataNascimento}
                |Pais: ${candidato.dataNascimento}
                |Estado: ${candidato.pais}
                |CEP: ${candidato.cep}
                |Competências: ${candidato.competencias}
                |Descrição: ${candidato.descricao}
        |\n""".stripMargin();
    }

    Candidato cadastrarCandidato() {
        println("Cadastro de um novo usuário:")

        print "Nome: ";
        String nome = scanner.nextLine();
        print "CPF: ";
        String cpf = scanner.nextLine();
        print "Data de nascimento: ";
        String dataNascimento = scanner.nextLine();
        print "Pais: ";
        String pais = scanner.nextLine();
        print "Estado: ";
        String estado = scanner.nextLine();
        print "CEP: ";
        String cep = scanner.nextLine();

        List<Competencia> competencias = [];
        while (true) {
            print "Competência (ou deixe em branco para encerrar): ";
            String nomeCompetencia = scanner.nextLine();
            Competencia competencia = new Competencia(null, nomeCompetencia);
            if (nomeCompetencia.isEmpty()) {
                break;
            }
            if (competenciasCadastradas.contains(competencia)) {
                competencias.add(competencia);
            } else {
                println "a competência ${nomeCompetencia} não está presente na lista de competências\n" +
                        "${competenciasCadastradas}";
            }

        }

        print "Descrição: ";
        String descricao = scanner.nextLine();

        return new Candidato(
                nome: nome,
                cpf: cpf,
                dataNascimento: manipulacaoData.stringParaDate(dataNascimento),
                pais: pais,
                estado: estado,
                cep: cep,
                competencias: competencias,
                descricao: descricao
        );
    }

    Candidato alterarNome(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato);

        print "Nome Atual: ${candidato.nome}\n";
        print "Nome Novo: ";
        String nome = scanner.nextLine();

        candidatoAlterado.nome = nome;

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado);

        if (alterado)
            return candidatoAlterado;
        else
            return candidato;
    }

    Candidato alterarSobrenome(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato);

        print "Sobrenome Atual: ${candidato.sobrenome}\n";
        print "Sobrenome Novo: ";
        String sobrenome = scanner.nextLine();

        candidatoAlterado.sobrenome = sobrenome;

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado);

        if (alterado)
            return candidatoAlterado;
        else
            return candidato;
    }

    Candidato alterarCPF(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato);

        print "CPF Atual: ${candidato.cpf}\n";
        print "CPF Novo: ";
        String cpf = scanner.nextLine();

        candidatoAlterado.cpf = cpf;

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado);

        if (alterado)
            return candidatoAlterado;
        else
            return candidato;
    }

    Candidato alterarDataNascimento(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato);

        print "Data de nascimento Atual: ${candidato.dataNascimento}\n";
        print "Data de nascimento Novo: ";
        String dataNascimento = scanner.nextLine();

        candidatoAlterado.dataNascimento = manipulacaoData.stringParaDate(dataNascimento);

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado);

        if (alterado)
            return candidatoAlterado;
        else
            return candidato;
    }

    Candidato alterarPais(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato);

        print "País Atual: ${candidato.pais}\n";
        print "País Novo: ";
        String pais = scanner.nextLine();

        candidatoAlterado.pais = pais;

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado);

        if (alterado)
            return candidatoAlterado;
        else
            return candidato;
    }

    Candidato alterarCEP(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato);

        print "CEP Atual: ${candidato.cep}\n";
        print "CEP Novo: ";
        String cep = scanner.nextLine();

        candidatoAlterado.cep = cep;

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado);

        if (alterado)
            return candidatoAlterado;
        else
            return candidato;
    }

    Candidato alterarEstado(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato);

        print "Estado Atual: ${candidato.estado}\n";
        print "Estado Novo: ";
        String estado = scanner.nextLine();

        candidatoAlterado.estado = estado;

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado);

        if (alterado)
            return candidatoAlterado;
        else
            return candidato;
    }

    Candidato alterarDescricao(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato);

        print "Descricao Atual: ${candidato.descricao}\n";
        print "Descricao Novo: ";
        String descricao = scanner.nextLine();

        candidatoAlterado.descricao = descricao;

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado);

        if (alterado)
            return candidatoAlterado;
        else
            return candidato;
    }

    Candidato adicionarCompetenciasCandidato(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato);


        this.competenciasCadastradas.forEach {Competencia competencia -> {
            println "${competencia.id}: ${competencia.nome}"
        }}
        print "Competências Atuais: ${candidato.competencias}\n";
        List<Competencia> competencias = candidato.competencias;
        while (true) {
            print "Nome da competência (ou deixe em branco para encerrar): ";
            String nomeCompetencia = scanner.nextLine();
            if (nomeCompetencia.isEmpty()) {
                break;
            }
            Competencia competencia = competenciaController.procurarPorNome(nomeCompetencia);
            if (competencias.contains(competencia)) {
                println "a competência ${nomeCompetencia} já está na sua lista de competências\n"
            } else if (competenciasCadastradas.contains(competencia)) {
                competencias.add(competencia);
            } else {
                println "a competência ${nomeCompetencia} não está presente na lista de competências\n" +
                        "${competenciasCadastradas}";
            }

        }

        candidatoAlterado.competencias = competencias;

        Boolean alterado = candidatoController.adicionarCompetenciaCandidato(candidatoAlterado);

        if (alterado)
            return candidatoAlterado;
        else
            return candidato;
    }

    Candidato removerCompetenciasCandidato(Candidato candidato) {
        Candidato candidatoAlterado = candidatoController.copiarCandidato(candidato);



        print "Competências Atuais: ${candidato.competencias}\n";
        List<Competencia> competencias = candidato.competencias;
        while (true) {
            print "Nome da competência (ou deixe em branco para encerrar): ";
            String nomeCompetencia = scanner.nextLine();
            if (nomeCompetencia.isEmpty()) {
                break;
            }
            Competencia competencia = competenciaController.procurarPorNome(nomeCompetencia);
            if (competencias.contains(competencia)) {
                competencias.remove(competencia);
            } else {
                println "a competência ${nomeCompetencia} não está presente na lista de suas competências\n" +
                        "${competencias}";
            }

        }

        candidatoAlterado.competencias = competencias;

        Boolean alterado = candidatoController.alterarCandidato(candidatoAlterado);

        if (alterado)
            return candidatoAlterado;
        else
            return candidato;
    }
}
