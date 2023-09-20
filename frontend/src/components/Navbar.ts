declare const __webpack_public_path__: string;

class Navbar {
  private container: HTMLElement;
  private isPaginaInicial: boolean;

  constructor(containerId: string, isPaginaInicial: boolean) {
    const container: (HTMLElement|null) = document.getElementById(containerId);

    if (container) {
      this.container = container;
    } else {
      throw new Error(`Element with id "${containerId}" is not an HTMLElement.`);
    }

    this.isPaginaInicial = isPaginaInicial;

    this.render();
    this.addMenuToggle();
  }

  private render(): void {
    const headerElement: HTMLElement = document.createElement('header');

    headerElement.innerHTML = `
      <div><a href="${__webpack_public_path__}" class="logo">Linketinder</a></div>
    `;
    if (this.isPaginaInicial) {
      headerElement.innerHTML += `
        <nav id="nav-header" class="">
            <ul>
                <li><a href="${__webpack_public_path__}cadastroEmpresa.html" class="item-menu">Nova Empresa</a></li>
                <li><a href="${__webpack_public_path__}cadastroCandidato.html" class="item-menu">Novo(a) Candidato(a)</a></li>
            </ul>
        </nav>
        <div id="menu-toggle"><i class="fa-solid fa-bars"></i></div>
    `;
    }
    this.container.appendChild(headerElement);
  }

  private addMenuToggle(): void {
    const menuToggle: (HTMLElement|null) = document.getElementById("menu-toggle");
    const navHeader: (HTMLElement|null) = document.getElementById("nav-header");

    if (menuToggle && navHeader)
      menuToggle.addEventListener("click", (): void => {
        navHeader.classList.toggle("menu-ativo");
      });
}
}

export default Navbar;
