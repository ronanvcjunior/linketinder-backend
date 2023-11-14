package br.com.ronanjunior.linketinder.servelet

import br.com.ronanjunior.linketinder.controller.CandidatoController
import br.com.ronanjunior.linketinder.dto.CandidatoListaDaEmpresaDto
import groovy.json.JsonOutput

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/candidato/*")
class CandidatoServelet extends HttpServlet {
    private final CandidatoController candidatoController = new CandidatoController()

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo()

        if (pathInfo) {
            String[] pathParts = pathInfo.split("/")

            if (pathParts.size() > 1) {
                String operacao = pathParts[1]

                switch (operacao) {
                    case "empresa":
                        Integer idVagaParam = request.getParameter("id") as Integer
                        List<CandidatoListaDaEmpresaDto> candidatos = candidatoController.listarCandidatosParaEmpresa(idVagaParam)

                        if (candidatos) {
                            def jsonVagas = JsonOutput.toJson(candidatos)

                            response.contentType = "application/json"

                            response.writer.write(jsonVagas)
                        } else {
                            response.writer.println("Falha na listagem de candidatos. Verifique as informações da empresa.")
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


