package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.sql.Sql
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.Conexao

class EmpresaDao {
    private final Conexao conexao
    private final MapperUtils mapperUtils

    EmpresaDao(Conexao conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    Integer inserirEmpresa(Empresa empresa) {
        try {
            String sSQL = montarInserirEmpresa()

            Map<String, Object> parametros = mapperUtils.converterObjectToMap(empresa)

            return conexao.inserir(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao inserir empresa", e)
        }
    }

    private String montarInserirEmpresa() {
        String sSQL = """
            INSERT INTO Empresa (nome, cnpj, descricao, pais, cep)
            VALUES (:nome, :cnpj, :descricao, :pais, :cep)
        """
        return sSQL
    }

    Boolean excluirEmpresa(Integer idEmpresa) {
        try {
            String sSQL = montarExcluirEmpresa()

            Map<String, Integer> parametros = [idEmpresa: idEmpresa]

            conexao.executar(sSQL, parametros)

            return true
        } catch (Exception e) {
            throw new Exception("Erro ao excluir empresa", e)
        }
    }

    private String montarExcluirEmpresa() {
        String sSQL = """
            DELETE FROM Empresa
            WHERE id_empresa = :idEmpresa
        """
        return sSQL
    }

    Map buscarEmpresaPorId(Integer idEmpresa) {
        try {
            String sSQL = this.construirConsultaEmpresaPorId()

            Map<String, Integer> parametros = [idEmpresa: idEmpresa]

            return conexao.obterPrimeiraLinha(sSQL, parametros)

        } catch (Exception e) {
            throw new Exception("Erro ao buscar empresa por id", e)
        }
    }

    private String construirConsultaEmpresaPorId() {
        String sSQL = """
            SELECT * FROM Empresa
            WHERE id_empresa = :idEmpresa
        """
        return sSQL
    }

    Map buscarEmpresaPorCnpj(String cnpj) {
        try {
            String sSQL = this.construirConsultaEmpresaPorCnpj()

            Map<String, String> parametros = [cnpj: cnpj]

            return conexao.obterPrimeiraLinha(sSQL, parametros)

        } catch (Exception e) {
            throw new Exception("Erro ao verificar existência de empresa por cnpj", e)
        }
    }

    private String construirConsultaEmpresaPorCnpj() {
        String sSQL = """
            SELECT * FROM Empresa
            WHERE cnpj = :cnpj
        """
        return sSQL
    }
}
