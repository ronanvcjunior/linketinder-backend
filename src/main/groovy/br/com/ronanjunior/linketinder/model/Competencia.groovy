package br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Competencia implements Serializable, Cloneable {

    Integer id
    String nome

    Competencia(Integer id, String nome) {
        this.id = id
        this.nome = nome
    }

    Competencia(Map competenciaMap) {
        this.id = competenciaMap.get("id_competencia") as Integer
        this.nome = competenciaMap.get("nome") as String
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
