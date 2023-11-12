package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.EmpresaDao
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class EmpresaService {
    private final Conexao conexao
    private final MapperUtils mapperUtils
    private final EmpresaDao empresaDao

    EmpresaService(Conexao conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
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

            empresa = this.montarBuscarEmpresaPorId(empresa.id)

            conexao.commitTransacao()
            return empresa
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean verificarExistenciaEmpresaPorCnpj(String cnpj) {
        try {
            conexao.abrirConexao()

            Empresa empresa = this.montarBuscarEmpresaPorCnpj(cnpj)

            conexao.commitTransacao()
            if (empresa.id)
                return true
            return false
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Empresa inserirEmpresa(Empresa empresa) {
        try {
            conexao.abrirConexao()

            Empresa empresaGravada = this.montarInserirEmpresa(empresa)

            conexao.commitTransacao()
            return empresaGravada
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean alterarEmpresa(Empresa empresa) {
        try {
            conexao.abrirConexao()

            this.montarAlterarEmpresa(empresa)

            conexao.commitTransacao()
            return true
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean excluirEmpresa(Empresa empresa) {
        try {
            conexao.abrirConexao()

            this.montarExcluirEmpresa(empresa.id)

            conexao.commitTransacao()
            return true
        } catch (Exception e) {
            conexao.rollbackTransacao()
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
            throw new Exception("Houve um erro ao montar busca empresa por id: ${e.message}", e)
        }
    }

    protected Empresa montarBuscarEmpresaPorCnpj(String cnpj) {
        try {
            Map empresaMap = empresaDao.buscarEmpresaPorCnpj(cnpj)
            return new Empresa(empresaMap)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar busca empresa por cnpj: ${e.message}", e)
        }
    }

    protected Empresa montarInserirEmpresa(Empresa empresa) {
        try {
            Integer idEmpresa = empresaDao.inserirEmpresa(empresa)

            empresa.setId(idEmpresa)

            return empresa
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar inserir nova empresa: ${e.message}", e)
        }
    }

    protected Boolean montarAlterarEmpresa(Empresa empresa) {
        try {
            return empresaDao.atualizarEmpresa(empresa)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar alterar empresa: ${e.message}", e)
        }
    }

    protected Boolean montarExcluirEmpresa(Integer idEmpresa) {
        try {
            return empresaDao.excluirEmpresa(idEmpresa)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar excluir empresa: ${e.message}", e)
        }
    }
}
