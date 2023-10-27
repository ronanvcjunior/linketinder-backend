package br.com.ronanjunior.linketinder.dao

import groovy.sql.Sql
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.Conexao

class ContaDao {
    private final Conexao conexao

    ContaDao(Conexao conexao) {
        this.conexao = conexao
    }

//    void iniciarTransacao() {
//        try {
//
//        } catch ()
//    }

    Integer registrarEmpresa(Conta conta) {
        try (Sql sql = conexao.abrirConexao()) {
            conexao.iniciarTransacao()

            Integer empresaId = cadastrarEmpresa(conta.empresa, sql)
            if (empresaId == null) {
                conexao.rollbackTransacao()
                conexao.fecharConexao()
                return null
            }

            conta.empresa.id = empresaId
            Integer contaId = cadastrarConta(conta, sql)

            if (contaId == null) {
                conexao.rollbackTransacao()
                conexao.fecharConexao()
                return null
            }

            conexao.commitTransacao()
            conexao.fecharConexao()
            return contaId
        } catch (Exception e) {
            println e
            conexao.rollbackTransacao()
            conexao.fecharConexao()
            return null
        }
    }

    Integer registrarCandidato(Conta conta) {
        try (Sql sql = conexao.abrirConexao()) {
            conexao.iniciarTransacao()

            Integer candidatoId = cadastrarCandidato(conta.candidato, sql)
            if (candidatoId == null) {
                conexao.rollbackTransacao()
                conexao.fecharConexao()
                return null
            }

            this.cadastrarCompetenciaDoCandidato(candidatoId, conta.candidato.competencias, sql)

            conta.candidato.id = candidatoId
            Integer contaId = cadastrarConta(conta, sql)

            if (contaId == null) {
                conexao.rollbackTransacao()
                return null
            }

            conexao.commitTransacao()
            return contaId
        } catch (Exception e) {
            e.printStackTrace()
            conexao.rollbackTransacao()
            return null
        }
    }

    Conta realizarLogin(String email, String senha) {
        Candidato candidato = null
        Empresa empresa = null
        Conta conta = null
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = """
                SELECT * FROM Conta
                WHERE email = '${email}' AND senha = '${senha}'
            """
            sql.eachRow(sSQL) { linha ->
                if (linha.id_candidato) {
                    CandidatoDao candidatoDao = new CandidatoDao(conexao)
                    candidato = candidatoDao.buscarCandidatoPorId(linha.id_candidato)
                } else if (linha.id_empresa) {
                    EmpresaDao empresaDao = new EmpresaDao(conexao)
                    empresa = empresaDao.buscarEmpresaPorId(linha.id_Empresa)
                }
                conta = new Conta(
                        linha.id_conta,
                        linha.email,
                        linha.senha,
                        candidato,
                        empresa
                )
            }
            conexao.fecharConexao()
            return conta
        } catch (Exception e) {
            e.printStackTrace()
            conexao.fecharConexao()
            return null
        }
    }

    Conta buscarContaPorId(Integer idConta) {
        Candidato candidato = null
        Empresa empresa = null
        Conta conta = null
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = "SELECT * FROM Conta WHERE id_conta = ${idConta}"
            sql.eachRow(sSQL) { linha ->
                if (linha.id_candidato) {
                    CandidatoDao candidatoDao = new CandidatoDao(conexao)
                    candidato = candidatoDao.buscarCandidatoPorId(linha.id_candidato)
                } else if (linha.id_empresa) {
                    EmpresaDao empresaDao = new EmpresaDao(conexao)
                    empresa = empresaDao.buscarEmpresaPorId(linha.id_Empresa)
                }
                conta = new Conta(
                        linha.id_conta,
                        linha.email,
                        linha.senha,
                        candidato,
                        empresa
                )
            }
            conexao.fecharConexao()
            return conta
        } catch (Exception e) {
            e.printStackTrace()
            conexao.fecharConexao()
            return conta
        }
    }

    Boolean verificarExistenciaEmailCadastrado(String email) {
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = """
                SELECT * FROM Conta
                WHERE email = '${email}'
            """

            Boolean contaEncontrada = false

            sql.eachRow(sSQL) { linha ->
                contaEncontrada = true
            }

            conexao.fecharConexao()
            return contaEncontrada
        } catch (Exception e) {
            e.printStackTrace()
            conexao.fecharConexao()
            return false
        }
    }

    Integer cadastrarEmpresa(Empresa empresa, Sql sql) {
        String sSQL = """
            INSERT INTO Empresa (nome, cnpj, descricao, pais, cep)
            VALUES (
                '${empresa.nome}',
                '${empresa.cnpj}',
                '${empresa.descricao}',
                '${empresa.pais}',
                '${empresa.cep}'
            )
            RETURNING id_empresa
        """
        List<List<Object>> resultado = sql.executeInsert(sSQL)
        if (resultado)
            return resultado[0][0]
        else
            return null
    }

    Integer cadastrarCandidato(Candidato candidato, Sql sql) {
        String sSQL = """
            INSERT INTO Candidato (nome, sobrenome, cpf, data_nascimento, pais, cep, estado, descricao)
            VALUES (
                '${candidato.nome}',
                '${candidato.sobrenome}',
                '${candidato.cpf}',
                '${candidato.dataNascimento}',
                '${candidato.pais}',
                '${candidato.cep}',
                '${candidato.estado}',
                '${candidato.descricao}'
            )
            RETURNING id_candidato
        """
        List<List<Object>> resultado = sql.executeInsert(sSQL)
        if (resultado)
            return resultado[0][0]
        else
            return null
    }

    private void cadastrarCompetenciaDoCandidato(Integer idCandidato, List<Competencia> competencias, Sql sql) {
        competencias.forEach {Competencia competencia -> {
            String sSQL = """
                INSERT INTO candidato_competencia (id_candidato, id_competencia)
                VALUES (
                    ${idCandidato},
                    ${competencia.id}
                )
                RETURNING id_candidato
            """
            sql.executeInsert(sSQL)
        }}
    }

    private Integer cadastrarConta(Conta conta, Sql sql) {
        Integer idCandidato = null
        Integer idEmpresa = null
        if (conta.candidato)
            idCandidato = conta.candidato.id
        else if (conta.empresa)
            idEmpresa = conta.empresa.id
        String sSQL = """
            INSERT INTO Conta (email, senha, id_candidato, id_empresa)
            VALUES (
                '${conta.email}',
                '${conta.senha}',
                ${idCandidato},
                ${idEmpresa}
            )
            RETURNING id_conta
        """
        List<List<Object>> resultado = sql.executeInsert(sSQL)
        if (resultado)
            return resultado[0][0]
        else
            return null
    }
}
