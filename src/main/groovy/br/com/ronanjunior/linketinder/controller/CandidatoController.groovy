package br.com.ronanjunior.linketinder.controller

import br.com.ronanjunior.linketinder.dao.CandidatoDao
import br.com.ronanjunior.linketinder.dto.CandidatoListaDaEmpresaDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.Conexao
import io.github.cdimascio.dotenv.Dotenv

class CandidatoController {
    Dotenv dotenv = Dotenv.configure().load()
    CandidatoDao candidatoDao = new CandidatoDao(new Conexao(dotenv))
    CompetenciaController competenciaController = new CompetenciaController()

    List<CandidatoListaDaEmpresaDto> listarCandidatosParaEmpresa(Empresa empresa) {
        return candidatoDao.listarCandidatosParaEmpresa(empresa.id)
    }

    Boolean alterarCandidato(Candidato candidato) {
        return candidatoDao.atualizarCandidato(candidato)
    }

    Boolean adicionarCompetenciaCandidato(Candidato candidato) {
        return candidatoDao.cadastrarCompetenciaCandidato(candidato)
    }

    Boolean removerCompetenciaCandidato(Candidato candidatoAlterado, Candidato candidatoAntigo) {
        return candidatoDao.removerCompetenciaCandidato(candidatoAlterado, candidatoAntigo)
    }

    Candidato procurarCandidatoPorId(Integer idCandidato) {
        return candidatoDao.buscarCandidatoPorId(idCandidato)
    }

    Candidato copiarCandidato(Candidato candidato) {
        List<Competencia> competencias = []
        candidato.competencias.forEach {Competencia competencia -> {
            competencias.add(competenciaController.copiarCompetencia(competencia))
        }}
        return new Candidato(
                candidato.id,
                candidato.nome,
                candidato.sobrenome,
                candidato.cpf,
                candidato.dataNascimento,
                candidato.pais,
                candidato.estado,
                candidato.cep,
                candidato.descricao,
                competencias
        )
    }

    Boolean verificarCpf(String cpf) {
        return candidatoDao.verificarExistenciaCpfCadastrado(cpf)
    }
}
