package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.dao.VagaDao
import main.br.com.ronanjunior.linketinder.dto.VagaListaDoCandidadoDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Conta
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.model.Vaga
import main.br.com.ronanjunior.linketinder.utils.Conexao

class VagaController {
    VagaDao vagaDao = new VagaDao(new Conexao());
    CompetenciaController competenciaController = new CompetenciaController();

    List<VagaListaDoCandidadoDto> listarTodasVagasParaCandidato(Candidato candidato) {
        return vagaDao.listarTodasVagasParaCandidato(candidato);
    }

    List<Vaga> listarTodasVagasParaEmpresa(Empresa empresa) {
        return vagaDao.listarVagasPorEmpresa(empresa.id);
    }

    public Vaga registrarVaga(Vaga novaVaga) {
        Integer idVaga =  vagaDao.cadastrarVaga(novaVaga);

        return vagaDao.buscarVagaPorId(idVaga);
    }

    Boolean alterarVaga(Vaga vaga) {
        return vagaDao.atualizarVaga(vaga);
    }

    Boolean adicionarCompetenciaVaga(Vaga vaga) {
        return vagaDao.cadastrarCompetenciaVaga(vaga);
    }

    Boolean removerCompetenciaVaga(Vaga vagaAlterado, Vaga vagaAntigo) {
        return vagaDao.removerCompetenciaVaga(vagaAlterado, vagaAntigo);
    }

    Vaga procurarVagaDaEmpresaPorId(Integer idVaga, Integer idEmpresa) {
        return vagaDao.buscarVagaDaEmpresaPorId(idVaga, idEmpresa);
    }

    Vaga copiarVaga(Vaga vaga) {
        List<Competencia> competencias = [];
        vaga.competencias.forEach {Competencia competencia -> {
            competencias.add(competenciaController.copiarCompetencia(competencia));
        }}
        return new Vaga(
                vaga.id,
                vaga.nome,
                vaga.descricao,
                vaga.estado,
                vaga.cidade,
                vaga.empresa,
                competencias
        );
    }
}
