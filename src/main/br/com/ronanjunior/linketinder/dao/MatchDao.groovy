package main.br.com.ronanjunior.linketinder.dao

import groovy.sql.Sql
import main.br.com.ronanjunior.linketinder.dto.MatchComIdVagaEIdCandidatoDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Vaga
import main.br.com.ronanjunior.linketinder.utils.Conexao
import main.br.com.ronanjunior.linketinder.utils.ManipulacaoData

class MatchDao {
    private final Conexao conexao

    ManipulacaoData manipulacaoData = new ManipulacaoData()

    MatchDao(Conexao conexao) {
        this.conexao = conexao
    }

    Boolean curtirVaga(Candidato candidato, Vaga vaga) {
        MatchComIdVagaEIdCandidatoDto match = buscarMatchPorCandidatoEVaga(candidato.id, vaga.id)
        if (match == null) {
            match = new MatchComIdVagaEIdCandidatoDto(null, null, new Date(), candidato.id, vaga.id)
            return cadastrarMatch(match)
        } else {
            match.dataCurtidaVaga = new Date()
            return atualizarMatch(match)
        }
    }

    Boolean curtirCandidato(Candidato candidato, Vaga vaga) {
        MatchComIdVagaEIdCandidatoDto match = buscarMatchPorCandidatoEVaga(candidato.id, vaga.id)
        if (match == null) {
            match = new MatchComIdVagaEIdCandidatoDto(null, new Date(), null, candidato.id, vaga.id)
            return cadastrarMatch(match)
        } else {
            match.dataCurtidaCandidato = new Date()
            return atualizarMatch(match)
        }
    }

    private MatchComIdVagaEIdCandidatoDto buscarMatchPorCandidatoEVaga(Integer idCandidato, Integer idVaga) {
        MatchComIdVagaEIdCandidatoDto match = null
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = """
                SELECT * FROM Match
                WHERE id_candidato = ${idCandidato}
                AND id_vaga = ${idVaga}
            """
            sql.eachRow(sSQL) { linha ->
                match = new MatchComIdVagaEIdCandidatoDto(
                        linha.id_match,
                        linha.data_curtida_candidato,
                        linha.data_curtida_vaga,
                        linha.id_candidato,
                        linha.id_vaga
                )
            }
            conexao.fecharConexao()
            return match
        } catch (Exception e) {
            e.printStackTrace()
            conexao.fecharConexao()
            return match
        }
    }

    private Boolean cadastrarMatch(MatchComIdVagaEIdCandidatoDto match) {
        String sSQL = """
            INSERT INTO Match (id_candidato, id_vaga, data_curtida_candidato, data_curtida_vaga)
            VALUES (
                ${match.idCandidato},
                ${match.idVaga},
                ${dataCurtida(match.dataCurtidaCandidato)},
                ${dataCurtida(match.dataCurtidaVaga)}
            )
        """
        return executarUpdate(sSQL)
    }

    private Boolean atualizarMatch(MatchComIdVagaEIdCandidatoDto match) {
        String sSQL = """
            UPDATE Match
            SET data_curtida_candidato = ${dataCurtida(match.dataCurtidaCandidato)},
                data_curtida_vaga = ${dataCurtida(match.dataCurtidaVaga)}
            WHERE id_candidato = ${match.idCandidato}
            AND id_vaga = ${match.idVaga}
        """
        return executarUpdate(sSQL)
    }

    private String dataCurtida(Date dataCurtida) {
        if (dataCurtida)
            return "'${manipulacaoData.dateParaString(dataCurtida)}'"
        return null
    }

    private Boolean executarUpdate(String sSQL) {
        try (Sql sql = conexao.abrirConexao()) {
            sql.execute(sSQL)
            return true
        } catch (Exception e) {
            e.printStackTrace()
            return false
        }
    }
}
