package main.br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Vaga implements Serializable {

    Integer id;
    String nome;
    String descricao;
    String estado;
    String cidade;
    Empresa empresa;
    List<Competencia> competencias = [];
}
