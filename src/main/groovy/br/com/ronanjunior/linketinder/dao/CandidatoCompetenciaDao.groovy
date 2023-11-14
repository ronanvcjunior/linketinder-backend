package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.utils.MapperUtils

class CandidatoCompetenciaDao {
    private final ConexaoRepository conexao
    private final MapperUtils mapperUtils

    CandidatoCompetenciaDao(ConexaoRepository conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    Map buscarCompetenciaCandidato(Integer idCandidato, Integer idCompetencia) {
        try {
            String sSQL = montarBuscarCompetenciaCandidato()

            Map<String, Integer> parametros = [
                    idCandidato: idCandidato,
                    idCompetencia: idCompetencia
            ]

            return conexao.obterPrimeiraLinha(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao buscar competência candidato: ${e.message}", e)
        }
    }

    private String montarBuscarCompetenciaCandidato() {
        String sSQL = """
            SELECT
                c.id_competencia AS id, 
                c.nome 
            FROM Candidato_Competencia cc
            INNER JOIN Competencia c ON c.id_competencia = cc.id_competencia
            WHERE cc.id_candidato = :idCandidato
            AND cc.id_competencia = :idCompetencia
        """
        return sSQL
    }

    List<Map> listarCompetenciasPorCandidatoID(Integer idCandidato) {
        try {
            String sSQL = montarListarCompetenciasPorCandidatoID()

            Map<String, Integer> parametros = [ idCandidato: idCandidato ]

            return conexao.obterLinhas(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao listar competências do candidato: ${e.message}", e)
        }
    }

    private String montarListarCompetenciasPorCandidatoID() {
        String sSQL = """
            SELECT 
                c.id_competencia AS id, 
                c.nome 
            FROM Candidato_Competencia cc
            LEFT JOIN Competencia c ON c.id_competencia = cc.id_competencia
            WHERE cc.id_candidato = :idCandidato
        """
        return sSQL
    }

    Boolean cadastrarCompetenciaCandidato(Integer idCandidato, Integer idCompetencia) {
        try {
            String sSQL = this.montarInserirCompetenciaCandidato()

            Map<String, Integer> parametros = [
                    idCandidato: idCandidato,
                    idCompetencia: idCompetencia
            ]

            conexao.executar(sSQL, parametros)

            return true
        } catch (Exception e) {
            throw new Exception("Erro ao cadastrar competência para candidato: ${e.message}", e)
        }
    }

    private String montarInserirCompetenciaCandidato() {
        String sSQL = """
            INSERT INTO Candidato_Competencia (id_candidato, id_competencia)
            VALUES (:idCandidato, :idCompetencia)
        """
        return sSQL
    }

    Boolean excluirCompetenciaCandidato(Integer idCandidato, Integer idCompetencia) {
        try {
            String sSQL = montarExcluirCompetenciaCandidato()

            Map<String, Integer> parametros = [
                    idCandidato: idCandidato,
                    idCompetencia: idCompetencia
            ]

            conexao.executar(sSQL, parametros)

            return true
        } catch (Exception e) {
            throw new Exception("Erro ao excluir competência do candidato: ${e.message}", e)
        }
    }

    private String montarExcluirCompetenciaCandidato() {
        String sSQL = """
            DELETE FROM Candidato_Competencia
            WHERE id_candidato = :idCandidato
            AND id_competencia = :idCompetencia
        """
        return sSQL
    }
}
