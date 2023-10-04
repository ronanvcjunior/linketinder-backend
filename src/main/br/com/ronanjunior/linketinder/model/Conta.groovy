package main.br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Conta implements Serializable {

    Integer id;
    String email;
    String senha;
    Candidato candidato;
    Empresa empresa;
}
