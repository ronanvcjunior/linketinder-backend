package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.ContaDao
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.utils.MapperUtils

class ContaService {
    private final ConexaoRepository conexao
    private final MapperUtils mapperUtils
    private final ContaDao contaDao
    private final CandidatoService candidatoService
    private final EmpresaService empresaService

    ContaService(ConexaoRepository conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.contaDao = new ContaDao(conexao, mapperUtils)
        this.candidatoService = new CandidatoService(conexao, mapperUtils)
        this.empresaService = new EmpresaService(conexao, mapperUtils)
    }

    ContaService(ConexaoRepository conexao, MapperUtils mapperUtils, ContaDao contaDao, CandidatoService candidatoService, EmpresaService empresaService) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.contaDao = contaDao
        this.candidatoService = candidatoService
        this.empresaService = empresaService
    }

    Boolean verificarExistenciaContaComEmail(String email) {
        try {
            conexao.abrirConexao()

            Conta contaEncontrada =  this.montarBuscarContaPorEmail(email)

            conexao.commitTransacao()
            switch (contaEncontrada.id) {
                case null:
                    return false
                default:
                    return true
            }
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    protected Conta montarBuscarContaPorEmail(String emailConta) {
        try {
            Map contaMap = contaDao.buscarContaPorEmail(emailConta)

            return mapperUtils.converterMapToObject(contaMap, Conta)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar busca conta por email: ${e.message}", e)
        }
    }

    protected Conta montarBuscarContaPorEmailSenha(String emailConta, String senhaConta) {
        try {
            Conta conta
            Candidato candidato = null
            Empresa empresa = null
            Map contaMap = contaDao.buscarContaPorEmailSenha(emailConta, senhaConta)

            switch (contaMap.get("id")) {
                case null:
                    conta = mapperUtils.converterMapToObject(contaMap, Conta)
                    break
                default:
                    if (contaMap.get("id_candidato"))
                        candidato = candidatoService.montarBuscarCandidatoPorId(contaMap.get("id_candidato") as Integer)
                    if (contaMap.get("id_empresa"))
                        empresa = empresaService.montarBuscarEmpresaPorId(contaMap.get("id_empresa") as Integer)

                    conta = mapperUtils.converterMapToObject(contaMap, Conta)
                    conta.setCandidato(candidato)
                    conta.setEmpresa(empresa)
            }

            return conta
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar busca conta por email e senha: ${e.message}", e)
        }
    }

    protected Conta montarInserirConta(Conta conta) {
        try {
            Integer idConta = contaDao.inserirConta(conta)

            conta.setId(idConta)

            return conta
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar inserir nova conta: ${e.message}", e)
        }
    }
}
