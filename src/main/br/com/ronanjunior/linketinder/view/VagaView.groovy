package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.controller.CompetenciaController
import main.br.com.ronanjunior.linketinder.controller.VagaController
import main.br.com.ronanjunior.linketinder.dto.VagaListaDoCandidadoDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Conta
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.model.Vaga

class VagaView {

    VagaController vagaController = new VagaController();
    Scanner scanner = new Scanner(System.in);

    List<Competencia> competenciasCadastradas = [];

    CompetenciaController competenciaController;

    VagaView(List<Competencia> competenciasCadastradas) {
        this.competenciasCadastradas = competenciasCadastradas;
        this.competenciaController = new CompetenciaController(this.competenciasCadastradas);
    }

    Vaga cadastrarVaga(Conta login) {
        println("Cadastro de um nova vaga:")

        String nome;
        String estado;
        String cidade;

        while (true) {
            print "Nome: ";
            nome = scanner.nextLine();
            nome = nome.trim();
            if (nome)
                break;
            else
                println "Nome Inválido";
        }

        while (true) {
            print "Estado: ";
            estado = scanner.nextLine();
            estado = estado.trim();
            if (estado)
                break
            else
                println "Estado Inválido";
        }

        while (true) {
            print "Cidade: ";
            cidade = scanner.nextLine();
            cidade = cidade.trim();
            if (cidade)
                break
            else
                println "Cidade Inválido";
        }

        this.competenciasCadastradas.forEach {Competencia competencia -> {
            println "${competencia.id}: ${competencia.nome}"
        }}
        List<Competencia> competencias = [];
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

        print "Descrição: ";
        String descricao = scanner.nextLine();
        descricao = descricao.trim();

        Vaga novaVaga = new Vaga(
                null,
                nome,
                descricao,
                estado,
                cidade,
                login.empresa,
                competencias
        );

        return vagaController.registrarVaga(novaVaga);
    }

    void alterarVaga(Empresa empresa) {
        List<Vaga> vagas = this.listarVagasParaEmpresa(empresa);

        println "Qual vaga deseja alterar? Selecione o número dela"
        print "Número da vaga: ";
        Integer idVaga = scanner.nextInt();
        scanner.nextLine();

        Vaga vaga = vagaController.procurarVagaDaEmpresaPorId(idVaga, empresa.id);
        if (vagas.contains(vaga)) {
            println "Digite um campo para alterar (nome, esdato, cidade, descricao, add competencias, sub competencias)"
            print ">>> ";
            String comandoAlterar = scanner.nextLine();
            switch (comandoAlterar) {
                case "nome":
                    this.alterarNome(vaga);
                    break;
                case "esdato":
                    this.alterarEstado(vaga);
                    break;
                case "cidade":
                    this.alterarCidade(vaga);
                    break;
                case "descricao":
                    this.alterarDescricao(vaga);
                    break;
                case "add competencias":
                    this.adicionarCompetenciasVaga(vaga);
                    break;
                case "sub competencias":
                    this.removerCompetenciasVaga(vaga);
                    break;
                default:
                    println "O campo ${comandoAlterar} não pertence a vaga!"
            }
        } else {
            println "Não exite a vaga de número ${idVaga} registrada\n"
        }

    }

    Vaga alterarNome(Vaga vaga) {
        Vaga vagaAlterado = vagaController.copiarVaga(vaga);

        String nome;

        print "Nome Atual: ${vaga.nome}\n";
        while (true) {
            print "Nome Novo: ";
            nome = scanner.nextLine();
            nome = nome.trim();
            if (nome)
                break;
            else
                println "Nome Inválido";
        }

        vagaAlterado.nome = nome;

        Boolean alterado = vagaController.alterarVaga(vagaAlterado);

        if (alterado)
            return vagaAlterado;
        else
            return vaga;
    }

    Vaga alterarEstado(Vaga vaga) {
        Vaga vagaAlterado = vagaController.copiarVaga(vaga);

        String estado;

        print "Estado Atual: ${vaga.estado}\n";
        while (true) {
            print "Estado Novo: ";
            estado = scanner.nextLine();
            estado = estado.trim();
            if (estado)
                break;
            else
                println "Estado Inválido";
        }

        vagaAlterado.estado = estado;

        Boolean alterado = vagaController.alterarVaga(vagaAlterado);

        if (alterado)
            return vagaAlterado;
        else
            return vaga;
    }

    Vaga alterarCidade(Vaga vaga) {
        Vaga vagaAlterado = vagaController.copiarVaga(vaga);

        String cidade;

        print "Cidade Atual: ${vaga.cidade}\n";
        while (true) {
            print "Cidade Nova: ";
            cidade = scanner.nextLine();
            cidade = cidade.trim();
            if (cidade)
                break;
            else
                println "Cidade Inválida";
        }

        vagaAlterado.cidade = cidade;

        Boolean alterado = vagaController.alterarVaga(vagaAlterado);

        if (alterado)
            return vagaAlterado;
        else
            return vaga;
    }

    Vaga alterarDescricao(Vaga vaga) {
        Vaga vagaAlterado = vagaController.copiarVaga(vaga);

        print "Descricao Atual: ${vaga.descricao}\n";
        print "Descricao Novo: ";
        String descricao = scanner.nextLine();

        vagaAlterado.descricao = descricao;

        Boolean alterado = vagaController.alterarVaga(vagaAlterado);

        if (alterado)
            return vagaAlterado;
        else
            return vaga;
    }

    Vaga adicionarCompetenciasVaga(Vaga vaga) {
        Vaga vagaAlterado = vagaController.copiarVaga(vaga);


        this.competenciasCadastradas.forEach {Competencia competencia -> {
            println "${competencia.id}: ${competencia.nome}"
        }}
        print "Competências Atuais: ${vaga.competencias}\n";
        List<Competencia> competencias = vaga.competencias;
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

        vagaAlterado.competencias = competencias;

        Boolean alterado = vagaController.adicionarCompetenciaVaga(vagaAlterado);

        if (alterado)
            return vagaAlterado;
        else
            return vaga;
    }

    Vaga removerCompetenciasVaga(Vaga vaga) {
        Vaga vagaAlterado = vagaController.copiarVaga(vaga);



        print "Competências Atuais: ${vagaAlterado.competencias}\n";
        List<Competencia> competencias = vagaAlterado.competencias;
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

        Boolean alterado = vagaController.removerCompetenciaVaga(vagaAlterado, vaga);

        if (alterado)
            return vagaAlterado;
        else
            return vaga;
    }


    void listarVagasParaCandidato(Candidato candidato) {
        List<VagaListaDoCandidadoDto> vagas = vagaController.listarTodasVagasParaCandidato(candidato);

        vagas.forEach {VagaListaDoCandidadoDto vaga -> {
            println "" +
                    "Vaga de número ${vaga.id}: " +
                    "[Nome: ${vaga.nome}, " +
                    "Empresa: ${vaga.nomeEmpresa}, " +
                    "Competências desejadas: ${vaga.competencias}, " +
                    "Descrição: ${vaga.descricao}]" +
                    "";
        }}
    }

    List<Vaga> listarVagasParaEmpresa(Empresa empresa) {
        List<Vaga> vagas = vagaController.listarTodasVagasParaEmpresa(empresa);

        this.mostrarVagasParaEmpresa(vagas);

        return vagas;
    }

    private void mostrarVagasParaEmpresa(List<Vaga> vagas) {
        vagas.forEach {Vaga vaga -> {
            println "" +
                    "Vaga de número ${vaga.id}: " +
                    "[Nome: ${vaga.nome}, " +
                    "Estado: ${vaga.estado}, " +
                    "Cidade: ${vaga.cidade}, " +
                    "Competências desejadas: ${vaga.competencias}, " +
                    "Descrição: ${vaga.descricao}]" +
                    "";
        }}
    }

}
