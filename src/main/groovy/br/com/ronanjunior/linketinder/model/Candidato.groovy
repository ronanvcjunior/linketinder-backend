package br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

import java.time.LocalDate

@EqualsAndHashCode
class Candidato implements Serializable {

    Integer id
    String nome
    String sobrenome
    String cpf
    LocalDate dataNascimento
    String pais
    String estado
    String cep
    String descricao
    List<Competencia> competencias = []

    Candidato(
            Integer id,
            String nome,
            String sobrenome,
            String cpf,
            LocalDate dataNascimento,
            String pais,
            String estado,
            String cep,
            String descricao,
            List<Competencia> competencias
    ) {
        this.id = id
        this.nome = nome
        this.sobrenome = sobrenome
        this.cpf = cpf
        this.dataNascimento = dataNascimento
        this.pais = pais
        this.estado = estado
        this.cep = cep
        this.descricao = descricao
        this.competencias = competencias
    }

    Candidato(Map candidatoMap, List<Competencia> competencias) {
        this.id = candidatoMap.get("id_candidato") as Integer
        this.nome = candidatoMap.get("nome") as String
        this.sobrenome = candidatoMap.get("sobrenome") as String
        this.cpf = candidatoMap.get("cpf") as String
        this.dataNascimento = candidatoMap.get("data_nascimento") ? LocalDate.parse(candidatoMap.get("data_nascimento") as String) : null
        this.pais = candidatoMap.get("pais") as String
        this.estado = candidatoMap.get("estado") as String
        this.cep = candidatoMap.get("cep") as String
        this.descricao = candidatoMap.get("descricao") as String
        this.competencias = competencias
    }

    Candidato(Map candidatoMap) {
        this.id = candidatoMap.get("id_candidato") as Integer
        this.nome = candidatoMap.get("nome") as String
        this.sobrenome = candidatoMap.get("sobrenome") as String
        this.cpf = candidatoMap.get("cpf") as String
        this.dataNascimento = LocalDate.parse(candidatoMap.get("data_nascimento") as String)
        this.pais = candidatoMap.get("pais") as String
        this.estado = candidatoMap.get("estado") as String
        this.cep = candidatoMap.get("cep") as String
        this.descricao = candidatoMap.get("descricao") as String
    }
}
