package main.br.com.ronanjunior.linketinder.dao

import groovy.sql.Sql
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.utils.Conexao

class CompetenciaDao {
    private final Conexao conexao

    CompetenciaDao(Conexao conexao) {
        this.conexao = conexao
    }

    public List<Competencia> listarTodasCompetencias() {
        String sSQL = "SELECT id_competencia, nome FROM Competencia"
        return executarConsulta(sSQL)
    }

    public List<Competencia> listarCompetenciasPorCandidatoID(Integer idCandidato) {
        String sSQL = "SELECT con.id_competencia, con.nome FROM Candidato_Competencia cc\n" +
                "LEFT JOIN Competencia con ON con.id_competencia = cc.id_competencia\n" +
                "WHERE cc.id_candidato = ${idCandidato}"
        return executarConsulta(sSQL)
    }

    public List<Competencia> listarCompetenciasPorVagaID(Integer idVaga) {
        String sSQL = "SELECT con.id_competencia, con.nome FROM Vaga_Competencia vc\n" +
                "LEFT JOIN Competencia con ON con.id_competencia = vc.id_competencia\n" +
                "WHERE vc.id_vaga = ${idVaga}"
        return executarConsulta(sSQL)
    }

    public Boolean cadastrarCompetencia(Competencia competencia) {
        String sSQL = "INSERT INTO Competencia (id_competencia, nome)\n" +
                "VALUES (\n" +
                "\t${competencia.id},\n" +
                "\t'${competencia.nome}'\n" +
                ");"

        return executarUpdate(sSQL)
    }

    public Boolean atualizarCompetencia(Competencia competencia) {
        String sSQL = "UPDATE Competencia\n" +
                "SET nome = '${competencia.nome}'\n" +
                "WHERE id_competencia = ${competencia.id};"

        return executarUpdate(sSQL)
    }

    public Boolean excluirCompetencia(Integer idCompetencia) {
        String sSQL = "DELETE FROM Competencia WHERE id_competencia = ${idCompetencia};"
        return executarUpdate(sSQL)
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
