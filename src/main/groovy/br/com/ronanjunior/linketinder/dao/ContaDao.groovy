package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.utils.MapperUtils
import br.com.ronanjunior.linketinder.model.Conta

class ContaDao {
    private final ConexaoRepository conexao
    private final MapperUtils mapperUtils

    ContaDao(ConexaoRepository conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    Map buscarContaPorId(Integer idConta) {
        try {
            String sSQL = this.construirConsultaContaPorId()

            Map<String, Integer> parametros = [idConta: idConta]

            return conexao.obterPrimeiraLinha(sSQL, parametros)

        } catch (Exception e) {
            throw new Exception("Erro ao buscar conta por id: ${e.message}", e)
        }
    }

    private String construirConsultaContaPorId() {
        String sSQL = """
            SELECT * FROM Conta
            WHERE id_conta = :idConta
        """
        return sSQL
    }

    Map buscarContaPorEmailSenha(String emailConta, String senhaConta) {
        try {
            String sSQL = this.construirConsultaContaPorEmailSenha()

            Map<String, Object> parametros = [
                    email: emailConta,
                    senha: senhaConta
            ]

            return conexao.obterPrimeiraLinha(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao buscar conta por email e senha: ${e.message}", e)
        }
    }

    private String construirConsultaContaPorEmailSenha() {
        String sSQL = """
            SELECT  
                id_conta AS id,
                email,
                senha,
                id_candidato,
                id_empresa
            FROM Conta
            WHERE email = :email
            AND senha = :senha
        """
        return sSQL
    }

    Map buscarContaPorEmail(String email) {
        try {
            String sSQL = this.construirConsultaContaPorEmail()

            Map<String, String> parametros = [email: email]

            return conexao.obterPrimeiraLinha(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao buscar conta por email: ${e.message}", e)
        }
    }

    private String construirConsultaContaPorEmail() {
        String sSQL = """
            SELECT  
                id_conta AS id,
                email,
                senha,
                id_candidato,
                id_empresa
            FROM Conta
            WHERE email = :email
        """
        return sSQL
    }

    Integer inserirConta(Conta conta) {
        try {
            String sSQL = montarInserirConta()

            Map<String, Object> parametros = [
                    email: conta.email,
                    senha: conta.senha,
                    idCandidato: conta.candidato?.id,
                    idEmpresa: conta.empresa?.id
            ]

            return conexao.inserir(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao inserir conta: ${e.message}", e)
        }
    }

    private String montarInserirConta() {
        String sSQL = """
            INSERT INTO Conta (email, senha, id_candidato, id_empresa)
            VALUES (:email, :senha, :idCandidato, :idEmpresa)
        """
        return sSQL
    }

}
