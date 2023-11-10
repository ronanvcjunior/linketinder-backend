package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.EmpresaDao
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class EmpresaService {
    private final Conexao conexao
    private final MapperUtils mapperUtils
    private final EmpresaDao empresaDao

    EmpresaService() {
        this.conexao = new Conexao()
        this.mapperUtils = new MapperUtils()
        this.empresaDao = new EmpresaDao(conexao, mapperUtils)
    }

    EmpresaService(Conexao conexao, MapperUtils mapperUtils, EmpresaDao empresaDao) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.empresaDao = empresaDao
    }

    Empresa buscarEmpresaPorId(Empresa empresa) {
        try {
            conexao.abrirConexao()
            return this.montarBuscarEmpresaPorId(empresa.id)
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean verificarExistenciaEmpresaPorCnpj(String cnpj) {
        try {
            conexao.abrirConexao()
            Empresa empresa = this.montarBuscarEmpresaPorCnpj(cnpj)

            if (empresa.id)
                return true
            return false
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Empresa inserirEmpresa(Empresa empresa) {
        try {
            conexao.abrirConexao()
            return this.montarInserirEmpresa(empresa)
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean alterarEmpresa(Empresa empresa) {
        try {
            conexao.abrirConexao()
            return this.montarAlterarEmpresa(empresa)
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean excluirEmpresa(Empresa empresa) {
        try {
            conexao.abrirConexao()
            return this.montarExcluirEmpresa(empresa.id)
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    protected Empresa montarBuscarEmpresaPorId(Integer idEmpresa) {
        try {
            Map empresaMap = empresaDao.buscarEmpresaPorId(idEmpresa)
            return new Empresa(empresaMap)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao buscar empresa por id: ${e.message}", e)
        }
    }

    protected Empresa montarBuscarEmpresaPorCnpj(String cnpj) {
        try {
            Map empresaMap = empresaDao.buscarEmpresaPorCnpj(cnpj)
            return new Empresa(empresaMap)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao buscar empresa por cnpj: ${e.message}", e)
        }
    }

    protected Empresa montarInserirEmpresa(Empresa empresa) {
        try {
            Integer idEmpresa = empresaDao.inserirEmpresa(empresa)

            empresa.setId(idEmpresa)

            return empresa
        } catch (Exception e) {
            throw new Exception("Houve um erro ao inserir um nova empresa: ${e.message}", e)
        }
    }

    protected Boolean montarAlterarEmpresa(Empresa empresa) {
        try {
            return empresaDao.atualizarEmpresa(empresa)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao alterar os dados da empresa: ${e.message}", e)
        }
    }

    protected Boolean montarExcluirEmpresa(Integer idEmpresa) {
        try {
            return empresaDao.excluirEmpresa(idEmpresa)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao excluir empresa: ${e.message}", e)
        }
    }
}
