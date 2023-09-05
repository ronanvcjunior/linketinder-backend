package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.view.CandidatoView
import main.br.com.ronanjunior.linketinder.view.EmpresaView
import main.br.com.ronanjunior.linketinder.view.TerminalInterativoView

class TerminalInterativoController {
    Boolean running = true;
    TerminalInterativoView terminalInterativoView = new TerminalInterativoView();
    CandidatoController candidatoController = new CandidatoController();
    CandidatoView candidatoView = new CandidatoView();
    EmpresaController empresaController = new EmpresaController();
    EmpresaView empresaView = new EmpresaView();

    void iniciar() {
        println "Digite um comando ('ajuda' para listar as opções, 'sair' para sair do programa):"

        while (running) {
            String comando = terminalInterativoView.retornarComando();

            switch (comando) {
                case "ajuda":
                    terminalInterativoView.exibirAjuda();
                    break;
                case "candidato cadastrar":
                    Candidato candidato = candidatoView.cadastrarCandidato();
                    candidatoController.adicionarCandidato(candidato);
                    candidatoView.candidatos = candidatoController.candidatos;
                    break;
                case "candidato listar":
                    candidatoView.exibirCandidatos();
                    break;
                case "empresa cadastrar":
                    Empresa empresa = empresaView.cadastrarEmpresa();
                    empresaController.adicionarEmpresa(empresa);
                    empresaView.empresas = empresaController.empresas;
                    break;
                case "empresa listar":
                    empresaView.exibirEmpresas();
                    break;
                case "sair":
                    running = false;
                    break;
                default:
                    terminalInterativoView.exibirOpcaoInvalida();
            }
        }
    }
}
