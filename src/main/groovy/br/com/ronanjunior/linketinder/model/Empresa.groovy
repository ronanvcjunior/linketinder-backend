package br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Empresa implements Serializable, Cloneable {

    Integer id
    String nome
    String cnpj
    String pais
    String cep
    String descricao

    Empresa() {}

    Empresa(Integer id, String nome, String cnpj, String pais, String cep, String descricao) {
        this.id = id
        this.nome = nome
        this.cnpj = cnpj
        this.pais = pais
        this.cep = cep
        this.descricao = descricao
    }

    @Override
    Empresa clone() throws CloneNotSupportedException {
        return super.clone() as Empresa
    }
}
