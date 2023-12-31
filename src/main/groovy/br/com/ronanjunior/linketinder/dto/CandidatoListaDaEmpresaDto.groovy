package br.com.ronanjunior.linketinder.dto

import br.com.ronanjunior.linketinder.model.Competencia
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class CandidatoListaDaEmpresaDto {
    Integer id
    String nomeCompleto
    List<Competencia> competencias = []

    CandidatoListaDaEmpresaDto() {
    }

    CandidatoListaDaEmpresaDto(Integer id, String nomeCompleto, List<Competencia> competencias) {
        this.id = id
        this.nomeCompleto = nomeCompleto
        this.competencias = competencias
    }
}
