package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.model.Candidato

class CandidatoView {
    List<Candidato> candidatos;

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
}
