package br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Empresa implements Serializable, Cloneable {

    Integer id
    String nome
    String cnpj
    String pais
    String cep
    String descricao

    Empresa(Integer id, String nome, String cnpj, String pais, String cep, String descricao) {
        this.id = id
        this.nome = nome
        this.cnpj = cnpj
        this.pais = pais
        this.cep = cep
        this.descricao = descricao
    }

    Empresa(Map empresaMap) {
        this.id = empresaMap.get("id_empresa") as Integer
        this.nome = empresaMap.get("nome") as String
        this.cnpj = empresaMap.get("cnpj") as String
        this.pais = empresaMap.get("pais") as String
        this.cep = empresaMap.get("cep") as String
        this.descricao = empresaMap.get("descricao") as String
    }

    @Override
    Empresa clone() throws CloneNotSupportedException {
        return super.clone() as Empresa
    }
}
