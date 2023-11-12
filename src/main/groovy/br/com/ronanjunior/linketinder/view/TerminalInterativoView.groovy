package br.com.ronanjunior.linketinder.view

import br.com.ronanjunior.linketinder.controller.CompetenciaController
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Conta

class TerminalInterativoView {
    Scanner scanner = new Scanner(System.in)

    Boolean runnigIncial = true
    Boolean runnigCandidatoOuEmpresa = false

    CompetenciaController competenciaController = new CompetenciaController()
    List<Competencia> competenciasCadastradas = competenciaController.listarTodasCompetencias()

    ContaView contaView = new ContaView(competenciasCadastradas)
    CandidatoView candidatoView = new CandidatoView(competenciasCadastradas)
    EmpresaView empresaView = new EmpresaView()
    VagaView vagaView = new VagaView(competenciasCadastradas)
    MatchView matchView = new MatchView()
    Conta login

    void iniciar() {
        println "Digite um comando ('ajuda' para listar as opções, 'sair' para sair do programa):"

        this.menuInicial()

        if(!runnigCandidatoOuEmpresa)
            return

        if (this.login.candidato)
            this.menuCandidato()
        else if (this.login.empresa)
            this.menuEmpresa()
    }

    private void menuInicial() {
        while (runnigIncial) {
            String comando = this.retornarComando()

            switch (comando) {
                case "ajuda":
                    this.exibirAjudaInicio()
                    break
                case "candidato cadastrar":
                    login = contaView.cadastrarContaCandidato()
                    if (login) {
                        this.runnigIncial = false
                        this.runnigCandidatoOuEmpresa = true
                    }
                    break
                case "empresa cadastrar":
                    login = contaView.cadastrarContaEmpresa()
                    if (login) {
                        this.runnigIncial = false
                        this.runnigCandidatoOuEmpresa = true
                    }
                    break
                case "login":
                    login = contaView.fazerLogin()
                    if (login) {
                        this.runnigIncial = false
                        this.runnigCandidatoOuEmpresa = true
                    }
                    break
                case "sair":
                    this.runnigIncial = false
                    break
                default:
                    this.exibirOpcaoInvalida()
            }
        }
    }

    private void menuCandidato() {
        while (runnigCandidatoOuEmpresa) {
            String comando = this.retornarComando()

            switch (comando) {
                case "ajuda":
                    this.exibirAjudaMenuCandidato()
                    break
                case "info":
                    candidatoView.exibirCandidato(login.candidato)
                    break
                case "alterar":
                    println "Digite um campo para alterar (nome, sobrenome, cpf, dataNascimento, pais, estado, cep, descricao, add competencias, sub competencias)"
                    String comandoAlterar = this.retornarComando()
                    switch (comandoAlterar) {
                        case "nome":
                            this.login.candidato = candidatoView.alterarNome(login.candidato)
                            break
                        case "sobrenome":
                            this.login.candidato = candidatoView.alterarSobrenome(login.candidato)
                            break
                        case "cpf":
                            this.login.candidato = candidatoView.alterarCPF(login.candidato)
                            break
                        case "dataNascimento":
                            this.login.candidato = candidatoView.alterarDataNascimento(login.candidato)
                            break
                        case "pais":
                            this.login.candidato = candidatoView.alterarPais(login.candidato)
                            break
                        case "estado":
                            this.login.candidato = candidatoView.alterarEstado(login.candidato)
                            break
                        case "cep":
                            this.login.candidato = candidatoView.alterarCEP(login.candidato)
                            break
                        case "descricao":
                            this.login.candidato = candidatoView.alterarDescricao(login.candidato)
                            break
                        case "add competencias":
                            this.login.candidato = candidatoView.adicionarCompetenciasCandidato(login.candidato)
                            break
                        case "sub competencias":
                            this.login.candidato = candidatoView.removerCompetenciasCandidato(login.candidato)
                            break
                        default:
                            println "O campo ${comandoAlterar} não pertence ao candidato!"
                    }
                    break
                case "vaga listar":
                    vagaView.listarVagasParaCandidato(login.candidato)
                    break
                case "vaga curtir":
                    matchView.curtirVaga(login.candidato)
                    break
                case "sair":
                    this.runnigCandidatoOuEmpresa = false
                    break
                default:
                    this.exibirOpcaoInvalida()
            }
        }
    }

    private void menuEmpresa() {
        while (runnigCandidatoOuEmpresa) {
            String comando = this.retornarComando()

            switch (comando) {
                case "ajuda":
                    this.exibirAjudaMenuEmpresa()
                    break
                case "info":
                    empresaView.exibirEmpresa(login.empresa)
                    break
                case "alterar":
                    println "Digite um campo para alterar (nome, cnpj, pais, cep, descricao)"
                    String comandoAlterar = this.retornarComando()
                    switch (comandoAlterar) {
                        case "nome":
                            this.login.empresa = empresaView.alterarNome(login.empresa)
                            break
                        case "cnpj":
                            this.login.empresa = empresaView.alterarCnpj(login.empresa)
                            break
                        case "pais":
                            this.login.empresa = empresaView.alterarPais(login.empresa)
                            break
                        case "cep":
                            this.login.empresa = empresaView.alterarCep(login.empresa)
                            break
                        case "descricao":
                            this.login.empresa = empresaView.alterarDescricao(login.empresa)
                            break
                        default:
                            println "O campo ${comandoAlterar} não pertence ao candidato!"
                    }
                    break
                case "vaga cadastrar":
                    vagaView.cadastrarVaga(login)
                    break
                case "vaga listar":
                    vagaView.listarVagasParaEmpresa(login.empresa)
                    break
                case "vaga alterar":
                    vagaView.alterarVaga(login.empresa)
                    break
                case "vaga deletar":
                    vagaView.deletarVaga(login.empresa)
                    break
                case "candidato listar":
                    candidatoView.listarCandidatosParaEmpresa(login.empresa)
                    break
                case "candidato curtir":
                    matchView.curtirCandidato(login.empresa)
                    break
                case "sair":
                    this.runnigCandidatoOuEmpresa = false
                    break
                default:
                    this.exibirOpcaoInvalida()
            }
        }
    }

    void exibirAjudaMenuCandidato() {
        print   "Opções disponíveis:\n" +
                " - ajuda: Mostra todas as opções disponíveis\n" +
                " - info: Para mostrar as suas informações\n" +
                " - alterar: Para alterar as suas informações\n" +
                " - vaga listar: Mostra todas as vagas\n" +
                " - vaga curtir: Para curtir uma vaga\n" +
                " - sair: Para sair do programa\n" +
                "\n"
    }

    void exibirAjudaMenuEmpresa() {
        print   "Opções disponíveis:\n" +
                " - ajuda: Mostra todas as opções disponíveis\n" +
                " - info: Para mostrar as suas informações\n" +
                " - alterar: Para alterar as suas informações\n" +
                " - vaga cadastrar: Para cadastrar um nova vaga\n" +
                " - vaga listar: Mostra todos as vagas cadastradas pela empresa\n" +
                " - candidato listar: Mostra todos candidatos cadastrados\n" +
                " - candidato curtir: Para curtir um candidato para uma vaga\n" +
                " - sair: Para sair do programa\n" +
                "\n"
    }

    void exibirAjudaInicio() {
        print   "Opções disponíveis:\n" +
                " - ajuda: Mostra todas as opções disponíveis\n" +
                " - candidato cadastrar: Para cadastrar um novo candidato\n" +
                " - empresa cadastrar: Para cadastrar uma nova empresa\n" +
                " - login: Para entrar na conta\n" +
                " - sair: Para sair do programa\n" +
                "\n"
    }

    void exibirOpcaoInvalida() {
        print   "Opção inválida\n" +
                " digite 'ajuda', para mostrar todas as opções disponíveis\n" +
                "\n"
    }

    String retornarComando() {
        print ">>> "
        String comando = scanner.nextLine()

        return comando
    }
}
