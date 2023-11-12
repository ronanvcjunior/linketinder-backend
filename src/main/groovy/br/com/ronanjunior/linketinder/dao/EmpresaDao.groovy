package br.com.ronanjunior.linketinder.dao

import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.utils.MapperUtils
import br.com.ronanjunior.linketinder.model.Empresa

class EmpresaDao {
    private final ConexaoRepository conexao
    private final MapperUtils mapperUtils

    EmpresaDao(ConexaoRepository conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
    }

    Map buscarEmpresaPorId(Integer idEmpresa) {
        try {
            String sSQL = this.construirConsultaEmpresaPorId()

            Map<String, Integer> parametros = [idEmpresa: idEmpresa]

            return conexao.obterPrimeiraLinha(sSQL, parametros)

        } catch (Exception e) {
            throw new Exception("Erro ao buscar empresa por id: ${e.message}", e)
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
            throw new Exception("Erro ao  buscar empresa por cnpj: ${e.message}", e)
        }
    }

    private String construirConsultaEmpresaPorCnpj() {
        String sSQL = """
            SELECT * FROM Empresa
            WHERE cnpj = :cnpj
        """
        return sSQL
    }

    Integer inserirEmpresa(Empresa empresa) {
        try {
            String sSQL = montarInserirEmpresa()

            Map<String, Object> parametros = mapperUtils.converterObjectToMap(empresa)

            return conexao.inserir(sSQL, parametros)
        } catch (Exception e) {
            throw new Exception("Erro ao inserir empresa: ${e.message}", e)
        }
    }

    private String montarInserirEmpresa() {
        String sSQL = """
            INSERT INTO Empresa (nome, cnpj, descricao, pais, cep)
            VALUES (:nome, :cnpj, :descricao, :pais, :cep)
        """
        return sSQL
    }

    Boolean atualizarEmpresa(Empresa empresa) {
        try {
            String sSQL = construirAtualizaEmpresa()

            Map<String, Object> parametros = mapperUtils.converterObjectToMap(empresa)

            conexao.executar(sSQL, parametros)
            return true
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar a empresa: ${e.message}", e)
        }
    }

    private String construirAtualizaEmpresa() {
        String sSQL = """
                UPDATE Empresa
                SET nome = :nome,
                    cnpj = :cnpj,
                    pais = :pais,
                    cep = :cep,
                    descricao = :descricao
                WHERE id_empresa = :id
            """
        return sSQL;
    }

    Boolean excluirEmpresa(Integer idEmpresa) {
        try {
            String sSQL = montarExcluirEmpresa()

            Map<String, Integer> parametros = [idEmpresa: idEmpresa]

            conexao.executar(sSQL, parametros)

            return true
        } catch (Exception e) {
            throw new Exception("Erro ao excluir empresa: ${e.message}", e)
        }
    }

    private String montarExcluirEmpresa() {
        String sSQL = """
            DELETE FROM Empresa
            WHERE id_empresa = :idEmpresa
        """
        return sSQL
    }
}
