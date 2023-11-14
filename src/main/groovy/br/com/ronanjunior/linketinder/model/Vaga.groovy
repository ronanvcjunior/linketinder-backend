package br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Vaga implements Serializable, Cloneable {

    Integer id
    String nome
    String descricao
    String estado
    String cidade
    Empresa empresa
    List<Competencia> competencias = []

    Vaga() {
    }

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

    @Override
    Vaga clone() {
        Vaga clonedVaga = (Vaga) super.clone()
        clonedVaga.empresa = empresa?.clone()
        clonedVaga.competencias = competencias.collect { it.clone() }
        return clonedVaga
    }
}
