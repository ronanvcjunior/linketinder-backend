import groovy.sql.Sql
import main.br.com.ronanjunior.linketinder.model.Match
import main.br.com.ronanjunior.linketinder.utils.Conexao

class MatchDao {
    private final Conexao conexao

    MatchDao(Conexao conexao) {
        this.conexao = conexao
    }

    public Boolean curtirVaga(Integer idCandidato, Integer idVaga) {
        Match match = buscarMatchPorCandidatoEVaga(idCandidato, idVaga)
        if (match == null) {
            match = new Match(idCandidato, idVaga, null, new Date())
            return cadastrarMatch(match)
        } else {
            match.dataCurtidaVaga = new Date()
            return atualizarMatch(match)
        }
    }

    public Boolean curtirCandidato(Integer idCandidato, Integer idVaga) {
        Match match = buscarMatchPorCandidatoEVaga(idCandidato, idVaga)
        if (match == null) {
            match = new Match(idCandidato, idVaga, new Date(), null)
            return cadastrarMatch(match)
        } else {
            match.dataCurtidaCandidato = new Date()
            return atualizarMatch(match)
        }
    }

    private Match buscarMatchPorCandidatoEVaga(Integer idCandidato, Integer idVaga) {
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = """
                SELECT * FROM Match
                WHERE id_candidato = ${idCandidato}
                AND id_vaga = ${idVaga}
            """
            return sql.firstRow(sSQL, Match)
        } catch (Exception e) {
            e.printStackTrace()
            return null
        }
    }

    public Boolean cadastrarMatch(Match match) {
        String sSQL = """
            INSERT INTO Match (id_candidato, id_vaga, data_curtida_candidato, data_curtida_vaga)
            VALUES (
                ${match.candidato.id},
                ${match.vaga.id},
                ${match.dataCurtidaCandidato},
                ${match.dataCurtidaVaga}
            )
        """
        return executarUpdate(sSQL)
    }

    public Boolean atualizarMatch(Match match) {
        String sSQL = """
            UPDATE Match
            SET data_curtida_candidato = ${match.dataCurtidaCandidato},
                data_curtida_vaga = ${match.dataCurtidaVaga}
            WHERE id_candidato = ${match.candidato.id}
            AND id_vaga = ${match.vaga.id}
        """
        return executarUpdate(sSQL)
    }

    public Boolean excluirMatch(Integer idMatch) {
        String sSQL = "DELETE FROM Match WHERE id_match = ${idMatch};"
        return executarUpdate(sSQL)
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
