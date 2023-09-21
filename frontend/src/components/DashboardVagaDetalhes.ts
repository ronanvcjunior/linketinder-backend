import { Chart } from 'chart.js/auto';


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
            <h1 class="titulo-vaga">Vaga para desenvolvimento</h1>
            <div id="chart-vaga"></div>
        `;

        dashbaord.appendChild(dashboardVaga);

        this.container.appendChild(dashbaord);
    }

    private montarChart(): void {
        const chartVaga: (HTMLElement|null) = document.getElementById("chart-vaga");

        const chartCanvas: HTMLCanvasElement = document.createElement("canvas");
        chartCanvas.setAttribute("id", "chart-canvas");

        new Chart(chartCanvas, {
            type: 'bar',
            data: {
                labels: ['Red', 'Blue'],
                datasets: [
                    {
                        label: 'Competência',
                        data: [12, 19, 3, 5, 2, 3],
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                        ],
                        borderColor: [
                            'rgba(255, 99, 132, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                        ],
                        borderWidth: 5,
                    },
                ],
            }
        });

        chartVaga && chartVaga.appendChild(chartCanvas);
    }
}

export default DashboardVagaDetalhes;
