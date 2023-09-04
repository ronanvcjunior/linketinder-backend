package main.br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Candidato extends Pessoa {
    String cpf;
    Integer idade;
    List<Competencia> competencias = [];
}
