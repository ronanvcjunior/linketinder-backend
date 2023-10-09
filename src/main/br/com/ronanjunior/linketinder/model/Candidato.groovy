package main.br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Candidato implements Serializable {

    Integer id
    String nome
    String sobrenome
    String cpf
    Date dataNascimento
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
            Date dataNascimento,
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
