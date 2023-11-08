package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.CandidatoDao
import br.com.ronanjunior.linketinder.dto.CandidatoListaDaEmpresaDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class CandidatoService {
    private final Conexao conexao
    private final MapperUtils mapperUtils
    private final CandidatoDao candidatoDao
    private final CandidatoCompetenciaService candidatoCompetenciaService

    CandidatoService() {
        this.conexao = new Conexao()
        this.mapperUtils = new MapperUtils()
        this.candidatoDao = new CandidatoDao(conexao, mapperUtils)
        this.candidatoCompetenciaService = new CandidatoCompetenciaService()
    }

    CandidatoService(Conexao conexao, MapperUtils mapperUtils, CandidatoDao candidatoDao, CandidatoCompetenciaService candidatoCompetenciaService) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.candidatoDao = candidatoDao
        this.candidatoCompetenciaService = candidatoCompetenciaService
    }

    List<CandidatoListaDaEmpresaDto> listarCandidatosParaEmpresa(Empresa empresa) {
        try {
            conexao.abrirConexao()
            return this.montarListaCandidatosParaEmpresa(empresa.id)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao listar de candidatos para empresa: ${e.message}", e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Candidato buscarCandidatoPorId(Candidato candidato) {
        try {
            conexao.abrirConexao()
            return this.montarBuscarCandidatoPorId(candidato.id)
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Candidato buscarCandidatoPorCpf(Candidato candidato) {
        try {
            conexao.abrirConexao()
            return this.montarBuscarCandidatoPorCpf(candidato.cpf)
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Candidato inserirCandidato(Candidato candidato) {
        try {
            conexao.abrirConexao()
            return this.montarInserirCandidato(candidato)
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean alterarCandidato(Candidato candidato) {
        try {
            conexao.abrirConexao()
            return this.montarAlterarCandidato(candidato)
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean excluirCandidato(Candidato candidato) {
        try {
            conexao.abrirConexao()
            return this.montarExcluirCandidato(candidato.id)
        } catch (Exception e) {
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    protected List<CandidatoListaDaEmpresaDto> montarListaCandidatosParaEmpresa(Integer idEmpresa) {
        try {
            List<CandidatoListaDaEmpresaDto> candidatosListaDaEmpresaDto = []

            List<Map> candidatosParaEmpresaEmMap = candidatoDao.listarCandidatosParaEmpresa(idEmpresa)
            candidatosParaEmpresaEmMap.forEach { Map candidatoMap ->
                List<Competencia> competencias = candidatoCompetenciaService
                        .montarListaCompetenciaParaCandidato(candidatoMap.get("id_candidato") as Integer)
                candidatosListaDaEmpresaDto.push(new CandidatoListaDaEmpresaDto(candidatoMap, competencias))
            }
            return candidatosListaDaEmpresaDto
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar lista de candidatos para empresa: ${e.message}", e)
        }
    }

    protected Candidato montarBuscarCandidatoPorId(Integer idCandidato) {
        try {
            Map candidatoMap = candidatoDao.buscarCandidatoPorId(idCandidato)
            List<Competencia> competencias = candidatoCompetenciaService
                    .montarListaCompetenciaParaCandidato(candidatoMap.get("id_candidato") as Integer)
            return new Candidato(candidatoMap, competencias)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao buscar os dados do candidato por id: ${e.message}", e)
        }
    }

    protected Candidato montarBuscarCandidatoPorCpf(String cpf) {
        try {
            Map candidatoMap = candidatoDao.buscarCandidatoPorCpf(cpf)
            List<Competencia> competencias = candidatoCompetenciaService
                    .montarListaCompetenciaParaCandidato(candidatoMap.get("id_candidato") as Integer)
            return new Candidato(candidatoMap, competencias)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao buscar os dados do candidato por cpf: ${e.message}", e)
        }
    }

    protected Candidato montarInserirCandidato(Candidato candidato) {
        try {
            Integer idCandidato = candidatoDao.inserirCandidato(candidato)

            candidato.setId(idCandidato)

            return candidato
        } catch (Exception e) {
            throw new Exception("Houve um erro ao inserir um novo candidato: ${e.message}", e)
        }
    }

    protected Boolean montarAlterarCandidato(Candidato candidato) {
        try {
            return candidatoDao.atualizarCandidato(candidato)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao alterar os dados do candidato: ${e.message}", e)
        }
    }

    protected Boolean montarExcluirCandidato(Integer idCandidato) {
        try {
            return candidatoDao.excluirCandidato(idCandidato)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao excluir candidato: ${e.message}", e)
        }
    }
}
