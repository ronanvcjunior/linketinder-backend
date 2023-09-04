package main.br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Competencia {
    String nome;


    @Override
    String toString() {
        return nome;
    }
}
