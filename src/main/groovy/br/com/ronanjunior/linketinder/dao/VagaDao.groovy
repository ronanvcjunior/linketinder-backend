package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.model.Match
import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.sql.Sql
import br.com.ronanjunior.linketinder.dto.VagaListaDoCandidadoDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.utils.Conexao

class VagaDao {
    private final Conexao conexao
    private final MapperUtils mapperUtils

    VagaDao(Conexao conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    List<Map> listarVagasParaCandidato(Integer idCandidato) {
        try {
            String sSQL = construirConsultaVagasParaCandidato()
            Map<String, Integer> parametros = [idCandidato: idCandidato]
            return conexao.obterLinhas(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao listar vagas para o candidato: ${e.message}", e)
        }
    }

    private String construirConsultaVagasParaCandidato() {
        String sSQL = """
                SELECT
                    va.id_vaga,
                    va.nome,
                    va.descricao,
                    CASE
                        WHEN MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) = 1 THEN em.nome
                        ELSE 'Anônimo'
                    END AS empresa,
                    MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) AS match
                FROM Vaga va
                LEFT JOIN (
                    SELECT * FROM Match WHERE id_candidato = :idCandidato
                ) ma ON ma.id_vaga = va.id_vaga
                INNER JOIN Empresa em ON em.id_empresa = va.id_empresa
                GROUP BY va.id_vaga, em.nome
                ORDER BY match DESC, va.nome ASC, va.id_vaga ASC
            """
        return sSQL
    }

    List<Map> buscarVagasPorIdEmpresa(Integer idEmpresa) {
        try {
            String sSQL = this.construirConsultaVagasPorIdEmpresa()

            Map<String, Integer> parametros = [idEmpresa: idEmpresa]

            return conexao.obterLinhas(sSQL, parametros)

        } catch (Exception e) {
            throw new Exception("Erro ao buscar candidato por id", e)
        }
    }

    private String construirConsultaVagasPorIdEmpresa() {
        String sSQL = """
                SELECT * FROM Vaga
                WHERE id_empresa = :idEmpresa
            """
        return sSQL
    }

    Integer inserirVaga(Vaga vaga) {
        try {
            String sSQL = montarInserirVaga()

            Map<String, Object> parametros = [
                    nome: vaga.nome,
                    descricao: vaga.descricao,
                    estado: vaga.estado,
                    cidade: vaga.cidade,
                    idEmpresa: vaga.empresa.id
            ]

            return conexao.inserir(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao inserir vaga", e)
        }
    }

    private String montarInserirVaga() {
        String sSQL = """
            INSERT INTO Vaga (nome, descricao, estado, cidade, id_empresa)
            VALUES (:nome, :descricao, :estado, :cidade, :idEmpresa)
        """
        return sSQL
    }

    Map buscarVagaPorId(Integer idVaga) {
        try {
            String sSQL = this.construirConsultaCandidatoPorId()

            Map<String, Integer> parametros = [idVaga: idVaga]

            return conexao.obterPrimeiraLinha(sSQL, parametros)

        } catch (Exception e) {
            throw new Exception("Erro ao buscar vaga por id", e)
        }
    }

    private String construirConsultaCandidatoPorId() {
        String sSQL = """
            SELECT * FROM Vaga
            WHERE id_vaga = :idVaga
        """
        return sSQL
    }

    Boolean atualizarVaga(Vaga vaga) {
        try {
            String sSQL = construirAtualizaVaga()

            Map<String, Object> parametros = mapperUtils.converterObjectToMap(vaga)

            conexao.executar(sSQL, parametros)
            return true
        } catch (Exception e) {
            throw new Exception("Erro ao altera a vaga", e)
        }
    }

    private String construirAtualizaVaga() {
        String sSQL = """
                UPDATE Vaga
                SET nome = :nome,
                    descricao = :descricao,
                    estado = :estado,
                    cidade = :cidade
                WHERE id_vaga = :idVaga
            """
        return sSQL
    }

    Boolean excluirVaga(Integer idVaga) {
        try {
            String sSQL = montarExcluirVaga()

            Map<String, Integer> parametros = [idVaga: idVaga]

            conexao.executar(sSQL, parametros)

            return true
        } catch (Exception e) {
            throw new Exception("Erro ao excluir candidato", e)
        }
    }

    private String montarExcluirVaga() {
        String sSQL = """
            DELETE FROM Vaga
            WHERE id_vaga = : idVaga
        """
        return sSQL
    }

}
