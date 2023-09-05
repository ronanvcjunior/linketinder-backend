package main.br.com.ronanjunior.linketinder

import main.br.com.ronanjunior.linketinder.controller.CandidatoController
import main.br.com.ronanjunior.linketinder.controller.CompetenciaController
import main.br.com.ronanjunior.linketinder.controller.EmpresaController
import main.br.com.ronanjunior.linketinder.controller.TerminalInterativoController
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.view.CandidatoView
import main.br.com.ronanjunior.linketinder.view.EmpresaView

class App {
    static TerminalInterativoController terminalInterativoController = new TerminalInterativoController();
    static CandidatoController candidatoController = new CandidatoController();
    static CandidatoView candidatoView = new CandidatoView();
    static EmpresaController empresaController = new EmpresaController();
    static EmpresaView empresaView = new EmpresaView();
    static CompetenciaController competenciaController = new CompetenciaController();

    static void main(String[] args) {
        List<Competencia> competencias = [
                new Competencia(nome:  "Groovy"),
                new Competencia(nome:  "Java"),
                new Competencia(nome:  "JavaScript"),
                new Competencia(nome:  "TypeScript")
        ];

        List<Candidato> candidatos = [
                new Candidato(
                        nome: "Alice",
                        email: "alice@example.com",
                        cpf: "11111111111",
                        idade: 25,
                        pais: "Brasil",
                        estado: "São Paulo",
                        cep: "12345-678",
                        competencias: [
                                competencias[0], // Groovy
                                competencias[1]  // Java
                        ],
                        descricao: "Desenvolvedor Full Stack"
                ),
                new Candidato(
                        nome: "Bob",
                        email: "bob@example.com",
                        cpf: "22222222222",
                        idade: 28,
                        pais: "Brasil",
                        estado: "Rio de Janeiro",
                        cep: "98765-432",
                        competencias: [
                                competencias[2], // JavaScript
                                competencias[3]  // TypeScript
                        ],
                        descricao: "Engenheiro de Software"
                ),
                new Candidato(
                        nome: "Carlos",
                        email: "carlos@example.com",
                        cpf: "33333333333",
                        idade: 30,
                        pais: "Brasil",
                        estado: "Minas Gerais",
                        cep: "54321-987",
                        competencias: [
                                competencias[0], // Groovy
                                competencias[2]  // JavaScript
                        ],
                        descricao: "Analista de Dados"
                ),
                new Candidato(
                        nome: "Daniela",
                        email: "daniela@example.com",
                        cpf: "44444444444",
                        idade: 22,
                        pais: "Brasil",
                        estado: "Bahia",
                        cep: "13579-246",
                        competencias: [
                                competencias[1], // Java
                                competencias[3]  // TypeScript
                        ],
                        descricao: "Desenvolvedor Frontend"
                ),
                new Candidato(
                        nome: "Eduardo",
                        email: "eduardo@example.com",
                        cpf: "55555555555",
                        idade: 27,
                        pais: "Brasil",
                        estado: "Rio Grande do Sul",
                        cep: "54321-543",
                        competencias: [
                                competencias[0], // Groovy
                                competencias[1], // Java
                                competencias[2]  // JavaScript
                        ],
                        descricao: "Arquiteto de Software"
                )
        ];

        List<Empresa> empresas = [
                new Empresa(
                        nome: "Empresa A",
                        email: "empresaA@example.com",
                        cnpj: "12345678901234",
                        pais: "Brasil",
                        estado: "Minas Gerais",
                        cep: "54321-987",
                        competenciasEsperadas: [
                                competencias[0], // Groovy
                                competencias[1]  // Java
                        ],
                        descricao: "Startup de Tecnologia"
                ),
                new Empresa(
                        nome: "Empresa B",
                        email: "empresaB@example.com",
                        cnpj: "56789012345678",
                        pais: "Brasil",
                        estado: "Bahia",
                        cep: "13579-246",
                        competenciasEsperadas: [
                                competencias[2], // JavaScript
                                competencias[3]  // TypeScript
                        ],
                        descricao: "Consultoria em TI"
                ),
                new Empresa(
                        nome: "Empresa C",
                        email: "empresaC@example.com",
                        cnpj: "98765432109876",
                        pais: "Brasil",
                        estado: "São Paulo",
                        cep: "54321-876",
                        competenciasEsperadas: [
                                competencias[0], // Groovy
                                competencias[2]  // JavaScript
                        ],
                        descricao: "Desenvolvimento Web"
                ),
                new Empresa(
                        nome: "Empresa D",
                        email: "empresaD@example.com",
                        cnpj: "11223344556677",
                        pais: "Brasil",
                        estado: "Rio de Janeiro",
                        cep: "98765-123",
                        competenciasEsperadas: [
                                competencias[1], // Java
                                competencias[3]  // TypeScript
                        ],
                        descricao: "E-commerce"
                ),
                new Empresa(
                        nome: "Empresa E",
                        email: "empresaE@example.com",
                        cnpj: "55555555555555",
                        pais: "Brasil",
                        estado: "Rio Grande do Sul",
                        cep: "54321-543",
                        competenciasEsperadas: [
                                competencias[0], // Groovy
                                competencias[1], // Java
                                competencias[2]  // JavaScript
                        ],
                        descricao: "Consultoria em Desenvolvimento"
                )
        ];

        competenciaController.competencias = competencias;
        candidatoController.candidatos = candidatos;
        candidatoView.candidatos = candidatos;
        candidatoView.competenciasCadastradas = competencias;
        empresaController.empresas = empresas;
        empresaView.empresas = empresas;
        empresaView.competenciasCadastradas = competencias;

        terminalInterativoController.candidatoController = candidatoController;
        terminalInterativoController.candidatoView = candidatoView;
        terminalInterativoController.empresaController = empresaController;
        terminalInterativoController.empresaView = empresaView;
        terminalInterativoController.iniciar();
    }
}
