package br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Vaga implements Serializable {

    Integer id
    String nome
    String descricao
    String estado
    String cidade
    Empresa empresa
    List<Competencia> competencias = []

    Vaga(
            Integer id,
            String nome,
            String descricao,
            String estado,
            String cidade,
            Empresa empresa,
            List<Competencia> competencias
    ) {
        this.id = id
        this.nome = nome
        this.descricao = descricao
        this.estado = estado
        this.cidade = cidade
        this.empresa = empresa
        this.competencias = competencias
    }

    Vaga(Map vagaMap, Empresa empresa, List<Competencia> competencias) {
        this.id = vagaMap.get("id_vaga") as Integer
        this.nome = vagaMap.get("nome") as String
        this.descricao = vagaMap.get("descricao") as String
        this.estado = vagaMap.get("estado") as String
        this.cidade = vagaMap.get("cidade") as String
        this.empresa = empresa
        this.competencias = competencias
    }

    Vaga(Map vagaMap, List<Competencia> competencias) {
        this.id = vagaMap.get("id_vaga") as Integer
        this.nome = vagaMap.get("nome") as String
        this.descricao = vagaMap.get("descricao") as String
        this.estado = vagaMap.get("estado") as String
        this.cidade = vagaMap.get("cidade") as String
        this.competencias = competencias
    }

    Vaga(Map vagaMap, Empresa empresa) {
        this.id = vagaMap.get("id_vaga") as Integer
        this.nome = vagaMap.get("nome") as String
        this.descricao = vagaMap.get("descricao") as String
        this.estado = vagaMap.get("estado") as String
        this.cidade = vagaMap.get("cidade") as String
        this.empresa = empresa
    }

    Vaga(Map vagaMap) {
        this.id = vagaMap.get("id_vaga") as Integer
        this.nome = vagaMap.get("nome") as String
        this.descricao = vagaMap.get("descricao") as String
        this.estado = vagaMap.get("estado") as String
        this.cidade = vagaMap.get("cidade") as String
    }
}
