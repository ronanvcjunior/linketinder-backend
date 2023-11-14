package br.com.ronanjunior.linketinder.servelet

import br.com.ronanjunior.linketinder.controller.AutenticacaoController
import br.com.ronanjunior.linketinder.model.Conta
import br.com.ronanjunior.linketinder.utils.MapperUtils
import groovy.json.JsonSlurper

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/autenticacao/*")
class AutenticacaoServlet extends HttpServlet {
    private final AutenticacaoController autenticacaoController = new AutenticacaoController()
    private final MapperUtils mapperUtils = new MapperUtils()

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo()

        if (pathInfo) {
            String[] pathParts = pathInfo.split("/")

            JsonSlurper jsonSlurper = new JsonSlurper()
            Map jsonMap = jsonSlurper.parseText(request.reader.text) as Map

            Conta conta = mapperUtils.converterMapToObject(jsonMap, Conta)

            if (pathParts.size() > 1) {
                String operacao = pathParts[1]

                switch (operacao) {
                    case "login":
                        Conta login = autenticacaoController.fazerLogin(conta.email, conta.senha)

                        if (login.id) {
                            if (login.candidato)
                                response.writer.println("Login bem-sucedido! Bem-vindo, ${login.candidato.nome} ${login.candidato.sobrenome}")
                            if (login.empresa)
                                response.writer.println("Login bem-sucedido! Bem-vindo, ${login.empresa.nome}")
                        } else {
                            response.writer.println("Falha no login. Verifique suas credenciais.")
                        }
                        break
                    case "cadastrar":
                        Conta cadastrado = autenticacaoController.cadastrarUsuario(conta)

                        if (cadastrado) {
                            response.writer.println("Cadastro bem-sucedido!")
                        } else {
                            response.writer.println("Falha no cadastro. Verifique as informações do usuário.")
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
