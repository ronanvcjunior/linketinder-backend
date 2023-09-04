package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia

class CandidatoView {
    List<Candidato> candidatos;
    Scanner scanner = new Scanner(System.in);
    List<Competencia> competenciasCadastradas = [];

    void exibirCandidatos() {
        candidatos.each {candidato ->
            print   "(" +
                    " nome: " + candidato.getNome() + "," +
                    " email: " + candidato.getEmail() + "," +
                    " cpf: " + candidato.getCpf() + "," +
                    " idade: " + candidato.getIdade() + "," +
                    " pais: " + candidato.getPais() + "," +
                    " estado: " + candidato.getEstado() + "," +
                    " cep: " + candidato.getCep() + "," +
                    " competências: " + candidato.getCompetencias() + "," +
                    " descrição: " + candidato.getDescricao() +
                    ")\n";
        }
    }

    Candidato cadastrarCandidato() {
        println("Cadastro de um novo usuário:")

        print "Nome: ";
        String nome = scanner.nextLine();
        print "Email: ";
        String email = scanner.nextLine();
        print "CPF: ";
        String cpf = scanner.nextLine();
        print "Idade: ";
        Integer idade = scanner.nextInt();
        scanner.nextLine();
        print "Pais: ";
        String pais = scanner.nextLine();
        print "Estado: ";
        String estado = scanner.nextLine();
        print "CEP: ";
        String cep = scanner.nextLine();

        List<Competencia> competencias = [];
        while (true) {
            print "Competência (ou deixe em branco para encerrar): ";
            String nomeCompetencia = scanner.nextLine();
            Competencia competencia = new Competencia(nome: nomeCompetencia);
            if (nomeCompetencia.isEmpty()) {
                break;
            }
            if (competenciasCadastradas.contains(competencia)) {
                competencias.add(competencia);
            } else {
                println("a competência ${nomeCompetencia} não está presente na lista de competências");
            }

        }

        print "Descrição: ";
        String descricao = scanner.nextLine();

        return new Candidato(
                nome: nome,
                email: email,
                cpf: cpf,
                idade: idade,
                pais: pais,
                estado: estado,
                cep: cep,
                competencias: competencias,
                descricao: descricao
        );
    }
}
