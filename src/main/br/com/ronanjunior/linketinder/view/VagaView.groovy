package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.controller.CompetenciaController
import main.br.com.ronanjunior.linketinder.controller.VagaController
import main.br.com.ronanjunior.linketinder.dto.VagaListaDoCandidadoDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Conta
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
        println("Cadastro de um novo usuário:")

        String nome;
        String pais;
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
            print "Pais: ";
            pais = scanner.nextLine();
            pais = pais.trim();
            if (pais)
                break
            else
                println "País Inválido";
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
                pais,
                cidade,
                login.empresa,
                competencias
        );

        return vagaController.registrarVaga(novaVaga);
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

}
