package main.br.com.ronanjunior.linketinder.dto

class MatchComIdVagaEIdCandidatoDto {
    Integer id;
    Date dataCurtidaCandidato;
    Date dataCurtidaVaga;
    Integer idCandidato;
    Integer idVaga;

    MatchComIdVagaEIdCandidatoDto(Integer id, Date dataCurtidaCandidato, Date dataCurtidaVaga, Integer idCandidato, Integer idVaga) {
        this.id = id
        this.dataCurtidaCandidato = dataCurtidaCandidato
        this.dataCurtidaVaga = dataCurtidaVaga
        this.idCandidato = idCandidato
        this.idVaga = idVaga
    }
}
