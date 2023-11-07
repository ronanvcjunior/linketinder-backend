package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class CandidatoCompetenciaDao {
    private final Conexao conexao
    private final MapperUtils mapperUtils

    CandidatoCompetenciaDao(Conexao conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    Boolean cadastrarCompetenciaCandidato(Integer idCandidato, Integer idCompetencia) {
        try {
            String sSQL = this.montarInserirCompetenciaCandidato();

            Map<String, Integer> parametros = [
                    idCandidato: idCandidato,
                    idCompetencia: idCompetencia
            ]

            conexao.executar(sSQL, parametros)

            return true
        } catch (Exception e) {
            throw new Exception("Erro ao cadastrar competência para candidato", e)
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
            throw new Exception("Erro ao excluir competência candidato", e)
        }
    }

    private String montarExcluirCompetenciaCandidato() {
        String sSQL = """
            DELETE FROM Candidato_Competencia
            WHERE id_candidato = : idCandidato
            AND id_competencia = : idCompetencia
        """
        return sSQL
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
            throw new Exception("Erro ao excluir competência candidato", e)
        }
    }

    private String montarBuscarCompetenciaCandidato() {
        String sSQL = """
            SELECT id_candidato_competencia FROM Candidato_Competencia
            WHERE id_candidato = : idCandidato
            AND id_competencia = : idCompetencia
        """
        return sSQL
    }

    List<Map> listarCompetenciasPorCandidatoID(Integer idCandidato) {
        String sSQL = montarListarCompetenciasPorCandidatoID()

        Map<String, Integer> parametros = [ idCandidato: idCandidato ]

        return conexao.obterLinhas(sSQL, parametros)
    }

    private String montarListarCompetenciasPorCandidatoID() {
        String sSQL = """
            SELECT con.id_competencia, con.nome FROM Candidato_Competencia cc
            LEFT JOIN Competencia con ON con.id_competencia = cc.id_competencia
            WHERE cc.id_candidato = :idCandidato
        """
        return sSQL
    }
}
