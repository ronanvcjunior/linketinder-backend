package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.dao.CandidatoDao
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.utils.Conexao

class CandidatoController {
    CandidatoDao candidatoDao = new CandidatoDao(new Conexao());
    List<Candidato> candidatos = [];

    void adicionarCandidato(Candidato candidato) {
        candidatos.add(candidato);
    }

    Boolean alterarCandidato(Candidato candidato) {
        return candidatoDao.atualizarCandidato(candidato);
    }

    Boolean adicionarCompetenciaCandidato(Candidato candidato) {
        return candidatoDao.cadastrarCompetenciaCandidato(candidato);
    }

    Candidato copiarCandidato(Candidato candidato) {
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
                candidato.competencias
        );
    }

    public Boolean verificarCpf(String cpf) {
        return candidatoDao.verificarExistenciaCpfCadastrado(cpf);
    }
}
