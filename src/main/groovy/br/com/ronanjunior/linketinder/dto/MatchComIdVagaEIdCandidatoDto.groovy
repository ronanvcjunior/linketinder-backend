package br.com.ronanjunior.linketinder.dto

import java.time.LocalDate

class MatchComIdVagaEIdCandidatoDto {
    Integer idMatch
    LocalDate dataCurtidaCandidato
    LocalDate dataCurtidaVaga
    Integer idCandidato
    Integer idVaga

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

    MatchComIdVagaEIdCandidatoDto(Map matchMap) {
        this.idMatch = matchMap.get("id_match") as Integer
        this.dataCurtidaCandidato = matchMap.get("data_curtida_candidato") ? LocalDate.parse(matchMap.get("data_curtida_candidato") as String) : null
        this.dataCurtidaVaga = matchMap.get("data_curtida_vaga") ? LocalDate.parse(matchMap.get("data_curtida_vaga") as String) : null
        this.idCandidato = matchMap.get("id_candidato") as Integer
        this.idVaga = matchMap.get("id_vaga") as Integer
    }
}
