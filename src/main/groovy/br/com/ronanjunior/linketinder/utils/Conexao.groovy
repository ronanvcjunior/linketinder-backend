package br.com.ronanjunior.linketinder.utils

import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import groovy.sql.Sql
import io.github.cdimascio.dotenv.Dotenv
import org.postgresql.util.PSQLException

class Conexao implements ConexaoRepository {
    private static Conexao instancia
    private String url = "jdbc:postgresql://localhost:5432/db_linketinder"
    private String usuario = "ronan"
    private String senha = "kubuntu"
    private Sql sql

    private Conexao() {}

    static Conexao obterInstancia() {
        if (instancia == null) {
            instancia = new Conexao()
        }
        return instancia
    }

    @Override
    void abrirConexao() {
        try {
            Class.forName("org.postgresql.Driver")
            this.sql = Sql.newInstance(this.url, this.usuario, this.senha)
            this.sql.getConnection().setAutoCommit(false)
        } catch (ConnectException e) {
            throw new ConnectException("Erro ao abrir conexão com o banco de dados: \n" + e.getMessage())
        } catch (NullPointerException e) {
            throw new NullPointerException("Erro ao abrir conexão com o banco de dados, não há conexão: \n" + e.getMessage())
        } catch (PSQLException e) {
            throw new PSQLException("Erro ao abrir conexão com o banco de dados.", null, e)
        } catch (Exception e ) {
            throw new Exception("Erro", e)
        }
    }

    @Override
    List<Map> obterLinhas(String sSQL, Map parametros = [:]) {
        try {
            List<Map> linhas = sql.rows(sSQL, parametros)

            return linhas ? linhas : []
        } catch (Exception e) {
            throw new Exception("Erro na consulta com rows: " + e.message, e)
        }
    }

    @Override
    void executar(String sSQL, Map parametros = [:]) {
        try {
            sql.execute(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro na consulta com execute: " + e.message, e)
        }
    }

    @Override
    Integer inserir(String sSQL, Map parametros = [:]) {
        try {
            List<List<Object>> inserido = sql.executeInsert(sSQL, parametros)
            return inserido[0][0]
        } catch (Exception e) {
            throw new Exception("Erro ao inserir informações no banco de dados: " + e.message, e)
        }
    }

    @Override
    Map obterPrimeiraLinha(String sSQL, Map parametros = [:]) {
        try {
            Map linha = sql.firstRow(sSQL, parametros)
            return linha ? linha : [:]
        } catch (Exception e) {
            throw new Exception("Erro na consulta com firstRow: " + e.message, e)
        }
    }

    @Override
    void fecharConexao() {
        try {
            this.sql.close()
        } catch (ConnectException e) {
            throw new ConnectException("Erro ao fechar conexão com o banco de dados: \n" + e.getMessage())
        } catch (NullPointerException e) {
            throw new NullPointerException("Erro ao fechar conexão com o banco de dados, não há conexão: \n" + e.getMessage())
        } catch (PSQLException e) {
            throw new PSQLException("Erro ao fechar conexão com o banco de dados.", null, e)
        }
    }

    @Override
    void commitTransacao() {
        try {
            this.sql.getConnection().commit()
        } catch (ConnectException e) {
            throw new ConnectException("Erro ao fazer commit da transação no banco de dados: \n" + e.getMessage())
        } catch (NullPointerException e) {
            throw new NullPointerException("Erro ao fazer commit da transação no banco de dados, não há conexão: \n" + e.getMessage())
        } catch (PSQLException e) {
            throw new PSQLException("Erro ao fazer commit da transação no banco de dados.", null, e)
        }
    }

    @Override
    void rollbackTransacao() {
        try {
            this.sql.getConnection().rollback()
        } catch (ConnectException e) {
            throw new ConnectException("Erro ao fazer rollback da transação no banco de dados: \n" + e.getMessage())
        } catch (NullPointerException e) {
            throw new NullPointerException("Erro ao fazer rollback da transação no banco de dados, não há conexão: \n" + e.getMessage())
        } catch (PSQLException e) {
            throw new PSQLException("Erro ao fazer rollback da transação no banco de dados.", null, e)
        }
    }
}
