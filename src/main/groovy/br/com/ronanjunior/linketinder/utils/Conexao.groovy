package br.com.ronanjunior.linketinder.utils

import groovy.sql.Sql

class Conexao {
    private final String URL = "jdbc:postgresql://localhost:5432/db_linketinder"
    private final String USUARIO = "ronan"
    private final String SENHA = "kubuntu"
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
