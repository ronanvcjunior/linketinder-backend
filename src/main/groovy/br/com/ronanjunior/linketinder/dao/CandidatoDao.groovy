package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.utils.MapperUtils
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.utils.Conexao

class CandidatoDao {
    private final Conexao conexao
    private final MapperUtils mapperUtils

    CandidatoDao(Conexao conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    List<Map> listarCandidatosParaEmpresa(Integer idEmpresa) {
        try {
            String sSQL = construirConsultaCandidatosParaEmpresa()
            Map<String, Integer> parametros = [idEmpresa: idEmpresa]
            return conexao.obterLinhas(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao listar candidatos para a empresa: ${e.message}", e)
        }
    }

    private String construirConsultaCandidatosParaEmpresa() {
        String sSQL = """
            SELECT
                can.id_candidato,
                CASE
                    WHEN MAX(
                        CASE 
                            WHEN data_curtida_candidato IS NOT NULL 
                            AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END
                    ) = 1 THEN CONCAT(can.nome, ' ', can.sobrenome)
                    ELSE 'Anônimo'
                END AS nome_completo,
                MAX(
                    CASE 
                        WHEN data_curtida_candidato IS NOT NULL 
                        AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END
                ) AS match
            FROM Candidato can
            LEFT JOIN (
                SELECT * FROM Match WHERE id_vaga IN (
                    SELECT va.id_vaga FROM Vaga va
                    RIGHT JOIN Empresa em ON em.id_empresa = va.id_empresa
                    WHERE em.id_empresa = :idEmpresa
                )
            ) ma ON ma.id_candidato = can.id_candidato
            GROUP BY can.id_candidato
            ORDER BY match DESC, nome_completo ASC, can.id_candidato ASC
        """
        return sSQL
    }

    Map buscarCandidatoPorId(Integer idCandidato) {
        try {
            String sSQL = this.construirConsultaCandidatoPorId()

            Map<String, Integer> parametros = [idCandidato: idCandidato]

            return conexao.obterPrimeiraLinha(sSQL, parametros)

        } catch (Exception e) {
            throw new Exception("Erro ao buscar candidato por id", e)
        }
    }

    private String construirConsultaCandidatoPorId() {
        String sSQL = """
            SELECT * FROM Candidato
            WHERE id_candidato = :idCandidato
        """
        return sSQL
    }

    Map buscarCandidatoPorCpf(String cpf) {
        try {
            String sSQL = this.construirConsultaCandidatoPorCpf()

            Map<String, String> parametros = [cpf: cpf]

            return conexao.obterPrimeiraLinha(sSQL, parametros)

        } catch (Exception e) {
            throw new Exception("Erro ao verificar existência de candidato por cpf", e)
        }
    }

    private String construirConsultaCandidatoPorCpf() {
        String sSQL = """
            SELECT * FROM Candidato
            WHERE cpf = :cpf
        """
        return sSQL
    }

    Integer inserirCandidato(Candidato candidato) {
        try {
            String sSQL = montarInserirCandidato()

            Map<String, Object> parametros = mapperUtils.converterObjectToMap(candidato)

            return conexao.inserir(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao inserir candidato", e)
        }
    }

    private String montarInserirCandidato() {
        String sSQL = """
            INSERT INTO Candidato (nome, sobrenome, cpf, data_nascimento, pais, cep, estado, descricao)
            VALUES (:nome, :sobrenome, :cpf, :dataNascimento, :pais, :cep, :estado, :descricao)
        """
        return sSQL
    }

    Boolean atualizarCandidato(Candidato candidato) {
        try {
            String sSQL = construirAtualizaCandidato()

            Map<String, Object> parametros = mapperUtils.converterObjectToMap(candidato)

            conexao.executar(sSQL, parametros)
            return true
        } catch (Exception e) {
            throw new Exception("Erro ao altera o candidato", e)
        }
    }

    private String construirAtualizaCandidato() {
        String sSQL = """
                UPDATE Candidato
                SET nome = :nome,
                    sobrenome = :sobrenome,
                    cpf = :cpf,
                    data_nascimento = :dataNascimento,
                    pais = :pais,
                    cep = :cep,
                    estado = :estado,
                    descricao = :descricao
                WHERE id_candidato = :id
            """
        return sSQL;
    }

    Boolean excluirCandidato(Integer idCandidato) {
        try {
            String sSQL = montarExcluirCandidato()

            Map<String, Integer> parametros = [idCandidato: idCandidato]

            conexao.executar(sSQL, parametros)

            return true
        } catch (Exception e) {
            throw new Exception("Erro ao excluir candidato", e)
        }
    }

    private String montarExcluirCandidato() {
        String sSQL = """
            DELETE FROM Candidato
            WHERE id_candidato = :idCandidato
        """
        return sSQL
    }
}
