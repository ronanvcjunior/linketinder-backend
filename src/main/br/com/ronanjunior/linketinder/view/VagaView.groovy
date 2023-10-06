package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.controller.CompetenciaController
import main.br.com.ronanjunior.linketinder.controller.VagaController
import main.br.com.ronanjunior.linketinder.dto.VagaListaDoCandidadoDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia

class VagaView {

    VagaController vagaController = new VagaController();

    void listarVagasParaCandidato(Candidato candidato) {
        List<VagaListaDoCandidadoDto> vagas = vagaController.listarTodasVagasParaCandidato(candidato);

        vagas.forEach {VagaListaDoCandidadoDto vaga -> {
            println "" +
                    "Vaga de número ${vaga.id}: " +
                    "[Nome: ${vaga.nome}, " +
                    "Empresa: ${vaga.nomeEmpresa}, " +
                    "Competências desejadas: ${vaga.competencias}, " +
                    "Descrição: ${vaga.descricao}]" +
                    "";
        }}
    }

}
