package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.dao.CandidatoDao
import main.br.com.ronanjunior.linketinder.dto.CandidatoListaDaEmpresaDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.utils.Conexao

class CandidatoController {
    CandidatoDao candidatoDao = new CandidatoDao(new Conexao());
    List<Candidato> candidatos = [];
    CompetenciaController competenciaController = new CompetenciaController();

    List<CandidatoListaDaEmpresaDto> listarCandidatosParaEmpresa(Empresa empresa) {
        return candidatoDao.listarCandidatosParaEmpresa(empresa.id);
    }

    void adicionarCandidato(Candidato candidato) {
        candidatos.add(candidato);
    }

    Boolean alterarCandidato(Candidato candidato) {
        return candidatoDao.atualizarCandidato(candidato);
    }

    Boolean adicionarCompetenciaCandidato(Candidato candidato) {
        return candidatoDao.cadastrarCompetenciaCandidato(candidato);
    }

    Boolean removerCompetenciaCandidato(Candidato candidatoAlterado, Candidato candidatoAntigo) {
        return candidatoDao.removerCompetenciaCandidato(candidatoAlterado, candidatoAntigo);
    }

    Candidato procurarCandidatoPorId(Integer idCandidato) {
        return candidatoDao.buscarCandidatoPorId(idCandidato);
    }

    Candidato copiarCandidato(Candidato candidato) {
        List<Competencia> competencias = [];
        candidato.competencias.forEach {Competencia competencia -> {
            competencias.add(competenciaController.copiarCompetencia(competencia));
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
        );
    }

    public Boolean verificarCpf(String cpf) {
        return candidatoDao.verificarExistenciaCpfCadastrado(cpf);
    }
}
