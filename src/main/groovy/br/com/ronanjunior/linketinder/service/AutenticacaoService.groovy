package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class AutenticacaoService {
    private final Conexao conexao
    private final MapperUtils mapperUtils
    private final ContaService contaService
    private final CandidatoService candidatoService
    private final EmpresaService empresaService
    private final CandidatoCompetenciaService candidatoCompetenciaService

    AutenticacaoService() {
        this.conexao = new Conexao()
        this.mapperUtils = new MapperUtils()
        this.contaService = new ContaService()
        this.candidatoService = new CandidatoService()
        this.empresaService = new EmpresaService()
        this.candidatoCompetenciaService = new CandidatoCompetenciaService()
    }

    AutenticacaoService(Conexao conexao, MapperUtils mapperUtils, ContaService contaService, CandidatoService candidatoService, EmpresaService empresaService, CandidatoCompetenciaService candidatoCompetenciaService) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.contaService = contaService
        this.candidatoService = candidatoService
        this.empresaService = empresaService
        this.candidatoCompetenciaService = candidatoCompetenciaService
    }

    Conta registrarUsuario(Conta conta) {
        try {
            conexao.abrirConexao()

            if (conta.candidato) {
                conta.candidato.competencias.each { Competencia competencia ->
                    candidatoCompetenciaService.montarInserirCompeteciaParaCandidato(conta.candidato.id, competencia.id)
                }
                conta.candidato = candidatoService.montarInserirCandidato(conta.candidato)
            }

            if (conta.empresa)
                conta.empresa = empresaService.montarInserirEmpresa(conta.empresa)

            conexao.commitTransacao()
            return  contaService.montarInserirConta(conta)
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Conta Login(Conta conta) {
        try {
            conexao.abrirConexao()

            conexao.commitTransacao()
            return  contaService.montarBuscarContaPorEmailSenha(conta.email, conta.senha)
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }
}
