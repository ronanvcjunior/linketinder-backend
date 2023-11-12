package br.com.ronanjunior.linketinder.repository

interface ConexaoRepository {
    void abrirConexao()

    List<Map> obterLinhas(String sSQL, Map parametros)

    void executar(String sSQL, Map parametros)

    Integer inserir(String sSQL, Map parametros)

    Map obterPrimeiraLinha(String sSQL, Map parametros)

    void fecharConexao()

    void commitTransacao()

    void rollbackTransacao()
}