import dashboardPerfilEmpresaView from "../view/dashboardPerfilEmpresaView.html";
import dashboardVagasCadastradasPorEmpresaView from "../view/dashboardVagasCadastradasPorEmpresaView.html"

class DashboardEmpresa {
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

        dashbaord.appendChild(dashboardPefil);
        dashbaord.appendChild(dashboardVagasDiponiveis);

        this.container.appendChild(dashbaord);
    }
}

export default DashboardEmpresa;