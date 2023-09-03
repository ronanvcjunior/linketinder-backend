package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.model.Candidato

class CandidatoController {
    List<Candidato> candidatos = [];

    void adicionarCandidato(Candidato candidato) {
        candidatos.add(candidato);
    }
}
