import "select2/dist/css/select2.min.css";
import EmpresaDomain from "../domain/EmpresaDomain";
import LoginDomain from "../domain/LoginDomain";
import CompetenciaDomain from "../domain/CompetenciaDomain";
import candidatoDomain from "../domain/CandidatoDomain";
import CandidatoDomain from "../domain/CandidatoDomain";
import VagaDomain from "../domain/VagaDomain";

declare const __webpack_public_path__: string;

class Cadastro {
  private container: HTMLElement;
  private cadastroView: string;
  private tipoCadastro: string;
  private empresas: EmpresaDomain[] = [];
  private candidatos: candidatoDomain[] = [];
  private vagas: VagaDomain[] = [];

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
    this.candidatos = this.montarListaCandidato();
    this.vagas = this.montarListaVaga();

    this.render();
    this.montarSelect2();
    this.montaSelectCompetencias();

    if (this.tipoCadastro == "empresa")
      this.cadastrarEmpresa();
    else if (this.tipoCadastro == "candidato")
      this.cadastrarCandidato();
    else if (this.tipoCadastro == "vaga")
      this.cadastrarVaga();
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

  private montarListaCandidato(): CandidatoDomain[] {
    const localStorageCandidatos: (string|null) = localStorage.getItem('candidatos');

    if (localStorageCandidatos) {
      const parsedCandidatos: CandidatoDomain[] = JSON.parse(localStorageCandidatos);
      const candidatos: CandidatoDomain[] = Object.values(parsedCandidatos);

      return candidatos;
    }
    return [];
  }


  private montarListaVaga(): VagaDomain[] {
    const localStorageVagas: (string|null) = localStorage.getItem('vagas');

    if (localStorageVagas) {
      const parsedVagas: VagaDomain[] = JSON.parse(localStorageVagas);
      const vagas: VagaDomain[] = Object.values(parsedVagas);

      return vagas;
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

  private montaSelectCompetencias() {
    const competenciasSelect = document.getElementById("competenciasSelect");

    if (competenciasSelect) {
      const localStorageCompetencias: (string|null) = localStorage.getItem('competencias');

      if (localStorageCompetencias) {
        const parsedCompetencias: CompetenciaDomain = JSON.parse(localStorageCompetencias);
        const competencias: CompetenciaDomain[] = Object.values(parsedCompetencias);

        competencias.forEach((competencia) => {
          const optionCompetencia = document.createElement("option");
          optionCompetencia.setAttribute("value", `${competencia.id}`);
          optionCompetencia.innerText = competencia.nome;

          competenciasSelect.appendChild(optionCompetencia);
        });
      }
    }
  }

  private render(): void {
    this.container.innerHTML = this.cadastroView;
  }

  private cadastrarEmpresa() {
    const formulario = document.getElementById("formulario");

    if (formulario) {
      formulario.addEventListener("submit", (event) => {
        event.preventDefault();

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

  private cadastrarCandidato() {
    const formulario = document.getElementById("formulario");

    if (formulario) {
      formulario.addEventListener("submit", (event) => {
        event.preventDefault();

        const id: number = this.candidatos[this.candidatos.length - 1].id + 1;

        const nomeInput = document.getElementById("nomeInput") as HTMLInputElement;
        const nome: string = nomeInput.value;

        const emailInput = document.getElementById("emailInput") as HTMLInputElement;
        const email: string = emailInput.value;

        const cpfInput = document.getElementById("cpfInput") as HTMLInputElement;
        const cpf: string = cpfInput.value;

        const paisInput = document.getElementById("paisInput") as HTMLInputElement;
        const pais: string = paisInput.value;

        const estadoInput = document.getElementById("estadoInput") as HTMLInputElement;
        const estado: string = estadoInput.value;

        const cepInput = document.getElementById("cepInput") as HTMLInputElement;
        const cep: string = cepInput.value;

        const descricaoInput = document.getElementById("descricaoTextArea") as HTMLTextAreaElement;
        let descricao = descricaoInput.value;

        const competenciasSelect = document.getElementById("competenciasSelect") as HTMLSelectElement

        let competenciasId: number[] = [];
        for (let selectedOption of competenciasSelect.selectedOptions) {
          competenciasId.push(parseInt(selectedOption.value));
        }

        let candidato: CandidatoDomain = {
          id: id,
          nome: nome,
          email: email,
          cpf: cpf,
          pais: pais,
          estado: estado,
          cep: cep,
          descricao: descricao,
          competenciasId: competenciasId
        };

        this.candidatos.push(candidato);
        localStorage.setItem('candidatos', JSON.stringify(this.candidatos));

        let login: LoginDomain = {
          id: id,
          email: email,
          tipo: "candidato",
        };

        localStorage.setItem('login', JSON.stringify(login));

        window.location.href = `${__webpack_public_path__}perfilCandidato.html`;
      });
    }
  }

  private cadastrarVaga() {
    const formulario = document.getElementById("formulario");

    if (formulario) {
      formulario.addEventListener("submit", (event) => {
        event.preventDefault();

        const id: number = this.vagas[this.vagas.length - 1].id + 1;

        const localStorageLogin: (string|null) = localStorage.getItem('login');

        let empresaID: number = 0;

        if (localStorageLogin) {
          const parsedLogin = JSON.parse(localStorageLogin);
          empresaID = parsedLogin.id;
        }

        const nomeInput = document.getElementById("nomeInput") as HTMLInputElement;
        const nome: string = nomeInput.value;

        const competenciasSelect = document.getElementById("competenciasSelect") as HTMLSelectElement

        let competenciasId: number[] = [];
        for (let selectedOption of competenciasSelect.selectedOptions) {
          competenciasId.push(parseInt(selectedOption.value));
        }

        let vaga: VagaDomain = {
          id: id,
          nome: nome,
          empresaID: empresaID,
          competenciasId: competenciasId
        };

        this.vagas.push(vaga);
        localStorage.setItem('vagas', JSON.stringify(this.vagas));

        window.location.href = `${__webpack_public_path__}vagaDetalhes.html?vaga=${id}.html`;
      });
    }
  }
}

export default Cadastro;
