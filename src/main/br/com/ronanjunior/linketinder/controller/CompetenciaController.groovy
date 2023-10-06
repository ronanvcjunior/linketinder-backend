package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.model.Competencia

class CompetenciaController {
    List<Competencia> competencias;

    CompetenciaController(List<Competencia> competencias) {
        this.competencias = competencias
    }

    Competencia procurarPorNome(String nome) {
        return this.competencias.find(competencia -> {
            return competencia.nome == nome;
        });
    }
}
