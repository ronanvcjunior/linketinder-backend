package br.com.ronanjunior.linketinder.dto

import br.com.ronanjunior.linketinder.model.Competencia

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class VagaListaDoCandidadoDto {
    Integer id
    String nome
    String descricao
    String nomeEmpresa
    List<Competencia> competencias = []

    VagaListaDoCandidadoDto(
            Integer id,
            String nome,
            String descricao,
            String nomeEmpresa,
            List<Competencia> competencias
    ) {
        this.id = id
        this.nome = nome
        this.descricao = descricao
        this.nomeEmpresa = nomeEmpresa
        this.competencias = competencias
    }
}
