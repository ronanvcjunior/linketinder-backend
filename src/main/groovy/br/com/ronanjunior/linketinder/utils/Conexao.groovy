package br.com.ronanjunior.linketinder.utils

import groovy.sql.Sql
import io.github.cdimascio.dotenv.Dotenv

class Conexao {
    Dotenv dotenv = Dotenv.configure().load()
    private final String URL = dotenv.get("URL")
    private final String USUARIO = dotenv.get("USUARIO")
    private final String SENHA = dotenv.get("SENHA")
    private Sql sql

    public Sql abrirConexao() {
        this.sql = Sql.newInstance(URL, USUARIO, SENHA)
        return this.sql
    }

    public void fecharConexao() {
        this.sql.close()
    }

    public void iniciarTransacao() {
        this.sql.getConnection().setAutoCommit(false)
    }

    public void commitTransacao() {
        this.sql.getConnection().commit()
        this.sql.getConnection().setAutoCommit(true)
    }

    public void rollbackTransacao() {
        this.sql.getConnection().rollback()
        this.sql.getConnection().setAutoCommit(true)
    }
}
