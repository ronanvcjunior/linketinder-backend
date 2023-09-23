import dashboardPerfilEmpresaView from "../view/dashboardPerfilEmpresaView.html";
import dashboardVagasCadastradasPorEmpresaView from "../view/dashboardVagasCadastradasPorEmpresaView.html";
import dashboardCandidatosCadastradosView from "../view/dashboardCandidatosCadastradosView.html";
import EmpresaDomain from "../domain/EmpresaDomain";
import VagaDomain from "../domain/VagaDomain";
import CompetenciaDomain from "../domain/CompetenciaDomain";
import CandidatoDomain from "../domain/CandidatoDomain";

declare const __webpack_public_path__: string;

class DashboardEmpresa {
    private container: HTMLElement;
    private empresaId: number = 0;

    constructor(containerId: string) {
        const container: (HTMLElement|null) = document.getElementById(containerId);

        if (container) {
            this.container = container;
        } else {
            throw new Error(`Element with id "${containerId}" is not an HTMLElement.`);
        }

        this.render();
        this.montarInformacoes();
        this.montarVagasDiponiveis();
        this.montarCandidatosCadastrados();
    }

    private render(): void {
        const dashbaord: HTMLElement = document.createElement("div");
        dashbaord.id = "dashboard";

        const dashboardPefil: HTMLElement = document.createElement("div");
        dashboardPefil.setAttribute("id", "dashboard-perfil");
        dashboardPefil.setAttribute("class", "dashboard-card");
        dashboardPefil.innerHTML = dashboardPerfilEmpresaView;

        const dashboardVagasDiponiveis: HTMLElement = document.createElement("div");
        dashboardVagasDiponiveis.setAttribute("id", "dashboard-vagas-disponiveis");
        dashboardVagasDiponiveis.setAttribute("class", "dashboard-card");
        dashboardVagasDiponiveis.innerHTML = dashboardVagasCadastradasPorEmpresaView;

        const dashboardCandidatosCadastrados: HTMLElement = document.createElement("div");
        dashboardCandidatosCadastrados.setAttribute("id", "dashboard-candidatos-cadastrados");
        dashboardCandidatosCadastrados.setAttribute("class", "dashboard-card");
        dashboardCandidatosCadastrados.innerHTML = dashboardCandidatosCadastradosView;

        dashbaord.appendChild(dashboardPefil);
        dashbaord.appendChild(dashboardVagasDiponiveis);
        dashbaord.appendChild(dashboardCandidatosCadastrados);

        this.container.appendChild(dashbaord);

        const adicionarVaga = document.getElementsByClassName("tabela-adicionar")[0];
        adicionarVaga.setAttribute("href", `${__webpack_public_path__}cadastroVaga.html`);
    }

    private montarInformacoes(): void {
        const localStorageLogin: (string|null) = localStorage.getItem('login');
        const localStorageEmpresas: (string|null) = localStorage.getItem('empresas');

        if (localStorageLogin && localStorageEmpresas) {
            const parsedLogin = JSON.parse(localStorageLogin);
            console.log(parsedLogin)
            console.log(parsedLogin.id)
            const loginId = parsedLogin.id;
            this.empresaId = parsedLogin.id;

            const parsedEmpresas: EmpresaDomain[] = JSON.parse(localStorageEmpresas);
            const empresas: EmpresaDomain[] = Object.values(parsedEmpresas);

            const empresaLogado: (EmpresaDomain|undefined) = empresas.find(
                (empresa: EmpresaDomain): boolean => empresa.id == loginId
            );

            if (empresaLogado) {
                const nome: Element = document.getElementsByClassName("input-nome")[0];
                const email: Element = document.getElementsByClassName("input-email")[0];
                const cnpj: Element = document.getElementsByClassName("input-cnpj")[0];
                const pais: Element = document.getElementsByClassName("input-pais")[0];
                const estado: Element = document.getElementsByClassName("input-estado")[0];
                const cep: Element = document.getElementsByClassName("input-cep")[0];
                const descricao: Element = document.getElementsByClassName("input-descricao")[0];

                nome.innerHTML = empresaLogado.nome;
                email.innerHTML = empresaLogado.email;
                cnpj.innerHTML = empresaLogado.cnpj;
                pais.innerHTML = empresaLogado.pais;
                estado.innerHTML = empresaLogado.estado;
                cep.innerHTML = empresaLogado.cep;
                descricao.innerHTML = empresaLogado.descricao;
            }
        }
    }

    private montarVagasDiponiveis() {
        const localStorageVagas: (string|null) = localStorage.getItem('vagas');

        if (localStorageVagas) {
            const tabelaVagas = document.getElementById("tabela-vagas");

            const parsedVagas: VagaDomain = JSON.parse(localStorageVagas);
            const vagas: VagaDomain[] = Object.values(parsedVagas);
            vagas
                .filter((vaga) => vaga.empresaID == this.empresaId)
                .forEach((vaga: VagaDomain): void => {
                    const tabelaVaga = document.createElement("tr");
                    tabelaVaga.innerHTML = `
                        <td data-label="Nome">${vaga.nome}</td>
                        <td data-label="Competências Desejadas">
                            <div id="tabela-competencias${vaga.id}" class="table-competencias"></div>
                        </td>
                        <td data-label="Opção">
                            <div class="btns">
                                <a href="${__webpack_public_path__}vagaDetalhes.html?vaga=${vaga.id}" class="btn btn-curtir">Visualizar</a>
                            </div>
                        </td>
                    `;
                    tabelaVagas && tabelaVagas.appendChild(tabelaVaga);

                    vaga.competenciasId.forEach((competenciaID: number): void => {
                        this.montarCompetenciaVaga(competenciaID, vaga.id);
                    })
                });
        }
    }

    private montarCompetenciaVaga(competenciaId: number, vagaId: number): void {
        const localStorageCompetencias: (string|null) = localStorage.getItem('competencias');

        if (localStorageCompetencias) {
            const parsedCompetencias: CompetenciaDomain = JSON.parse(localStorageCompetencias);
            const competencias: CompetenciaDomain[] = Object.values(parsedCompetencias);

            const tabelaCompetencias = document.getElementById(`tabela-competencias${vagaId}`);

            const tabelaCompetencia = document.createElement("span");
            tabelaCompetencia.setAttribute("class", "table-competencia");

            const competencia = competencias.find(
                (element) => element.id == competenciaId
            );


            if (competencia) {
                tabelaCompetencia.innerText = competencia.nome;

                tabelaCompetencias && tabelaCompetencias.appendChild(tabelaCompetencia);
            }
        }
    }

    private montarCandidatosCadastrados() {
        const localStorageCandidatos: (string|null) = localStorage.getItem('candidatos');

        if (localStorageCandidatos) {
            const tabelaCandidatos = document.getElementById("tabela-candidatos");

            const parsedCandidatos: CandidatoDomain = JSON.parse(localStorageCandidatos);
            const candidatos: CandidatoDomain[] = Object.values(parsedCandidatos);
            console.log(candidatos);

            candidatos.forEach((candidato: CandidatoDomain): void => {
                const tabelaCandidato = document.createElement("tr");
                tabelaCandidato.innerHTML = `
                    <td data-label="Nome">Anônimo</td>
                    <td data-label="Competências">
                        <div id="tabela-competencias-candidato${candidato.id}" class="table-competencias"></div>
                    </td>
                `;
                tabelaCandidatos && tabelaCandidatos.appendChild(tabelaCandidato);

                candidato.competenciasId.forEach((competenciaID: number): void => {
                    this.montarCompetenciaCandidato(competenciaID, candidato.id);
                })
            });
        }
    }

    private montarCompetenciaCandidato(competenciaId: number, candidatoId: number): void {
        console.log(candidatoId + " - " + competenciaId)
        const localStorageCompetencias: (string|null) = localStorage.getItem('competencias');

        if (localStorageCompetencias) {
            const parsedCompetencias: CompetenciaDomain = JSON.parse(localStorageCompetencias);
            const competencias: CompetenciaDomain[] = Object.values(parsedCompetencias);

            const tabelaCompetencias = document.getElementById(`tabela-competencias-candidato${candidatoId}`);

            const tabelaCompetencia = document.createElement("span");
            tabelaCompetencia.setAttribute("class", "table-competencia");

            const competencia = competencias.find(
                (element) => element.id == competenciaId
            );


            if (competencia) {
                tabelaCompetencia.innerText = competencia.nome;

                tabelaCompetencias && tabelaCompetencias.appendChild(tabelaCompetencia);
            }
        }
    }
}

export default DashboardEmpresa;
