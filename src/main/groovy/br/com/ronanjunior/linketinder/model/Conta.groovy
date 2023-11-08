package br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Conta implements Serializable {

    Integer id
    String email
    String senha
    Candidato candidato
    Empresa empresa

    Conta(Integer id, String email, String senha, Candidato candidato, Empresa empresa) {
        this.id = id
        this.email = email
        this.senha = senha
        this.candidato = candidato
        this.empresa = empresa
    }

    Conta(Map contaMap, Candidato candidato, Empresa empresa) {
        this.id = contaMap.get("id_conta") as Integer
        this.email = contaMap.get("email") as String
        this.senha = contaMap.get("senha") as String
        this.candidato = candidato
        this.empresa = empresa
    }

    Conta(Map contaMap) {
        this.id = contaMap.get("id_conta") as Integer
        this.email = contaMap.get("email") as String
        this.senha = contaMap.get("senha") as String
    }
}
