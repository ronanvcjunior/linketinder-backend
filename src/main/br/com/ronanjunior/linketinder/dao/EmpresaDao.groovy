package main.br.com.ronanjunior.linketinder.dao

import groovy.sql.Sql
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.utils.Conexao

class EmpresaDao {
    private final Conexao conexao

    EmpresaDao(Conexao conexao) {
        this.conexao = conexao
    }

    public List<Empresa> listarTodasEmpresas() {
        List<Empresa> empresas = []
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = """
                SELECT id_empresa, nome, cnpj, descricao, pais, cep
                FROM Empresa
            """
            sql.eachRow(sSQL) { linha ->
                Empresa empresa = new Empresa(
                        linha.id_empresa,
                        linha.nome,
                        linha.cnpj,
                        linha.descricao,
                        linha.pais,
                        linha.cep
                )
                empresas.add(empresa)
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return empresas
    }

    public Boolean cadastrarEmpresa(Empresa empresa) {
        String sSQL = """
            INSERT INTO Empresa (nome, cnpj, descricao, pais, cep)
            VALUES (
                '${empresa.nome}',
                '${empresa.cnpj}',
                '${empresa.descricao}',
                '${empresa.pais}',
                '${empresa.cep}'
            )
        """
        return executarUpdate(sSQL)
    }

    public Boolean atualizarEmpresa(Empresa empresa) {
        String sSQL = """
            UPDATE Empresa
            SET nome = '${empresa.nome}',
                cnpj = '${empresa.cnpj}',
                descricao = '${empresa.descricao}',
                pais = '${empresa.pais}',
                cep = '${empresa.cep}'
            WHERE id_empresa = ${empresa.id_empresa}
        """
        return executarUpdate(sSQL)
    }

    public Boolean excluirEmpresa(Integer idEmpresa) {
        String sSQL = "DELETE FROM Empresa WHERE id_empresa = ${idEmpresa};"
        return executarUpdate(sSQL)
    }

    public Empresa buscarEmpresaPorId(Integer idEmpresa) {
        Empresa empresa = null;
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = "SELECT * FROM Empresa WHERE id_empresa = ${idEmpresa}"
            sql.eachRow(sSQL) { linha ->
                empresa = new Empresa(
                        linha.id_empresa,
                        linha.nome,
                        linha.cnpj,
                        linha.pais,
                        linha.cep,
                        linha.descricao
                )
            }
            return empresa;
        } catch (Exception e) {
            e.printStackTrace()
            return empresa
        }
    }

    Boolean verificarExistenciaCnpjCadastrado(String cnpj) {
        try (Sql sql = conexao.abrirConexao()) {
            String sSQL = """
                SELECT * FROM Empresa
                WHERE cnpj = '${cnpj}'
            """

            Boolean empresaEncontrado = false

            sql.eachRow(sSQL) { linha ->
                empresaEncontrado = true
            }

            conexao.fecharConexao();
            return empresaEncontrado;
        } catch (Exception e) {
            e.printStackTrace()
            conexao.fecharConexao();
            return false;
        }
    }

    private Boolean executarUpdate(String sSQL) {
        try (Sql sql = conexao.abrirConexao()) {
            sql.execute(sSQL)
            return true
        } catch (Exception e) {
            e.printStackTrace()
            return false
        }
    }
}
