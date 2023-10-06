package main.br.com.ronanjunior.linketinder.dto

import main.br.com.ronanjunior.linketinder.model.Competencia

class CandidatoListaDaEmpresaDto {
    Integer id;
    String nomeCompleto;
    List<Competencia> competencias = [];

    CandidatoListaDaEmpresaDto() {
    }

    CandidatoListaDaEmpresaDto(Integer id, String nomeCompleto, List<Competencia> competencias) {
        this.id = id
        this.nomeCompleto = nomeCompleto
        this.competencias = competencias
    }
}
