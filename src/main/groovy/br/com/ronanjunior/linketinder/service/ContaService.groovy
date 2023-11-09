package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.ContaDao
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class ContaService {
    private final Conexao conexao
    private final MapperUtils mapperUtils
    private final ContaDao contaDao
    private final CandidatoService candidatoService
    private final EmpresaService empresaService

    ContaService() {
        this.conexao = new Conexao()
        this.mapperUtils = new MapperUtils()
        this.contaDao = new ContaDao(conexao, mapperUtils)
        this.candidatoService = new CandidatoService()
        this.empresaService = new EmpresaService()
    }

    ContaService(Conexao conexao, MapperUtils mapperUtils, ContaDao contaDao, CandidatoService candidatoService, EmpresaService empresaService) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.contaDao = contaDao
        this.candidatoService = candidatoService
        this.empresaService = empresaService
    }

    Boolean verificarExistenciaContaComEmail(Conta conta) {
        try {
            conexao.abrirConexao()
            Conta contaEncontrada =  this.montarBuscarContaPorEmail(conta.email)

            switch (contaEncontrada.id) {
                case null:
                    return false
                default:
                    return true
            }

        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    protected Conta montarBuscarContaPorEmail(String emailConta) {
        try {
            Map contaMap = contaDao.buscarContaPorEmail(emailConta)

            return new Conta(contaMap)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao buscar conta por email: ${e.message}", e)
        }
    }

    protected Conta montarBuscarContaPorEmailSenha(String emailConta, String senhaConta) {
        try {
            Conta conta
            Candidato candidato = null
            Empresa empresa = null
            Map contaMap = contaDao.buscarContaPorEmailSenha(emailConta, senhaConta)

            switch (contaMap.get("id_conta")) {
                case null:
                    conta = new Conta(contaMap)
                    break
                default:
                    if (contaMap.get("id_candidato"))
                        candidato = candidatoService.montarBuscarCandidatoPorId(contaMap.get("id_candidato") as Integer)
                    if (contaMap.get("id_empresa"))
                        empresa = empresaService.montarBuscarEmpresaPorId(contaMap.get("id_empresa") as Integer)
                    conta = new Conta(contaMap, candidato, empresa)
            }

            return conta
        } catch (Exception e) {
            throw new Exception("Houve um erro ao buscar conta por email: ${e.message}", e)
        }
    }

    protected Conta montarInserirConta(Conta conta) {
        try {
            Integer idConta = contaDao.inserirConta(conta)

            conta.setId(idConta)

            return conta
        } catch (Exception e) {
            throw new Exception("Houve um erro ao inserir uma nova conta: ${e.message}", e)
        }
    }
}
