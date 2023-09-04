package main.br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Empresa extends Pessoa {
    String cnpj;
    List<Competencia> competenciasEsperadas = [];
}
