package main.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.dao.VagaDao
import main.br.com.ronanjunior.linketinder.dto.VagaListaDoCandidadoDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.utils.Conexao

class VagaController {
    VagaDao vagaDao = new VagaDao(new Conexao());

    List<VagaListaDoCandidadoDto> listarTodasVagasParaCandidato(Candidato candidato) {
        return vagaDao.listarTodasVagasParaCandidato(candidato);
    }
}
