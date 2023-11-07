package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.model.Match
import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.sql.Sql
import br.com.ronanjunior.linketinder.dto.MatchComIdVagaEIdCandidatoDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.ManipulacaoData

class MatchDao {
    private final Conexao conexao
    private final MapperUtils mapperUtils

    ManipulacaoData manipulacaoData = new ManipulacaoData()

    MatchDao(Conexao conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    Integer inserirMatch(Match match) {
        try {
            String sSQL = montarInserirMatch()

            Map<String, Object> parametros = [
                    dataCurtidaCandidato: match.dataCurtidaCandidato,
                    dataCurtidaVaga: match.dataCurtidaVaga,
                    idCandidato: match.candidato.id,
                    idVaga: match.vaga.id
            ]

            return conexao.inserir(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao inserir match", e)
        }
    }

    private String montarInserirMatch() {
        String sSQL = """
            INSERT INTO Match (id_candidato, id_vaga, data_curtida_candidato, data_curtida_vaga)
            VALUES (:idCandidato, :idVaga, :dataCurtidaCandidato, :dataCurtidaVaga)
        """
        return sSQL
    }

    Boolean atualizarMatch(Match match) {
        try {
            String sSQL = construirAtualizaMatch()

            Map<String, Object> parametros = [
                    dataCurtidaCandidato: match.dataCurtidaCandidato,
                    dataCurtidaVaga: match.dataCurtidaVaga,
                    idMatch: match.id,
            ]

            conexao.executar(sSQL, parametros)
            return true
        } catch (Exception e) {
            throw new Exception("Erro ao altera o candidato", e)
        }
    }

    private String construirAtualizaMatch() {
        String sSQL = """
                UPDATE Match
                SET data_curtida_candidato = :dataCurtidaCandidato,
                    data_curtida_vaga = :dataCurtidaVaga
                WHERE id_match = :idMatch
            """
        return sSQL
    }

    Map buscarMatchPorIdCandidatoIdVaga(Match match) {
        try {
            String sSQL = this.construirConsultaMatchPorIdCandidatoIdVaga()

            Map<String, Object> parametros = [
                    idCandidato: match.candidato.id,
                    idVaga: match.vaga.id
            ]

            return conexao.obterPrimeiraLinha(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao buscar match por id da vaga e id do candidato", e)
        }
    }

    private String construirConsultaMatchPorIdCandidatoIdVaga() {
        String sSQL = """
            SELECT * FROM Match
            WHERE id_candidato = :idCandidato
            AND id_vaga = :idVaga
        """
        return sSQL
    }

}
