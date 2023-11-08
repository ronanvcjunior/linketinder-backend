package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class VagaCompetenciaDao {
    private final Conexao conexao
    private final MapperUtils mapperUtils

    VagaCompetenciaDao(Conexao conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    Map buscarCompetenciaVaga(Integer idVaga, Integer idCompetencia) {
        try {
            String sSQL = montarBuscarCompetenciaVaga()

            Map<String, Integer> parametros = [
                    idVaga: idVaga,
                    idCompetencia: idCompetencia
            ]

            return conexao.obterPrimeiraLinha(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao excluir competência vaga", e)
        }
    }

    private String montarBuscarCompetenciaVaga() {
        String sSQL = """
            SELECT id_vaga_competencia FROM Vaga_Competencia
            WHERE id_vaga = : idVaga
            AND id_competencia = : idCompetencia
        """
        return sSQL
    }

    List<Map> listarCompetenciasPorVagaID(Integer idVaga) {
        String sSQL = montarListarCompetenciasPorVagaID()

        Map<String, Integer> parametros = [ idVaga: idVaga ]

        return conexao.obterLinhas(sSQL, parametros)
    }

    private String montarListarCompetenciasPorVagaID() {
        String sSQL = """
            SELECT con.id_competencia, con.nome FROM Vaga_Competencia cc
            LEFT JOIN Competencia con ON con.id_competencia = cc.id_competencia
            WHERE cc.id_vaga = :idVaga
        """
        return sSQL
    }

    Boolean cadastrarCompetenciaVaga(Integer idVaga, Integer idCompetencia) {
        try {
            String sSQL = this.montarInserirCompetenciaVaga();

            Map<String, Integer> parametros = [
                    idVaga: idVaga,
                    idCompetencia: idCompetencia
            ]

            conexao.executar(sSQL, parametros)

            return true
        } catch (Exception e) {
            throw new Exception("Erro ao cadastrar competência para vaga", e)
        }
    }

    private String montarInserirCompetenciaVaga() {
        String sSQL = """
            INSERT INTO Vaga_Competencia (id_vaga, id_competencia)
            VALUES (:idVaga, :idCompetencia)
        """
        return sSQL
    }

    Boolean excluirCompetenciaVaga(Integer idVaga, Integer idCompetencia) {
        try {
            String sSQL = montarExcluirCompetenciaVaga()

            Map<String, Integer> parametros = [
                    idVaga: idVaga,
                    idCompetencia: idCompetencia
            ]

            conexao.executar(sSQL, parametros)

            return true
        } catch (Exception e) {
            throw new Exception("Erro ao excluir competência vaga", e)
        }
    }

    private String montarExcluirCompetenciaVaga() {
        String sSQL = """
            DELETE FROM Vaga_Competencia
            WHERE id_vaga = : idVaga
            AND id_competencia = : idCompetencia
        """
        return sSQL
    }
}
