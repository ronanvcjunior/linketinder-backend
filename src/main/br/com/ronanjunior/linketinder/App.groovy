package main.br.com.ronanjunior.linketinder

import main.br.com.ronanjunior.linketinder.controller.CandidatoController
import main.br.com.ronanjunior.linketinder.controller.CompetenciaController
import main.br.com.ronanjunior.linketinder.controller.EmpresaController
import main.br.com.ronanjunior.linketinder.controller.TerminalInterativoController
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.view.CandidatoView
import main.br.com.ronanjunior.linketinder.view.EmpresaView

class App {
    static TerminalInterativoController terminalInterativoController = new TerminalInterativoController();

    static void main(String[] args) {
        terminalInterativoController.iniciar();
    }
}
