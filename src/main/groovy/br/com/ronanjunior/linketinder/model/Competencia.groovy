package br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Competencia implements Serializable, Cloneable {

    Integer id
    String nome

    Competencia() {}

    Competencia(Integer id, String nome) {
        this.id = id
        this.nome = nome
    }

    @Override
    protected Competencia clone() throws CloneNotSupportedException {
        return super.clone() as Competencia
    }

    @Override
    String toString() {
        return nome
    }
}
