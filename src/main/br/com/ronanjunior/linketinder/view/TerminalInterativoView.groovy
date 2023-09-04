package main.br.com.ronanjunior.linketinder.view

class TerminalInterativoView {
    void exibirAjuda() {
        print   "Opções disponíveis:\n" +
                " - ajuda: Mostra todas as opções disponíveis\n" +
                " - candidato cadastrar: Para cadastrar um novo candidato\n" +
                " - candidato listar: Mostra todos os candidatos\n" +
                " - empresa cadastrar: Para cadastrar uma nova empresa\n" +
                " - empresa listar: Mostra todas as empresas\n" +
                " - sair: Para sair do programa\n" +
                "\n";
    }

    void exibirOpcaoInvalida() {
        print   "Opção inválida\n" +
                " digite ajuda, para mostrar todas as opções disponíveis\n" +
                "\n";
    }
}
