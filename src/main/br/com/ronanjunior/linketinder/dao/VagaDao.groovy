package main.br.com.ronanjunior.linketinder.dao

import groovy.sql.Sql
import main.br.com.ronanjunior.linketinder.dto.VagaListaDoCandidadoDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Conta
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.model.Vaga
import main.br.com.ronanjunior.linketinder.utils.Conexao

class VagaDao {
    private final Conexao conexao

    VagaDao(Conexao conexao) {
        this.conexao = conexao
    }

    public List<VagaListaDoCandidadoDto> listarTodasVagasParaCandidato(Candidato candidato) {
        List<VagaListaDoCandidadoDto> vagas = []
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = """
                SELECT
                    va.id_vaga,
                    va.nome,
                    va.descricao,
                    CASE
                        WHEN MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) = 1 THEN em.nome
                        ELSE 'Anonimo'
                    END AS empresa,
                    MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) AS match
                FROM Vaga va
                LEFT JOIN (
                    SELECT * FROM Match WHERE id_candidato = ${candidato.id}
                ) ma ON ma.id_vaga = va.id_vaga
                INNER JOIN Empresa em ON em.id_empresa = va.id_empresa
                GROUP BY va.id_vaga, em.nome
                ORDER BY match DESC, va.nome ASC, va.id_vaga ASC;
            """
            sql.eachRow(sSQL) { linha ->
                CompetenciaDao competenciaDao = new CompetenciaDao(conexao)
                List<Competencia> competencias = competenciaDao.listarCompetenciasPorVagaID(linha.id_vaga)
                VagaListaDoCandidadoDto vagaListaDoCandidadoDto = new VagaListaDoCandidadoDto(
                        linha.id_vaga,
                        linha.nome,
                        linha.descricao,
                        linha.empresa,
                        competencias
                )
                vagas.add(vagaListaDoCandidadoDto)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return vagas
    }

    public List<Vaga> listarVagasPorEmpresa(Integer idEmpresa) {
        List<Vaga> vagas = []
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = """
                SELECT id_vaga, nome
                FROM Vaga
                WHERE id_empresa = ${idEmpresa}
            """
            sql.eachRow(sSQL) { linha ->
                CompetenciaDao competenciaDao = new CompetenciaDao(conexao)
                List<Competencia> competencias = competenciaDao.listarCompetenciasPorVagaID(linha.id_vaga)
                Vaga vaga = new Vaga(
                        linha.id_vaga,
                        linha.nome,
                        linha.descricao,
                        linha.estado,
                        linha.cidade,
                        null,
                        competencias
                )
                vagas.add(vaga)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return vagas
    }

    Integer cadastrarVaga(Vaga vaga) {
        try (Sql sql = conexao.abrirConexao()) {
            conexao.iniciarTransacao()

            Integer vagaId = cadastrarConta(vaga, sql)
            if (vagaId == null) {
                conexao.rollbackTransacao()
                conexao.fecharConexao();
                return null
            }

            this.cadastrarCompetenciaDaVaga(vagaId, vaga.competencias, sql);

            conexao.commitTransacao()
            return vagaId
        } catch (Exception e) {
            e.printStackTrace()
            conexao.rollbackTransacao()
            return null
        }
    }

    private Integer cadastrarConta(Vaga vaga, Sql sql) {
        String sSQL = """
            INSERT INTO Vaga (nome, descricao, estado, cidade, id_empresa)
            VALUES (
                '${vaga.nome}',
                '${vaga.descricao}',
                '${vaga.estado}',
                '${vaga.cidade}',
                ${vaga.empresa.id}
            )
            RETURNING id_vaga
        """
        List<List<Object>> resultado = sql.executeInsert(sSQL)
        if (resultado)
            return resultado[0][0]
        else
            return null
    }

    private void cadastrarCompetenciaDaVaga(Integer idVaga, List<Competencia> competencias, Sql sql) {
        competencias.forEach {Competencia competencia -> {
            String sSQL = """
                INSERT INTO vaga_competencia (id_vaga, id_competencia)
                VALUES (
                    ${idVaga},
                    ${competencia.id}
                )
            """
            sql.executeInsert(sSQL)
        }}
    }

    Vaga buscarVagaPorId(Integer idVaga) {
        Vaga vaga = null;
        List<Competencia> competencias = null;
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = "SELECT * FROM Vaga WHERE id_vaga = ${idVaga}"
            sql.eachRow(sSQL) { linha ->
                CompetenciaDao competenciaDao = new CompetenciaDao(conexao);
                competencias = competenciaDao.listarCompetenciasPorVagaID(linha.id_vaga);
                vaga = new Vaga(
                        linha.id_vaga,
                        linha.nome,
                        linha.descricao,
                        linha.estado,
                        linha.cidade,
                        null,
                        competencias
                )
            }
            conexao.fecharConexao();
            return vaga;
        } catch (Exception e) {
            e.printStackTrace()
            conexao.fecharConexao();
            return vaga
        }
    }

    public Boolean atualizarVaga(Vaga vaga) {
        String sSQL = """
            UPDATE Vaga
            SET nome = '${vaga.nome}',
                descricao = '${vaga.descricao}',
                estado = '${vaga.estado}',
                cidade = '${vaga.cidade}',
                id_empresa = ${vaga.empresa.id}
            WHERE id_vaga = ${vaga.id}
        """
        return executarUpdate(sSQL)
    }

    public Boolean excluirVaga(Integer idVaga) {
        String sSQL = "DELETE FROM Vaga WHERE id_vaga = ${idVaga};"
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
