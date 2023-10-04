package main.br.com.ronanjunior.linketinder.model

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Match implements Serializable {

    Integer id;
    Date dataCurtidaCandidato;
    Date dataCurtidaVaga;
    Candidato candidato;
    Vaga vaga;
}
