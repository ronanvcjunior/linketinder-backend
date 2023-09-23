import "select2/dist/css/select2.min.css";
import EmpresaDomain from "../domain/EmpresaDomain";
import LoginDomain from "../domain/LoginDomain";

declare const __webpack_public_path__: string;

class Cadastro {
  private container: HTMLElement;
  private cadastroView: string;
  private tipoCadastro: string;
  private empresas: EmpresaDomain[] = [];

  constructor(containerId: string, cadastroView: string, tipoCadastro: string) {
    const container: (HTMLElement|null) = document.getElementById(containerId);

    if (container) {
      this.container = container;
    } else {
      throw new Error(`Element with id "${containerId}" is not an HTMLElement.`);
    }

    this.cadastroView = cadastroView;

    this.tipoCadastro = tipoCadastro;

    this.empresas = this.montarListaEmpresa();

    this.render();
    this.montarSelect2();

    if(this.tipoCadastro == "empresa")
      this.cadastrarEmpresa();
  }

  private montarListaEmpresa(): EmpresaDomain[] {
    const localStorageEmpresas: (string|null) = localStorage.getItem('empresas');

    if (localStorageEmpresas) {
      const parsedEmpresas: EmpresaDomain[] = JSON.parse(localStorageEmpresas);
      const empresas: EmpresaDomain[] = Object.values(parsedEmpresas);

      return empresas;
    }
      return [];
  }

  private async montarSelect2(): Promise<void> {
    const listSelect2 = document.getElementsByClassName("select-select2");
    const select2Array = Array.from(listSelect2);

    await import("select2");

    select2Array.forEach((element): void => {
      $((): void => {
        $(`#${element.id}`).select2();
      })
    })
  }
  private render(): void {
    this.container.innerHTML = this.cadastroView;
  }

  private cadastrarEmpresa() {
    const formulario = document.getElementById("formulario");

    if (formulario) {
      formulario.addEventListener("submit", (event) => {
        event.preventDefault();

        console.log(this.empresas)

        const id: number = this.empresas[this.empresas.length - 1].id + 1;

        const nomeInput = document.getElementById("nomeInput") as HTMLInputElement;
        const nome: string = nomeInput.value;

        const emailInput = document.getElementById("emailInput") as HTMLInputElement;
        const email: string = emailInput.value;

        const cnpjInput = document.getElementById("cnpjInput") as HTMLInputElement;
        const cnpj: string = cnpjInput.value;

        const paisInput = document.getElementById("paisInput") as HTMLInputElement;
        const pais: string = paisInput.value;

        const estadoInput = document.getElementById("estadoInput") as HTMLInputElement;
        const estado: string = estadoInput.value;

        const cepInput = document.getElementById("cepInput") as HTMLInputElement;
        const cep: string = cepInput.value;

        const descricaoInput = document.getElementById("descricaoTextArea") as HTMLTextAreaElement;
        let descricao = descricaoInput.value;

        let empresa: EmpresaDomain = {
          id: id,
          nome: nome,
          email: email,
          cnpj: cnpj,
          pais: pais,
          estado: estado,
          cep: cep,
          descricao: descricao
        };

        this.empresas.push(empresa);
        localStorage.setItem('empresas', JSON.stringify(this.empresas));

        let login: LoginDomain = {
          id: id,
          email: email,
          tipo: "empresa",
        };

        localStorage.setItem('login', JSON.stringify(login));

        window.location.href = `${__webpack_public_path__}perfilEmpresa.html`;
      });
    }
  }
}

export default Cadastro;
