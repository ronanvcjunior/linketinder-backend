package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.dto.MatchComIdVagaEIdCandidatoDto
import br.com.ronanjunior.linketinder.utils.MapperUtils
import br.com.ronanjunior.linketinder.utils.Conexao

class MatchDao {
    private final Conexao conexao
    private final MapperUtils mapperUtils

    MatchDao(Conexao conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    Map buscarMatchPorIdCandidatoIdVaga(Integer idCandidato, Integer idVaga) {
        try {
            String sSQL = this.construirConsultaMatchPorIdCandidatoIdVaga()

            Map<String, Object> parametros = [
                    idCandidato: idCandidato,
                    idVaga: idVaga
            ]

            return conexao.obterPrimeiraLinha(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao buscar match por id da vaga e id do candidato: ${e.message}", e)
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

    Integer inserirMatch(MatchComIdVagaEIdCandidatoDto match) {
        try {
            String sSQL = montarInserirMatch()

            Map<String, Object> parametros = mapperUtils.converterObjectToMap(match)

            return conexao.inserir(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao inserir match: ${e.message}", e)
        }
    }

    private String montarInserirMatch() {
        String sSQL = """
            INSERT INTO Match (id_candidato, id_vaga, data_curtida_candidato, data_curtida_vaga)
            VALUES (:idCandidato, :idVaga, :dataCurtidaCandidato, :dataCurtidaVaga)
        """
        return sSQL
    }

    Boolean atualizarMatch(MatchComIdVagaEIdCandidatoDto match) {
        try {
            String sSQL = construirAtualizaMatch()

            Map<String, Object> parametros = mapperUtils.converterObjectToMap(match)

            conexao.executar(sSQL, parametros)
            return true
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar o match: ${e.message}", e)
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

}
