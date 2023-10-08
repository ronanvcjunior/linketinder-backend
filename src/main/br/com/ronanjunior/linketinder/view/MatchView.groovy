package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.controller.MatchController
import main.br.com.ronanjunior.linketinder.controller.VagaController
import main.br.com.ronanjunior.linketinder.dto.VagaListaDoCandidadoDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.model.Vaga

class MatchView {
    MatchController matchController = new MatchController();
    VagaView vagaView = new VagaView();
    Scanner scanner = new Scanner(System.in);
    VagaController vagaController = new VagaController();

    void curtirVaga(Candidato candidato) {
        Boolean vagaCurtida = false;
        List<VagaListaDoCandidadoDto> vagasEncontradas = vagaView.listarVagasParaCandidato(candidato);

        List<VagaListaDoCandidadoDto> vagas = [];

        vagasEncontradas.forEach {VagaListaDoCandidadoDto vaga -> {
            vaga.nomeEmpresa = null;
            vagas.add(vaga);
        }}

        println "Qual vaga deseja curtir? Selecione o número dela"
        print "Número da vaga: ";
        Integer idVaga = scanner.nextInt();
        scanner.nextLine();

        Vaga vagaEncontrada = vagaController.procurarVagaPorId(idVaga);

        VagaListaDoCandidadoDto vaga = new VagaListaDoCandidadoDto(
                vagaEncontrada.id,
                vagaEncontrada.nome,
                vagaEncontrada.descricao,
                null,
                vagaEncontrada.competencias
        )
        if (vagas.contains(vaga)) {
            vagaCurtida = matchController.curtirVaga(candidato, vagaEncontrada);
        } else {
            println "Não exite a vaga de número ${idVaga} registrada\n"
        }

        if(vagaCurtida)
            println "Vaga Curtida com sucesso!"
        else
            println "Não foi possível curtir a vaga!"

    }


}
