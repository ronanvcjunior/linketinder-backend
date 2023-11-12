package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.utils.MapperUtils

class VagaCompetenciaDao {
    private final ConexaoRepository conexao
    private final MapperUtils mapperUtils

    VagaCompetenciaDao(ConexaoRepository conexao, MapperUtils mapperUtils) {
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
            throw new Exception("Erro ao buscar competência vaga: ${e.message}", e)
        }
    }

    private String montarBuscarCompetenciaVaga() {
        String sSQL = """
            SELECT c.id_competencia, c.nome FROM Vaga_Competencia vc
            INNER JOIN Competencia c ON c.id_competencia = vc.id_competencia
            WHERE vc.id_vaga = :idVaga
            AND vc.id_competencia = :idCompetencia
        """
        return sSQL
    }

    List<Map> listarCompetenciasPorVagaID(Integer idVaga) {
        try {
            String sSQL = montarListarCompetenciasPorVagaID()

            Map<String, Integer> parametros = [ idVaga: idVaga ]

            return conexao.obterLinhas(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao listar competências da vaga: ${e.message}", e)
        }
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
            throw new Exception("Erro ao cadastrar competência para vaga: ${e.message}", e)
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
            throw new Exception("Erro ao excluir competência vaga: ${e.message}", e)
        }
    }

    private String montarExcluirCompetenciaVaga() {
        String sSQL = """
            DELETE FROM Vaga_Competencia
            WHERE id_vaga = :idVaga
            AND id_competencia = :idCompetencia
        """
        return sSQL
    }
}
