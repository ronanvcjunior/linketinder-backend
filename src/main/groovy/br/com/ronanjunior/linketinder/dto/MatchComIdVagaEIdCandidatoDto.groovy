package br.com.ronanjunior.linketinder.dto

import groovy.transform.EqualsAndHashCode

import java.time.LocalDate

@EqualsAndHashCode
class MatchComIdVagaEIdCandidatoDto {
    Integer idMatch
    LocalDate dataCurtidaCandidato
    LocalDate dataCurtidaVaga
    Integer idCandidato
    Integer idVaga

    MatchComIdVagaEIdCandidatoDto() {
    }

    MatchComIdVagaEIdCandidatoDto(
            Integer idMatch,
            LocalDate dataCurtidaCandidato,
            LocalDate dataCurtidaVaga,
            Integer idCandidato,
            Integer idVaga
    ) {
        this.idMatch = idMatch
        this.dataCurtidaCandidato = dataCurtidaCandidato
        this.dataCurtidaVaga = dataCurtidaVaga
        this.idCandidato = idCandidato
        this.idVaga = idVaga
    }
}
