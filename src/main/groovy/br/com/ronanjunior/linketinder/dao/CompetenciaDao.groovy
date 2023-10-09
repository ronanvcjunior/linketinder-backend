package br.com.ronanjunior.linketinder.dao

import groovy.sql.Sql
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.utils.Conexao

class CompetenciaDao {
    private final Conexao conexao

    CompetenciaDao(Conexao conexao) {
        this.conexao = conexao
    }

    List<Competencia> listarTodasCompetencias() {
        String sSQL = "SELECT id_competencia, nome FROM Competencia"
        return executarConsulta(sSQL)
    }

    List<Competencia> listarCompetenciasPorCandidatoID(Integer idCandidato) {
        String sSQL = "SELECT con.id_competencia, con.nome FROM Candidato_Competencia cc\n" +
                "LEFT JOIN Competencia con ON con.id_competencia = cc.id_competencia\n" +
                "WHERE cc.id_candidato = ${idCandidato}"
        return executarConsulta(sSQL)
    }

    List<Competencia> listarCompetenciasPorVagaID(Integer idVaga) {
        String sSQL = "SELECT con.id_competencia, con.nome FROM Vaga_Competencia vc\n" +
                "LEFT JOIN Competencia con ON con.id_competencia = vc.id_competencia\n" +
                "WHERE vc.id_vaga = ${idVaga}"
        return executarConsulta(sSQL)
    }

    private List<Competencia> executarConsulta(String sSQL) {
        List<Competencia> competencias = []
        try (Sql sql = conexao.abrirConexao()) {
            sql.eachRow(sSQL) { linha ->
                Competencia competencia = new Competencia(linha.id_competencia, linha.nome)
                competencias.add(competencia)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return competencias
    }
}
