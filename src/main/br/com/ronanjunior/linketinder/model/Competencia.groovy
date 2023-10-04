package main.br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Competencia implements Serializable {

    Integer id;
    String nome;

    Competencia(Integer id, String nome) {
        this.id = id
        this.nome = nome
    }

    @Override
    String toString() {
        return nome;
    }
}
