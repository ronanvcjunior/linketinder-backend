package br.com.ronanjunior.linketinder.view

import br.com.ronanjunior.linketinder.controller.CompetenciaController
import br.com.ronanjunior.linketinder.controller.VagaCompetenciaController
import br.com.ronanjunior.linketinder.controller.VagaController
import br.com.ronanjunior.linketinder.dto.VagaListaDoCandidatoDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.model.Vaga

class VagaView {
    VagaController vagaController = new VagaController()
    VagaCompetenciaController vagaCompetenciaController = new VagaCompetenciaController()
    Scanner scanner = new Scanner(System.in)

    List<Competencia> competenciasCadastradas = []

    CompetenciaController competenciaController

    VagaView() {}


    VagaView(List<Competencia> competenciasCadastradas) {
        this.competenciasCadastradas = competenciasCadastradas
        this.competenciaController = new CompetenciaController()
    }

    Vaga cadastrarVaga(Conta login) {
        println("Cadastro de um nova vaga:")

        String nome
        String estado
        String cidade

        while (true) {
            print "Nome: "
            nome = scanner.nextLine()
            nome = nome.trim()
            if (nome)
                break
            else
                println "Nome Inválido"
        }

        while (true) {
            print "Estado: "
            estado = scanner.nextLine()
            estado = estado.trim()
            if (estado)
                break
            else
                println "Estado Inválido"
        }

        while (true) {
            print "Cidade: "
            cidade = scanner.nextLine()
            cidade = cidade.trim()
            if (cidade)
                break
            else
                println "Cidade Inválido"
        }

        this.competenciasCadastradas.forEach {Competencia competencia -> {
            println "${competencia.id}: ${competencia.nome}"
        }}
        List<Competencia> competencias = []
        while (true) {
            print "Nome da competência (ou deixe em branco para encerrar): "
            String nomeCompetencia = scanner.nextLine()
            if (nomeCompetencia.isEmpty()) {
                break
            }
            Competencia competencia = competenciasCadastradas.find{ it.nome == nomeCompetencia}
            if (competencias.contains(competencia)) {
                println "a competência ${nomeCompetencia} já está na sua lista de competências\n"
            } else if (competenciasCadastradas.contains(competencia)) {
                competencias.add(competencia)
            } else {
                println "a competência ${nomeCompetencia} não está presente na lista de competências\n" +
                        "${competenciasCadastradas}"
            }

        }

        print "Descrição: "
        String descricao = scanner.nextLine()
        descricao = descricao.trim()

        Vaga novaVaga = new Vaga(
                null,
                nome,
                descricao,
                estado,
                cidade,
                login.empresa,
                competencias
        )

        return vagaController.cadastrarVaga(novaVaga)
    }

    void alterarVaga(Empresa empresa) {
        List<Vaga> vagas = this.listarVagasParaEmpresa(empresa)

        println "Qual vaga deseja alterar? Selecione o número dela"
        print "Número da vaga: "
        Integer idVaga = scanner.nextInt()
        scanner.nextLine()

        Vaga vaga = vagaController.buscarVagaPorId(idVaga)
        if (vagas.contains(vaga)) {
            println "Digite um campo para alterar (nome, esdato, cidade, descricao, add competencias, sub competencias)"
            print ">>> "
            String comandoAlterar = scanner.nextLine()
            switch (comandoAlterar) {
                case "nome":
                    this.alterarNome(vaga)
                    break
                case "esdato":
                    this.alterarEstado(vaga)
                    break
                case "cidade":
                    this.alterarCidade(vaga)
                    break
                case "descricao":
                    this.alterarDescricao(vaga)
                    break
                case "add competencias":
                    this.adicionarCompetenciasVaga(vaga)
                    break
                case "sub competencias":
                    this.removerCompetenciasVaga(vaga)
                    break
                default:
                    println "O campo ${comandoAlterar} não pertence a vaga!"
            }
        } else {
            println "Não exite a vaga de número ${idVaga} registrada\n"
        }

    }

    Vaga alterarNome(Vaga vaga) {
        Vaga vagaAlterado = vaga.clone()

        String nome

        print "Nome Atual: ${vaga.nome}\n"
        while (true) {
            print "Nome Novo: "
            nome = scanner.nextLine()
            nome = nome.trim()
            if (nome)
                break
            else
                println "Nome Inválido"
        }

        vagaAlterado.nome = nome

        Boolean alterado = vagaController.alterarVaga(vagaAlterado)

        if (alterado)
            return vagaAlterado
        else
            return vaga
    }

    Vaga alterarEstado(Vaga vaga) {
        Vaga vagaAlterado = vaga.clone()

        String estado

        print "Estado Atual: ${vaga.estado}\n"
        while (true) {
            print "Estado Novo: "
            estado = scanner.nextLine()
            estado = estado.trim()
            if (estado)
                break
            else
                println "Estado Inválido"
        }

        vagaAlterado.estado = estado

        Boolean alterado = vagaController.alterarVaga(vagaAlterado)

        if (alterado)
            return vagaAlterado
        else
            return vaga
    }

    Vaga alterarCidade(Vaga vaga) {
        Vaga vagaAlterado = vaga.clone()

        String cidade

        print "Cidade Atual: ${vaga.cidade}\n"
        while (true) {
            print "Cidade Nova: "
            cidade = scanner.nextLine()
            cidade = cidade.trim()
            if (cidade)
                break
            else
                println "Cidade Inválida"
        }

        vagaAlterado.cidade = cidade

        Boolean alterado = vagaController.alterarVaga(vagaAlterado)

        if (alterado)
            return vagaAlterado
        else
            return vaga
    }

    Vaga alterarDescricao(Vaga vaga) {
        Vaga vagaAlterado = vaga.clone()

        print "Descricao Atual: ${vaga.descricao}\n"
        print "Descricao Novo: "
        String descricao = scanner.nextLine()

        vagaAlterado.descricao = descricao

        Boolean alterado = vagaController.alterarVaga(vagaAlterado)

        if (alterado)
            return vagaAlterado
        else
            return vaga
    }

    Vaga adicionarCompetenciasVaga(Vaga vaga) {
        Vaga vagaAlterado = vaga.clone()


        this.competenciasCadastradas.forEach {Competencia competencia -> {
            println "${competencia.id}: ${competencia.nome}"
        }}
        print "Competências Atuais: ${vaga.competencias}\n"
        List<Competencia> competencias = vaga.competencias
        while (true) {
            print "Nome da competência (ou deixe em branco para encerrar): "
            String nomeCompetencia = scanner.nextLine()
            if (nomeCompetencia.isEmpty()) {
                break
            }
            Competencia competencia = competenciasCadastradas.find{ it.nome == nomeCompetencia}
            if (competencias.contains(competencia)) {
                println "a competência ${nomeCompetencia} já está na sua lista de competências\n"
            } else if (competenciasCadastradas.contains(competencia)) {
                competencias.add(competencia)
            } else {
                println "a competência ${nomeCompetencia} não está presente na lista de competências\n" +
                        "${competenciasCadastradas}"
            }

        }

        vagaAlterado.competencias = competencias

        Boolean alterado = vagaCompetenciaController.cadastrarCompetenciasParaVaga(vagaAlterado.id, vagaAlterado.competencias.collect { it.id })

        if (alterado)
            return vagaAlterado
        else
            return vaga
    }

    Vaga removerCompetenciasVaga(Vaga vaga) {
        List<Competencia> competenciasParaExcluir = []
        Vaga vagaAlterado = vaga.clone()

        print "Competências Atuais: ${vagaAlterado.competencias}\n"
        while (true) {
            print "Nome da competência (ou deixe em branco para encerrar): "
            String nomeCompetencia = scanner.nextLine()
            if (nomeCompetencia.isEmpty()) {
                break
            }
            Competencia competencia = competenciasCadastradas.find{ it.nome == nomeCompetencia}
            if (vagaAlterado.competencias.contains(competencia)) {
                vagaAlterado.competencias.remove(competencia)
                competenciasParaExcluir.add(competencia)
            } else {
                println "a competência ${nomeCompetencia} não está presente na lista de suas competências\n" +
                        "${vagaAlterado.competencias}"
            }

        }

        Boolean alterado = vagaCompetenciaController.excluirCompetenciasParaVaga(vagaAlterado.id, competenciasParaExcluir.collect { it.id })

        if (alterado)
            return vagaAlterado
        else
            return vaga
    }

    void deletarVaga(Empresa empresa) {
        Boolean vagaDeletada = false
        List<Vaga> vagas = this.listarVagasParaEmpresa(empresa)

        println "Qual vaga deseja deletar? Selecione o número dela"
        print "Número da vaga: "
        Integer idVaga = scanner.nextInt()
        scanner.nextLine()

        if (vagas.find{ it.id == idVaga }) {
            vagaDeletada = vagaController.deletarVaga(idVaga)
        } else {
            println "Não exite a vaga de número ${idVaga} registrada\n"
        }

        if(vagaDeletada)
            println "Vaga Deletada com sucesso!"
        else
            println "Não foi possível deletar a vaga!"

    }


    List<VagaListaDoCandidatoDto> listarVagasParaCandidato(Candidato candidato) {
        List<VagaListaDoCandidatoDto> vagas = vagaController.listarTodasVagasParaCandidato(candidato.id)

        vagas.forEach { VagaListaDoCandidatoDto vaga -> {
            println "" +
                    "Vaga de número ${vaga.id}: " +
                    "[Nome: ${vaga.nome}, " +
                    "Empresa: ${vaga.nomeEmpresa}, " +
                    "Competências desejadas: ${vaga.competencias}, " +
                    "Descrição: ${vaga.descricao}]" +
                    ""
        }}

        return  vagas
    }

    List<Vaga> listarVagasParaEmpresa(Empresa empresa) {
        List<Vaga> vagas = vagaController.listarTodasVagasParaEmpresa(empresa.id)

        this.mostrarVagasParaEmpresa(vagas)

        return vagas
    }

    private void mostrarVagasParaEmpresa(List<Vaga> vagas) {
        vagas.forEach {Vaga vaga -> {
            println "" +
                    "Vaga de número ${vaga.id}: " +
                    "[Nome: ${vaga.nome}, " +
                    "Estado: ${vaga.estado}, " +
                    "Cidade: ${vaga.cidade}, " +
                    "Competências desejadas: ${vaga.competencias}, " +
                    "Descrição: ${vaga.descricao}]" +
                    ""
        }}
    }

}
