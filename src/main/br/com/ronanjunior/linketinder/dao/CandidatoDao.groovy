package main.br.com.ronanjunior.linketinder.dao

import groovy.sql.Sql
import main.br.com.ronanjunior.linketinder.dto.CandidatoListaDaEmpresaDto
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Conta
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.utils.Conexao

class CandidatoDao {
    private final Conexao conexao

    CandidatoDao(Conexao conexao) {
        this.conexao = conexao
    }

    public List<CandidatoListaDaEmpresaDto> listarCandidatosParaEmpresa(Integer idEmpresa) {
        List<CandidatoListaDaEmpresaDto> candidatos = []
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = """
                SELECT
                    can.id_candidato,
                    CASE
                        WHEN MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) = 1 THEN CONCAT(can.nome, ' ', can.sobrenome)
                        ELSE 'Anônimo'
                    END AS nome_completo,
                    MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) AS match
                FROM Candidato can
                LEFT JOIN (
                    SELECT * FROM Match WHERE id_vaga IN (
                        SELECT va.id_vaga FROM Vaga va
                        RIGHT JOIN Empresa em ON em.id_empresa = va.id_empresa
                        WHERE em.id_empresa = ${idEmpresa}
                    )
                ) ma ON ma.id_candidato = can.id_candidato
                GROUP BY can.id_candidato
                ORDER BY match DESC, nome_completo ASC, can.id_candidato ASC;
            """

            sql.eachRow(sSQL) { linha ->
                CompetenciaDao competenciaDao = new CompetenciaDao(conexao)
                List<Competencia> competencias = competenciaDao.listarCompetenciasPorCandidatoID(linha.id_candidato)
                CandidatoListaDaEmpresaDto candidatoListaDaEmpresaDto = new CandidatoListaDaEmpresaDto(
                        linha.id_candidato,
                        linha.nome_completo,
                        competencias
                )
                candidatos.add(candidatoListaDaEmpresaDto)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return candidatos
    }

    public Boolean cadastrarCandidato(Candidato candidato) {
        String sSQL = """
            INSERT INTO Candidato_Competencia (id_candidato, id_competencia)
            VALUES (
                '${candidato.id}',
                '${candidato.competencias.id}',
            )
        """

        return executarUpdate(sSQL)
    }

    Boolean atualizarCandidato(Candidato candidato) {
        String sSQL = """
            UPDATE Candidato
            SET nome = '${candidato.nome}',
                sobrenome = '${candidato.sobrenome}',
                cpf = '${candidato.cpf}',
                data_nascimento = '${candidato.dataNascimento}',
                pais = '${candidato.pais}',
                cep = '${candidato.cep}',
                estado = '${candidato.estado}',
                descricao = '${candidato.descricao}'
            WHERE id_candidato = ${candidato.id}
        """

        return executarUpdate(sSQL)
    }

    Boolean cadastrarCompetenciaCandidato(Candidato candidato) {
        try (Sql sql = conexao.abrirConexao()) {
            conexao.iniciarTransacao()
            candidato.competencias.forEach {Competencia competencia -> {
                Boolean existeCompetenciaCandidato = this.verificarExistenciaCompetenciaParaCandidato(candidato.id, competencia.id, sql);
                if(!existeCompetenciaCandidato) {
                    String sSQL = """
                        INSERT INTO Candidato_Competencia (id_candidato, id_competencia)
                        VALUES (${candidato.id}, ${competencia.id})
                    """

                    sql.execute(sSQL);
                }
            }}

            conexao.commitTransacao()
            conexao.fecharConexao();
            return true;
        } catch (Exception e) {
            println e
            conexao.rollbackTransacao()
            conexao.fecharConexao();
            return false;
        }
    }

    Boolean removerCompetenciaCandidato(Candidato candidatoAlterado, Candidato candidatoAntigo) {
        try (Sql sql = conexao.abrirConexao()) {
            conexao.iniciarTransacao()
            candidatoAntigo.competencias.forEach {Competencia competencia -> {
                Boolean existeCompetenciaCandidato = candidatoAlterado.competencias.contains(competencia);
                if(!existeCompetenciaCandidato) {
                    String sSQL = """
                        DELETE FROM Candidato_Competencia 
                        WHERE id_candidato = ${candidatoAlterado.id} AND
                            id_competencia = ${competencia.id}
                    """
                    sql.execute(sSQL);
                }
            }}

            conexao.commitTransacao()
            conexao.fecharConexao();
            return true;
        } catch (Exception e) {
            println e
            conexao.rollbackTransacao()
            conexao.fecharConexao();
            return false;
        }
    }

    private Boolean verificarExistenciaCompetenciaParaCandidato(Integer idCandidato, Integer idCompetencia, Sql sql) {
        String sSQL = """
        SELECT * FROM Candidato_Competencia
        WHERE 
            id_candidato = ${idCandidato} AND
            id_competencia = ${idCompetencia}
    """

        Boolean competenciaEncontrada = false

        sql.eachRow(sSQL) { linha ->
            competenciaEncontrada = true
        }

        return competenciaEncontrada;
    }

    Boolean verificarExistenciaCpfCadastrado(String cpf) {
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = """
                SELECT * FROM Candidato
                WHERE cpf = '${cpf}'
            """

            Boolean candidatoEncontrado = false

            sql.eachRow(sSQL) { linha ->
                candidatoEncontrado = true
            }

            conexao.fecharConexao();
            return candidatoEncontrado;
        } catch (Exception e) {
            e.printStackTrace()
            conexao.fecharConexao();
            return false;
        }
    }


    public Boolean excluirCandidato(Integer idCandidato) {
        String sSQL = "DELETE FROM Candidato WHERE id_candidato = ${idCandidato};"
        return executarUpdate(sSQL)
    }

    public Candidato buscarCandidatoPorId(Integer idCandidato) {
        Candidato candidato = null;
        List<Competencia> competencias = null;
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = "SELECT * FROM Candidato WHERE id_candidato = ${idCandidato}"
            sql.eachRow(sSQL) { linha ->
                CompetenciaDao competenciaDao = new CompetenciaDao(conexao);
                competencias = competenciaDao.listarCompetenciasPorCandidatoID(linha.id_candidato);
                candidato = new Candidato(
                        linha.id_candidato,
                        linha.nome,
                        linha.sobrenome,
                        linha.cpf,
                        linha.data_nascimento,
                        linha.pais,
                        linha.estado,
                        linha.cep,
                        linha.descricao,
                        competencias
                )
            }
            conexao.fecharConexao();
            return candidato;
        } catch (Exception e) {
            e.printStackTrace()
            conexao.fecharConexao();
            return candidato
        }
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
