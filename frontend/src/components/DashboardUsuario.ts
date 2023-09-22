import dashboardPefilCandidatoView from "../view/dashboardPerfilCandidatoView.html";
import dashboardVagasDisponiveisView from "../view/dashboardVagasDisponiveisView.html"
import CandidatoDomain from "../domain/CandidatoDomain";
import CompetenciaDomain from "../domain/CompetenciaDomain";
import VagaDomain from "../domain/VagaDomain";

class DashboardUsuario {
    private container: HTMLElement;

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
    }

    private render(): void {
        const dashbaord: HTMLElement = document.createElement("div");
        dashbaord.id = "dashboard";

        const dashboardPefil: HTMLElement = document.createElement("div");
        dashboardPefil.setAttribute("id", "dashboard-perfil");
        dashboardPefil.setAttribute("class", "dashboard-card");
        dashboardPefil.innerHTML = dashboardPefilCandidatoView;

        const dashboardVagasDiponiveis: HTMLElement = document.createElement("div");
        dashboardVagasDiponiveis.setAttribute("id", "dashboard-vagas-disponiveis");
        dashboardVagasDiponiveis.setAttribute("class", "dashboard-card");
        dashboardVagasDiponiveis.innerHTML = dashboardVagasDisponiveisView;

        dashbaord.appendChild(dashboardPefil);
        dashbaord.appendChild(dashboardVagasDiponiveis);

        this.container.appendChild(dashbaord);
    }

    private montarInformacoes(): void {
        const localStorageLogin: (string|null) = localStorage.getItem('login');
        const localStorageCandidatos: (string|null) = localStorage.getItem('candidatos');

        if (localStorageLogin && localStorageCandidatos) {
            const parsedLogin = JSON.parse(localStorageLogin);
            const loginId = parsedLogin.id;

            const parsedCandidatos: CandidatoDomain[] = JSON.parse(localStorageCandidatos);
            const candidatos: CandidatoDomain[] = Object.values(parsedCandidatos);

            const candidatoLogado: (CandidatoDomain|undefined) = candidatos.find(
                (candidato: CandidatoDomain): void => candidato.id = loginId
            );

            if (candidatoLogado) {
                const nome: Element = document.getElementsByClassName("input-nome")[0];
                const email: Element = document.getElementsByClassName("input-email")[0];
                const cpf: Element = document.getElementsByClassName("input-cpf")[0];
                const pais: Element = document.getElementsByClassName("input-pais")[0];
                const estado: Element = document.getElementsByClassName("input-estado")[0];
                const cep: Element = document.getElementsByClassName("input-cep")[0];
                const descricao: Element = document.getElementsByClassName("input-descricao")[0];

                nome.innerHTML = candidatoLogado.nome;
                email.innerHTML = candidatoLogado.email;
                cpf.innerHTML = candidatoLogado.cpf;
                pais.innerHTML = candidatoLogado.pais;
                estado.innerHTML = candidatoLogado.estado;
                cep.innerHTML = candidatoLogado.cep;
                descricao.innerHTML = candidatoLogado.descricao;

                this.montarCompetencias(candidatoLogado.competenciasId);
            }
        }
    }

    private montarCompetencias(competenciasId: number[]): void {
        const localStorageCompetencias: (string|null) = localStorage.getItem('competencias');

        if (localStorageCompetencias) {
            const parsedCompetencias: CompetenciaDomain = JSON.parse(localStorageCompetencias);
            const competencias: CompetenciaDomain[] = Object.values(parsedCompetencias);
            console.log(competencias);

            competenciasId.forEach((competenciaID) => {
                const inputCompetencias = document.getElementsByClassName("input-competencias")[0];

                const elementCompetencia = document.createElement("span");
                elementCompetencia.setAttribute("class", "input-competencia");

                const competencia = competencias.find(
                    (element) => element.id == competenciaID
                );

                if (competencia) {
                    elementCompetencia.innerText = competencia.nome;
    
                    inputCompetencias.appendChild(elementCompetencia);
                }
            });
        }
    }

    private montarVagasDiponiveis() {
        const localStorageVagas: (string|null) = localStorage.getItem('vagas');

        if (localStorageVagas) {
            const tabelaVagas = document.getElementById("tabela-vagas");

            const parsedVagas: VagaDomain = JSON.parse(localStorageVagas);
            const vagas: VagaDomain[] = Object.values(parsedVagas);
            console.log(vagas);

            vagas.forEach((vaga: VagaDomain): void => {
                const tabelaVaga = document.createElement("tr");
                tabelaVaga.innerHTML = `
                    <td data-label="Nome">${vaga.nome}</td>
                    <td data-label="Competências Desejadas">
                        <div id="tabela-competencias${vaga.id}" class="table-competencias"></div>
                    </td>
                    <td data-label="Opção">
                        <div class="btns">
                            <a href="#" class="btn btn-curtir">Curtir</a>
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
}

export default DashboardUsuario;
