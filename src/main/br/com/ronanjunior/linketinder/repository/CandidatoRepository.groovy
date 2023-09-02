package main.br.com.ronanjunior.linketinder.repository

import main.br.com.ronanjunior.linketinder.model.Candidato

class CandidatoRepository {
    List<Candidato> candidatos = [];

    void adicionarCandidato(Candidato candidato) {
        candidatos.add(candidato);
    }
}
