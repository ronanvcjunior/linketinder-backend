package br.com.ronanjunior.linketinder.dto

import br.com.ronanjunior.linketinder.model.Competencia

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class VagaListaDoCandidatoDto {
    Integer id
    String nome
    String descricao
    String nomeEmpresa
    List<Competencia> competencias = []

    VagaListaDoCandidatoDto(
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

    VagaListaDoCandidatoDto(Map vagaMap, List<Competencia> competencias) {
        this.id = vagaMap.get("id_vaga") as Integer
        this.nome = vagaMap.get("nome") as String
        this.descricao = vagaMap.get("descricao") as String
        this.nomeEmpresa = vagaMap.get("empresa") as String
        this.competencias = competencias
    }

    VagaListaDoCandidatoDto(Map vagaMap) {
        this.id = vagaMap.get("id_vaga") as Integer
        this.nome = vagaMap.get("nome") as String
        this.descricao = vagaMap.get("descricao") as String
        this.nomeEmpresa = vagaMap.get("empresa") as String
    }
}
