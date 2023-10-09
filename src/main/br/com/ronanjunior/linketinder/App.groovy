package main.br.com.ronanjunior.linketinder

import main.br.com.ronanjunior.linketinder.controller.TerminalInterativoController

class App {
    static TerminalInterativoController terminalInterativoController = new TerminalInterativoController()

    static void main(String[] args) {
        terminalInterativoController.iniciar()
    }
}
