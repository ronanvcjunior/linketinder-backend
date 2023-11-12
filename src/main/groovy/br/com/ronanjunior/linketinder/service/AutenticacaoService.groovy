package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.repository.ConexaoRepository
import br.com.ronanjunior.linketinder.utils.MapperUtils

class AutenticacaoService {
    private final ConexaoRepository conexao
    private final MapperUtils mapperUtils
    private final ContaService contaService
    private final CandidatoService candidatoService
    private final EmpresaService empresaService
    private final CandidatoCompetenciaService candidatoCompetenciaService

    AutenticacaoService(ConexaoRepository conexao, MapperUtils mapperUtils, ContaService contaService, CandidatoService candidatoService, EmpresaService empresaService, CandidatoCompetenciaService candidatoCompetenciaService) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.contaService = contaService
        this.candidatoService = candidatoService
        this.empresaService = empresaService
        this.candidatoCompetenciaService = candidatoCompetenciaService
    }

    AutenticacaoService(ConexaoRepository conexao, MapperUtils mapperUtils) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.contaService = new ContaService(conexao, mapperUtils)
        this.candidatoService = new CandidatoService(conexao, mapperUtils)
        this.empresaService = new EmpresaService(conexao, mapperUtils)
        this.candidatoCompetenciaService = new CandidatoCompetenciaService(conexao, mapperUtils)
    }

    Conta registrarUsuario(Conta conta) {
        try {
            conexao.abrirConexao()

            if (conta.candidato) {
                conta.candidato = candidatoService.montarInserirCandidato(conta.candidato)

                conta.candidato.competencias.each { Competencia competencia ->
                    candidatoCompetenciaService.montarInserirCompeteciaParaCandidato(conta.candidato.id, competencia.id)
                }
            }

            if (conta.empresa) {
                conta.empresa = empresaService.montarInserirEmpresa(conta.empresa)
            }

            Conta contaInserida = contaService.montarInserirConta(conta)

            conexao.commitTransacao()
            return  contaInserida
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Conta login(String email, String senha) {
        try {
            conexao.abrirConexao()

            Conta conta = contaService.montarBuscarContaPorEmailSenha(email, senha)
            conexao.commitTransacao()
            return  conta
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }
}
