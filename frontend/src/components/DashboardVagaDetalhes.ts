import { Chart } from 'chart.js/auto';
import VagaDomain from "../domain/VagaDomain";
import CandidatoDomain from "../domain/CandidatoDomain";

declare const __webpack_public_path__: string;

class DashboardVagaDetalhes {
    private container: HTMLElement;

    constructor(containerId: string) {
        const container: (HTMLElement|null) = document.getElementById(containerId);

        if (container) {
            console.log(containerId)
            this.container = container;
        } else {
            throw new Error(`Element with id "${containerId}" is not an HTMLElement.`);
        }

        this.render();
        this.montarChart();
    }

    private render(): void {
        const dashbaord: HTMLElement = document.createElement("div");
        dashbaord.id = "dashboard";

        const dashboardVaga: HTMLElement = document.createElement("div");
        dashboardVaga.setAttribute("id", "dashboard-vaga");
        dashboardVaga.setAttribute("class", "dashboard-card");

        dashboardVaga.innerHTML = `
            <h1 id="titulo-vaga" class="titulo-vaga">Vaga para desenvolvimento</h1>
            
            <div id="chart-vaga"></div>
            
            <div class="cadastro-btns">
                <a href="${__webpack_public_path__}perfilEmpresa.html" class="cadastro-btn btn-voltar">Voltar</a>
            </div>
        `;

        dashbaord.appendChild(dashboardVaga);

        this.container.appendChild(dashbaord);

        const btnVoltar = document.getElementsByClassName("btn-voltar")[0];
        btnVoltar.setAttribute("href", `${__webpack_public_path__}perfilEmpresa.html`);
    }

    private montarChart(): void {
        const localStorageVagas: (string|null) = localStorage.getItem('vagas');

        if (localStorageVagas) {
            const url = new URL(window.location.href);
            const vagaUrl = parseInt(<string>url.searchParams.get("vaga"));

            const parsedVagas: VagaDomain = JSON.parse(localStorageVagas);
            const vagas: VagaDomain[] = Object.values(parsedVagas);
            const vaga = vagas.find((vaga) => vaga.id == vagaUrl);
            console.log(vagas)
            if (vaga) {
                const tituloVaga = document.getElementById("titulo-vaga");
                if (tituloVaga)
                    tituloVaga.innerText = vaga.nome;
                const localStorageCompetencias: (string|null) = localStorage.getItem('competencias');

                const localStorageCandidatos: (string|null) = localStorage.getItem('candidatos');

                if (localStorageCompetencias && localStorageCandidatos) {
                    const parsedCompetencias: VagaDomain = JSON.parse(localStorageCompetencias);
                    const competencias: VagaDomain[] = Object.values(parsedCompetencias);

                    const parsedCandidatos: CandidatoDomain = JSON.parse(localStorageCandidatos);
                    const candidatos: CandidatoDomain[] = Object.values(parsedCandidatos);

                    let competenciasVaga: string[] = [];
                    let candidatosPorCompetenciaVaga: number[] = [];
                    vaga.competenciasId.forEach((competenciaID) => {
                        const competencia = competencias.find((elemento) => elemento.id == competenciaID);

                        competencia && competenciasVaga.push(competencia.nome);

                        let candidatosPorCompetencia: number = 0;

                        candidatos.forEach((candidato) => {
                            if (candidato.competenciasId.includes(competenciaID))
                                candidatosPorCompetencia++;
                        })

                        candidatosPorCompetenciaVaga.push(candidatosPorCompetencia);
                    });

                    const chartVaga: (HTMLElement|null) = document.getElementById("chart-vaga");

                    const chartCanvas: HTMLCanvasElement = document.createElement("canvas");
                    chartCanvas.setAttribute("id", "chart-canvas");
                    chartCanvas.classList.add('custom-y-axis');

                    new Chart(chartCanvas, {
                        type: 'bar',
                        data: {
                            labels: competenciasVaga,
                            datasets: [
                                {
                                    data: candidatosPorCompetenciaVaga,
                                    backgroundColor: [
                                        'rgba(255, 99, 132, 1)',
                                        'rgba(54, 162, 235, 1)',
                                        'rgba(255, 206, 86, 1)',
                                        'rgba(153, 102, 255, 1)',
                                        'rgba(255, 159, 64, 1)',
                                    ]
                                },
                            ],
                        },
                        options: {
                            plugins: {
                                legend: {
                                    display: false
                                }
                            },
                            scales: {
                                y: {
                                    beginAtZero: true
                                }
                            }
                        }
                    });

                    chartVaga && chartVaga.appendChild(chartCanvas);
                }

            }
        }
    }
}

export default DashboardVagaDetalhes;
