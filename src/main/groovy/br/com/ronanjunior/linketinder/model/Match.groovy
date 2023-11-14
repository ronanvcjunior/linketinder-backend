package br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

import java.time.LocalDate

@EqualsAndHashCode
class Match implements Serializable {

    Integer id
    LocalDate dataCurtidaCandidato
    LocalDate dataCurtidaVaga
    Candidato candidato
    Vaga vaga

    Match() {
    }

    Match(Integer id, LocalDate dataCurtidaCandidato, LocalDate dataCurtidaVaga, Candidato candidato, Vaga vaga) {
        this.id = id
        this.dataCurtidaCandidato = dataCurtidaCandidato
        this.dataCurtidaVaga = dataCurtidaVaga
        this.candidato = candidato
        this.vaga = vaga
    }
}
