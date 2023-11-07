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

    Candidato() {
    }

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
}
