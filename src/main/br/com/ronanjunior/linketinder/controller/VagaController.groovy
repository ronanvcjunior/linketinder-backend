package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.dao.VagaDao
import main.br.com.ronanjunior.linketinder.dto.VagaListaDoCandidadoDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Conta
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.model.Vaga
import main.br.com.ronanjunior.linketinder.utils.Conexao

class VagaController {
    VagaDao vagaDao = new VagaDao(new Conexao());

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
}
