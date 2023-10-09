package main.br.com.ronanjunior.linketinder.view

class TerminalInterativoView {
    Scanner scanner = new Scanner(System.in);

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

    void exibirAjudaMenuCandidato() {
        print   "Opções disponíveis:\n" +
                " - ajuda: Mostra todas as opções disponíveis\n" +
                " - info: Para mostrar as suas informações\n" +
                " - alterar: Para alterar as suas informações\n" +
                " - vaga listar: Mostra todas as vagas\n" +
                " - vaga curtir: Para curtir uma vaga\n" +
                " - sair: Para sair do programa\n" +
                "\n";
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
                "\n";
    }

    void exibirAjudaInicio() {
        print   "Opções disponíveis:\n" +
                " - ajuda: Mostra todas as opções disponíveis\n" +
                " - candidato cadastrar: Para cadastrar um novo candidato\n" +
                " - empresa cadastrar: Para cadastrar uma nova empresa\n" +
                " - login: Para entrar na conta\n" +
                " - sair: Para sair do programa\n" +
                "\n";
    }

    void exibirOpcaoInvalida() {
        print   "Opção inválida\n" +
                " digite 'ajuda', para mostrar todas as opções disponíveis\n" +
                "\n";
    }

    String retornarComando() {
        print ">>> ";
        String comando = scanner.nextLine();

        return comando;
    }
}
