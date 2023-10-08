package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.dao.CompetenciaDao
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Conta
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.utils.Conexao
import main.br.com.ronanjunior.linketinder.view.CandidatoView
import main.br.com.ronanjunior.linketinder.view.ContaView
import main.br.com.ronanjunior.linketinder.view.EmpresaView
import main.br.com.ronanjunior.linketinder.view.MatchView
import main.br.com.ronanjunior.linketinder.view.TerminalInterativoView
import main.br.com.ronanjunior.linketinder.view.VagaView

class TerminalInterativoController {
    Boolean runnigIncial = true;
    Boolean runnigCandidatoOuEmpresa = false;

    CompetenciaDao competenciaDao = new CompetenciaDao(new Conexao());
    List<Competencia> competenciasCadastradas = competenciaDao.listarTodasCompetencias();

    TerminalInterativoView terminalInterativoView = new TerminalInterativoView();
    ContaView contaView = new ContaView(competenciasCadastradas);
    CandidatoController candidatoController = new CandidatoController();
    CandidatoView candidatoView = new CandidatoView(competenciasCadastradas);
    EmpresaController empresaController = new EmpresaController();
    EmpresaView empresaView = new EmpresaView();
    VagaView vagaView = new VagaView(competenciasCadastradas);
    MatchView matchView = new MatchView();
    Conta login;


    TerminalInterativoController() {

    }

    void iniciar() {
        println "Digite um comando ('ajuda' para listar as opções, 'sair' para sair do programa):"

        this.menuInicial();

        if (this.login.candidato)
            this.menuCandidato();
        else if (this.login.empresa)
            this.menuEmpresa();
    }

    private void menuInicial() {
        while (runnigIncial) {
            String comando = terminalInterativoView.retornarComando();

            switch (comando) {
                case "ajuda":
                    terminalInterativoView.exibirAjudaInicio();
                    break;
                case "candidato cadastrar":
                    login = contaView.cadastrarContaCandidato();
                    if (login) {
                        this.runnigIncial = false;
                        this.runnigCandidatoOuEmpresa = true;
                    }
                    break;
                case "empresa cadastrar":
                    login = contaView.cadastrarContaEmpresa();
                    if (login) {
                        this.runnigIncial = false;
                        this.runnigCandidatoOuEmpresa = true;
                    }
                    break;
                case "login":
                    login = contaView.fazerLogin();
                    if (login) {
                        this.runnigIncial = false;
                        this.runnigCandidatoOuEmpresa = true;
                    }
                    break;
                case "sair":
                    this.runnigIncial = false;
                    break;
                default:
                    terminalInterativoView.exibirOpcaoInvalida();
            }
        }
    }

    private void menuCandidato() {
        while (runnigCandidatoOuEmpresa) {
            String comando = terminalInterativoView.retornarComando();

            switch (comando) {
                case "ajuda":
                    terminalInterativoView.exibirAjudaMenuCandidato();
                    break;
                case "info":
                    candidatoView.exibirCandidato(login.candidato);
                    break;
                case "alterar":
                    println "Digite um campo para alterar (nome, sobrenome, cpf, dataNascimento, pais, estado, cep, descricao, add competencias, sub competencias)"
                    String comandoAlterar = terminalInterativoView.retornarComando();
                    switch (comandoAlterar) {
                        case "nome":
                            this.login.candidato = candidatoView.alterarNome(login.candidato);
                            break;
                        case "sobrenome":
                            this.login.candidato = candidatoView.alterarSobrenome(login.candidato);
                            break;
                        case "cpf":
                            this.login.candidato = candidatoView.alterarCPF(login.candidato);
                            break;
                        case "dataNascimento":
                            this.login.candidato = candidatoView.alterarDataNascimento(login.candidato);
                            break;
                        case "pais":
                            this.login.candidato = candidatoView.alterarPais(login.candidato);
                            break;
                        case "estado":
                            this.login.candidato = candidatoView.alterarEstado(login.candidato);
                            break;
                        case "cep":
                            this.login.candidato = candidatoView.alterarCEP(login.candidato);
                            break;
                        case "descricao":
                            this.login.candidato = candidatoView.alterarDescricao(login.candidato);
                            break;
                        case "add competencias":
                            this.login.candidato = candidatoView.adicionarCompetenciasCandidato(login.candidato);
                            break;
                        case "sub competencias":
                            this.login.candidato = candidatoView.removerCompetenciasCandidato(login.candidato);
                            break;
                        default:
                            println "O campo ${comandoAlterar} não pertence ao candidato!"
                    }
                    break;
                case "vaga listar":
                    vagaView.listarVagasParaCandidato(login.candidato);
                    break;
                case "vaga curtir":
                    matchView.curtirVaga(login.candidato);
                    break;
                case "sair":
                    this.runnigCandidatoOuEmpresa = false;
                    break;
                default:
                    terminalInterativoView.exibirOpcaoInvalida();
            }
        }
    }

    private void menuEmpresa() {
        while (runnigCandidatoOuEmpresa) {
            String comando = terminalInterativoView.retornarComando();

            switch (comando) {
                case "ajuda":
                    terminalInterativoView.exibirAjudaMenuEmpresa();
                    break;
                case "info":
                    empresaView.exibirEmpresa(login.empresa);
                    break;
                case "alterar":
                    println "Digite um campo para alterar (nome, cnpj, pais, cep, descricao)"
                    String comandoAlterar = terminalInterativoView.retornarComando();
                    switch (comandoAlterar) {
                        case "nome":
                            this.login.empresa = empresaView.alterarNome(login.empresa);
                            break;
                        case "cnpj":
                            this.login.empresa = empresaView.alterarCnpj(login.empresa);
                            break;
                        case "pais":
                            this.login.empresa = empresaView.alterarPais(login.empresa);
                            break;
                        case "cep":
                            this.login.empresa = empresaView.alterarCep(login.empresa);
                            break;
                        case "descricao":
                            this.login.empresa = empresaView.alterarDescricao(login.empresa);
                            break;
                        default:
                            println "O campo ${comandoAlterar} não pertence ao candidato!"
                    }
                    break;
                case "vaga cadastrar":
                    vagaView.cadastrarVaga(login);
                    break;
                case "vaga listar":
                    vagaView.listarVagasParaEmpresa(login.empresa);
                    break;
                case "vaga alterar":
                    vagaView.alterarVaga(login.empresa);
                    break;
                case "vaga deletar":
                    vagaView.deletarVaga(login.empresa);
                    break;
                case "candidato listar":
                    candidatoView.listarCandidatosParaEmpresa(login.empresa);
                    break;
                case "sair":
                    this.runnigCandidatoOuEmpresa = false;
                    break;
                default:
                    terminalInterativoView.exibirOpcaoInvalida();
            }
        }
    }
}
