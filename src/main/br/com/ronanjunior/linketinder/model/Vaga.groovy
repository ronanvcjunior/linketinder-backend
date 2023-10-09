package main.br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Vaga implements Serializable {

    Integer id
    String nome
    String descricao
    String estado
    String cidade
    Empresa empresa
    List<Competencia> competencias = []

    Vaga(
            Integer id,
            String nome,
            String descricao,
            String estado,
            String cidade,
            Empresa empresa,
            List<Competencia> competencias
    ) {
        this.id = id
        this.nome = nome
        this.descricao = descricao
        this.estado = estado
        this.cidade = cidade
        this.empresa = empresa
        this.competencias = competencias
    }
}
