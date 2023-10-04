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
}
