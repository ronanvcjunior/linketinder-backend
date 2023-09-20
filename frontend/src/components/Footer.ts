class Footer {
  private container: HTMLElement;

  constructor(containerId: string) {
    const container: (HTMLElement|null) = document.getElementById(containerId);

    if (container) {
      this.container = container;
    } else {
      throw new Error(`Element with id "${containerId}" is not an HTMLElement.`);
    }

    this.render();
  }

  private render() {
    const footerElement = document.createElement('footer');
    footerElement.innerHTML = `
      <footer>
        <span>
          Criado por: 
          <a href="https://github.com/ronanvcjunior/" target="_blank">
            Ronan Vieira do Carmo Junior 
            <i class="fa-brands fa-github"></i>
          </a>
        </span>
      </footer>
    `;
    this.container.appendChild(footerElement);
  }
}

export default Footer;
