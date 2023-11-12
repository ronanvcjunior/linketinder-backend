package br.com.ronanjunior.linketinder

import br.com.ronanjunior.linketinder.view.TerminalInterativoView

class App {
    static TerminalInterativoView terminalInterativoView = new TerminalInterativoView()

    static void main(String[] args) {
        terminalInterativoView.iniciar()
    }
}
