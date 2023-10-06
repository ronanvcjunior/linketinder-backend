package main.br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Empresa implements Serializable {

    Integer id;
    String nome;
    String cnpj;
    String pais;
    String cep;
    String descricao;

    Empresa(Integer id, String nome, String cnpj, String pais, String cep, String descricao) {
        this.id = id
        this.nome = nome
        this.cnpj = cnpj
        this.pais = pais
        this.cep = cep
        this.descricao = descricao
    }
}
