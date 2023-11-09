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

    Match(Integer id, LocalDate dataCurtidaCandidato, LocalDate dataCurtidaVaga, Candidato candidato, Vaga vaga) {
        this.id = id
        this.dataCurtidaCandidato = dataCurtidaCandidato
        this.dataCurtidaVaga = dataCurtidaVaga
        this.candidato = candidato
        this.vaga = vaga
    }

    Match(Map matchMap, Candidato candidato, Vaga vaga) {
        this.id = matchMap.get("id_match") as Integer
        this.dataCurtidaCandidato = matchMap.get("data_curtida_candidato") ? LocalDate.parse(matchMap.get("data_curtida_candidato") as String) : null
        this.dataCurtidaVaga = matchMap.get("data_curtida_vaga") ? LocalDate.parse(matchMap.get("data_curtida_vaga") as String) : null
        this.candidato = candidato
        this.vaga = vaga
    }

    Match(Map matchMap) {
        this.id = matchMap.get("id_match") as Integer
        this.dataCurtidaCandidato = matchMap.get("data_curtida_candidato") ? LocalDate.parse(matchMap.get("data_curtida_candidato") as String) : null
        this.dataCurtidaVaga = matchMap.get("data_curtida_vaga") ? LocalDate.parse(matchMap.get("data_curtida_vaga") as String) : null
    }
}
