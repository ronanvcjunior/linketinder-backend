package br.com.ronanjunior.linketinder.service

import br.com.ronanjunior.linketinder.dao.VagaDao
import br.com.ronanjunior.linketinder.dto.VagaListaDoCandidatoDto
import br.com.ronanjunior.linketinder.model.Candidato
import br.com.ronanjunior.linketinder.model.Competencia
import br.com.ronanjunior.linketinder.model.Empresa
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.utils.Conexao
import br.com.ronanjunior.linketinder.utils.MapperUtils

class VagaService {
    private final Conexao conexao
    private final MapperUtils mapperUtils
    private final VagaDao vagaDao
    private final VagaCompetenciaService vagaCompetenciaService

    VagaService() {
        this.conexao = new Conexao()
        this.mapperUtils = new MapperUtils()
        this.vagaDao = new VagaDao(conexao, mapperUtils)
        this.vagaCompetenciaService = new VagaCompetenciaService()
    }

    VagaService(Conexao conexao, MapperUtils mapperUtils, VagaDao vagaDao, VagaCompetenciaService vagaCompetenciaService) {
        this.conexao = conexao
        this.mapperUtils = mapperUtils
        this.vagaDao = vagaDao
        this.vagaCompetenciaService = vagaCompetenciaService
    }

    List<VagaListaDoCandidatoDto> listarVagasParaCandidato(Integer idCandidato) {
        try {
            conexao.abrirConexao()

            List<VagaListaDoCandidatoDto> vagas = this.montarListaVagasParaCandidato(idCandidato)

            conexao.commitTransacao()
            return vagas
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    List<Vaga> listarVagasParaEmpresa(Integer idEmpresa) {
        try {
            conexao.abrirConexao()

            List<Vaga> vagas = this.montarListaVagasParaEmpresa(idEmpresa)

            conexao.commitTransacao()
            return vagas
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Vaga buscarVagaPorId(Vaga vaga) {
        try {
            conexao.abrirConexao()

            vaga = this.montarBuscarVagaPorId(vaga.id)

            conexao.commitTransacao()
            return vaga
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Vaga inserirVaga(Vaga vaga) {
        try {
            conexao.abrirConexao()

            vaga.competencias.each { Competencia competencia -> {
                vagaCompetenciaService.montarInserirCompeteciaParaVaga(vaga.id, competencia.id)
            }}

            vaga = this.montarInserirVaga(vaga)

            conexao.commitTransacao()
            return vaga
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean alterarVaga(Vaga vaga) {
        try {
            conexao.abrirConexao()

            this.montarAlterarVaga(vaga)

            conexao.commitTransacao()
            return true
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    Boolean excluirVaga(Integer idCandidato) {
        try {
            conexao.abrirConexao()

            this.montarExcluirVaga(idCandidato)

            conexao.commitTransacao()
            return true
        } catch (Exception e) {
            conexao.rollbackTransacao()
            throw new Exception(e.message, e)
        } finally {
            conexao.fecharConexao()
        }
    }

    protected List<VagaListaDoCandidatoDto> montarListaVagasParaCandidato(Integer idCandidato) {
        try {
            List<VagaListaDoCandidatoDto> candidatosListaDaEmpresaDto = []

            List<Map> vagasParaCandidatoMap = vagaDao.listarVagasParaCandidato(idCandidato)
            vagasParaCandidatoMap.forEach { Map vagaMap ->
                List<Competencia> competencias = this.vagaCompetenciaService
                        .montarListaCompetenciaParaVaga(vagaMap.get("id_vaga") as Integer)
                candidatosListaDaEmpresaDto.push(new VagaListaDoCandidatoDto(vagaMap, competencias))
            }
            return candidatosListaDaEmpresaDto
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar lista de vagas para candidato: ${e.message}", e)
        }
    }

    protected List<Vaga> montarListaVagasParaEmpresa(Integer idEmpresa) {
        try {
            List<Vaga> vagas = []

            List<Map> vagasParaCandidatoMap = vagaDao.listarVagasParaCandidato(idEmpresa)
            vagasParaCandidatoMap.forEach { Map vagaMap ->
                List<Competencia> competencias = this.vagaCompetenciaService
                        .montarListaCompetenciaParaVaga(vagaMap.get("id_vaga") as Integer)
                vagas.push(new Vaga(vagaMap, competencias))
            }
            return vagas
        } catch (Exception e) {
            throw new Exception("Houve um erro ao montar lista de vagas para empresa: ${e.message}", e)
        }
    }

    protected Vaga montarBuscarVagaPorId(Integer idVaga) {
        try {
            Map vagaMap = vagaDao.buscarVagaPorId(idVaga)
            List<Competencia> competencias = this.vagaCompetenciaService
                    .montarListaCompetenciaParaVaga(vagaMap.get("id_vaga") as Integer)

            return new Vaga(vagaMap, competencias)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao buscar vaga por id: ${e.message}", e)
        }
    }

    protected Vaga montarInserirVaga(Vaga vaga) {
        try {
            Integer idVaga = vagaDao.inserirVaga(vaga)

            vaga.setId(idVaga)

            return vaga
        } catch (Exception e) {
            throw new Exception("Houve um erro ao inserir um nova vaga: ${e.message}", e)
        }
    }

    protected Boolean montarAlterarVaga(Vaga vaga) {
        try {
            return vagaDao.atualizarVaga(vaga)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao alterar os dados da vaga: ${e.message}", e)
        }
    }

    protected Boolean montarExcluirVaga(Integer idVaga) {
        try {
            return vagaDao.excluirVaga(idVaga)
        } catch (Exception e) {
            throw new Exception("Houve um erro ao excluir vaga: ${e.message}", e)
        }
    }
}
