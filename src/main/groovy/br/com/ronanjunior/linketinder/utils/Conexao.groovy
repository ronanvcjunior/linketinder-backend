package br.com.ronanjunior.linketinder.utils

import groovy.sql.Sql
import io.github.cdimascio.dotenv.Dotenv
import org.postgresql.util.PSQLException

class Conexao {
    private String url
    private String usuario
    private String senha
    private Sql sql

    Conexao(Dotenv dotenv) {
        try {
            this.url = dotenv.get("URL")
            this.usuario = dotenv.get("USUARIO")
            this.senha = dotenv.get("SENHA")
        } catch (NullPointerException e) {
            throw new NullPointerException("A variável dotenv não pode ser nula: \n" + e.getMessage())
        }
    }

    Sql abrirConexao() {
        try {
            this.sql = Sql.newInstance(this.url, this.usuario, this.senha)
            return this.sql
        } catch (ConnectException e) {
            throw new ConnectException("Erro ao abrir conexão com o banco de dados: \n" + e.getMessage())
        } catch (NullPointerException e) {
            throw new NullPointerException("Erro ao abrir conexão com o banco de dados, não há conexão: \n" + e.getMessage())
        } catch (PSQLException e) {
            throw new PSQLException("Erro ao abrir conexão com o banco de dados.", null, e)
        }
    }

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

    void iniciarTransacao() {
        if (!this.sql)

        try {
            this.sql.getConnection().setAutoCommit(false)
        } catch (ConnectException e) {
            throw new ConnectException("Erro ao iniciar transação no banco de dados: \n" + e.getMessage())
        } catch (NullPointerException e) {
            throw new NullPointerException("Erro ao iniciar transação no banco de dados, não há conexão: \n" + e.getMessage())
        } catch (PSQLException e) {
            throw new PSQLException("Erro ao iniciar transação no banco de dados.", null, e)
        }
    }

    void commitTransacao() {
        try {
            this.sql.getConnection().commit()
            this.sql.getConnection().setAutoCommit(true)
        } catch (ConnectException e) {
            throw new ConnectException("Erro ao fazer commit da transação no banco de dados: \n" + e.getMessage())
        } catch (NullPointerException e) {
            throw new NullPointerException("Erro ao fazer commit da transação no banco de dados, não há conexão: \n" + e.getMessage())
        } catch (PSQLException e) {
            throw new PSQLException("Erro ao fazer commit da transação no banco de dados.", null, e)
        }
    }

    void rollbackTransacao() {
        try {
            this.sql.getConnection().rollback()
            this.sql.getConnection().setAutoCommit(true)
        } catch (ConnectException e) {
            throw new ConnectException("Erro ao fazer rollback da transação no banco de dados: \n" + e.getMessage())
        } catch (NullPointerException e) {
            throw new NullPointerException("Erro ao fazer rollback da transação no banco de dados, não há conexão: \n" + e.getMessage())
        } catch (PSQLException e) {
            throw new PSQLException("Erro ao fazer rollback da transação no banco de dados.", null, e)
        }
    }
}
