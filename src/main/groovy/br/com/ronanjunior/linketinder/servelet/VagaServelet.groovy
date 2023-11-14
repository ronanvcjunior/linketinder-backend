package br.com.ronanjunior.linketinder.servelet

import br.com.ronanjunior.linketinder.controller.VagaController
import br.com.ronanjunior.linketinder.dto.VagaListaDoCandidatoDto
import br.com.ronanjunior.linketinder.model.Vaga
import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/vaga/*")
class VagaServelet extends HttpServlet {
    private final VagaController vagaController = new VagaController()
    private final MapperUtils mapperUtils = new MapperUtils()

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo()

        if (pathInfo) {
            String[] pathParts = pathInfo.split("/")

            JsonSlurper jsonSlurper = new JsonSlurper()
            Map jsonMap = jsonSlurper.parseText(request.reader.text) as Map

            Vaga vaga = mapperUtils.converterMapToObject(jsonMap, Vaga)

            if (pathParts.size() > 1) {
                String operacao = pathParts[1]

                switch (operacao) {
                    case "cadastrar":
                        Vaga cadastrada = vagaController.cadastrarVaga(vaga)

                        if (cadastrada) {
                            response.writer.println("Cadastro bem-sucedido!")
                        } else {
                            response.writer.println("Falha no cadastro. Verifique as informações da vaga.")
                        }
                        break

                    default:
                        response.writer.println("Operação inválida.")
                        break
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo()

        if (pathInfo) {
            String[] pathParts = pathInfo.split("/")

            if (pathParts.size() > 1) {
                String operacao = pathParts[1]

                switch (operacao) {
                    case "candidato":
                        Integer idCandidatoParam = request.getParameter("id") as Integer
                        List<VagaListaDoCandidatoDto> vagas = vagaController.listarTodasVagasParaCandidato(idCandidatoParam)

                        if (vagas) {
                            def jsonVagas = JsonOutput.toJson(vagas)

                            response.contentType = "application/json"

                            response.writer.write(jsonVagas)
                        } else {
                            response.writer.println("Falha na listagem de vagas. Verifique as informações do candidato.")
                        }

                        break
                    case "empresa":
                        Integer idVagaParam = request.getParameter("id") as Integer
                        List<Vaga> vagas = vagaController.listarTodasVagasParaEmpresa(idVagaParam)

                        if (vagas) {
                            def jsonVagas = JsonOutput.toJson(vagas)

                            response.contentType = "application/json"

                            response.writer.write(jsonVagas)
                        } else {
                            response.writer.println("Falha na listagem de vagas. Verifique as informações da empresa.")
                        }

                        break
                    default:
                        response.writer.println("Operação inválida.")
                        break
                }
            }
        }
    }
}
