package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.controller.CandidatoController
import main.br.com.ronanjunior.linketinder.controller.MatchController
import main.br.com.ronanjunior.linketinder.controller.VagaController
import main.br.com.ronanjunior.linketinder.dto.CandidatoListaDaEmpresaDto
import main.br.com.ronanjunior.linketinder.dto.VagaListaDoCandidadoDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.model.Vaga

class MatchView {
    MatchController matchController = new MatchController()
    VagaView vagaView = new VagaView()
    CandidatoView candidatoView = new CandidatoView()
    Scanner scanner = new Scanner(System.in)
    VagaController vagaController = new VagaController()
    CandidatoController candidatoController = new CandidatoController()

    void curtirVaga(Candidato candidato) {
        Boolean vagaCurtida = false
        List<VagaListaDoCandidadoDto> vagasEncontradas = vagaView.listarVagasParaCandidato(candidato)

        List<VagaListaDoCandidadoDto> vagas = []

        vagasEncontradas.forEach {VagaListaDoCandidadoDto vaga -> {
            vaga.nomeEmpresa = null
            vagas.add(vaga)
        }}

        println "Qual vaga deseja curtir? Selecione o número dela"
        print "Número da vaga: "
        Integer idVaga = scanner.nextInt()
        scanner.nextLine()

        Vaga vagaEncontrada = vagaController.procurarVagaPorId(idVaga)

        VagaListaDoCandidadoDto vaga = new VagaListaDoCandidadoDto(
                vagaEncontrada.id,
                vagaEncontrada.nome,
                vagaEncontrada.descricao,
                null,
                vagaEncontrada.competencias
        )
        if (vagas.contains(vaga)) {
            vagaCurtida = matchController.curtirVaga(candidato, vagaEncontrada)
        } else {
            println "Não exite a vaga de número ${idVaga} registrada\n"
        }

        if(vagaCurtida)
            println "Vaga Curtida com sucesso!"
        else
            println "Não foi possível curtir a vaga!"

    }

    void curtirCandidato(Empresa empresa) {
        Boolean candidatoCurtido = false

        List<CandidatoListaDaEmpresaDto> candidatosEncontrados = candidatoView.listarCandidatosParaEmpresa(empresa)

        List<CandidatoListaDaEmpresaDto> candidatos = []

        candidatosEncontrados.forEach {CandidatoListaDaEmpresaDto candidato -> {
            candidato.nomeCompleto  = null

            candidatos.add(candidato)
        }}

        println "Qual candidato deseja curtir? Selecione o número dele"
        print "Número do candidato: "
        Integer idCandidato = scanner.nextInt()
        scanner.nextLine()

        Candidato candidatoEncontrado = candidatoController.procurarCandidatoPorId(idCandidato)

        CandidatoListaDaEmpresaDto candidato = new CandidatoListaDaEmpresaDto(
                candidatoEncontrado.id,
                null,
                candidatoEncontrado.competencias
        )

        if (candidatos.contains(candidato)) {
            List<Vaga> vagas = vagaView.listarVagasParaEmpresa(empresa)

            println "Para qual vaga deseja curtir o candidato? Selecione o número dela"
            print "Número da vaga: "
            Integer idVaga = scanner.nextInt()
            scanner.nextLine()

            Vaga vaga = vagaController.procurarVagaDaEmpresaPorId(idVaga, empresa.id)
            if (vagas.contains(vaga)) {
                candidatoCurtido = matchController.curtirCandidato(candidatoEncontrado, vaga)
            } else {
                println "Não exite a vaga de número ${idVaga} registrada\n"
            }
        } else {
            println "Não exite o candidato de número ${idCandidato} registrada\n"
        }

        if(candidatoCurtido)
            println "Vaga Curtida com sucesso!"
        else
            println "Não foi possível curtir a vaga!"

    }


}
