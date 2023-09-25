import "select2/dist/css/select2.min.css";
import EmpresaDomain from "../domain/EmpresaDomain";
import LoginDomain from "../domain/LoginDomain";
import CompetenciaDomain from "../domain/CompetenciaDomain";
import candidatoDomain from "../domain/CandidatoDomain";
import CandidatoDomain from "../domain/CandidatoDomain";
import VagaDomain from "../domain/VagaDomain";
import loginDomain from "../domain/LoginDomain";

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
    else if (this.tipoCadastro == "login")
      this.login();
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

    const btnVoltar = document.getElementsByClassName("btn-voltar")[0];
    if (this.tipoCadastro === "vaga")
      btnVoltar.setAttribute("href", `${__webpack_public_path__}perfilEmpresa.html`);
    else
      btnVoltar.setAttribute("href", `${__webpack_public_path__}`);
  }

  private cadastrarEmpresa() {
    const formulario = document.getElementById("formulario");

    if (formulario) {
      formulario.addEventListener("submit", (event) => {
        event.preventDefault();

        const id: number = this.empresas[this.empresas.length - 1].id + 1;

        const nomeInput = document.getElementById("nomeInput") as HTMLInputElement;
        const nome: string = nomeInput.value.trim();

        const emailInput = document.getElementById("emailInput") as HTMLInputElement;
        const email: string = emailInput.value.trim();

        const cnpjInput = document.getElementById("cnpjInput") as HTMLInputElement;
        const cnpj: string = cnpjInput.value.trim();

        const paisInput = document.getElementById("paisInput") as HTMLInputElement;
        const pais: string = paisInput.value.trim();

        const estadoInput = document.getElementById("estadoInput") as HTMLInputElement;
        const estado: string = estadoInput.value.trim();

        const cepInput = document.getElementById("cepInput") as HTMLInputElement;
        const cep: string = cepInput.value.trim();

        const descricaoInput = document.getElementById("descricaoTextArea") as HTMLTextAreaElement;
        let descricao = descricaoInput.value.trim();

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

        if (this.validarCadastroEmpresa(empresa)) {
          this.empresas.push(empresa);
          localStorage.setItem('empresas', JSON.stringify(this.empresas));

          let login: LoginDomain = {
            id: id,
            email: email,
            tipo: "empresa",
          };

          localStorage.setItem('login', JSON.stringify(login));

          window.location.href = `${__webpack_public_path__}perfilEmpresa.html`;
        }
      });
    }
  }

  private validarCadastroEmpresa(empresa: EmpresaDomain): boolean {
    const nomeRegex: RegExp = /^[\p{L},\s]{1,255}$/u;
    const emailRegex: RegExp = /^\S+@\w+\.\w{2,6}(\.\w{2})?$/;
    const cnpjRegex: RegExp = /^\d{2}\.?\d{3}\.?\d{3}\/?\d{4}\-?\d{2}$/;
    const cepRegex: RegExp = /^\d{5}\-?\d{3}$/;

    const nomeError = document.getElementById("nomeError");
    const emailError = document.getElementById("emailError");
    const cnpjError = document.getElementById("cnpjError");
    const paisError = document.getElementById("paisError");
    const estadoError = document.getElementById("estadoError");
    const cepError = document.getElementById("cepError");

    let isValid = true;

    if (!nomeRegex.test(empresa.nome)) {
      if (nomeError) {
        nomeError.hidden = false;
      }
      isValid = false;
    } else {
      if (nomeError) {
        nomeError.hidden = true;
      }
    }

    if (!nomeRegex.test(empresa.pais)) {
      if (paisError) {
        paisError.hidden = false;
      }
      isValid = false;
    } else {
      if (paisError) {
        paisError.hidden = true;
      }
    }

    if (!nomeRegex.test(empresa.estado)) {
      if (estadoError) {
        estadoError.hidden = false;
      }
      isValid = false;
    } else {
      if (estadoError) {
        estadoError.hidden = true;
      }
    }

    if (!emailRegex.test(empresa.email)) {
      if (emailError) {
        emailError.hidden = false;
      }
      isValid = false;
    } else {
      if (emailError) {
        emailError.hidden = true;
      }
    }

    if (!cnpjRegex.test(empresa.cnpj)) {
      if (cnpjError) {
        cnpjError.hidden = false;
      }
      isValid = false;
    } else {
      if (cnpjError) {
        cnpjError.hidden = true;
      }
    }

    if (!cepRegex.test(empresa.cep)) {
      if (cepError) {
        cepError.hidden = false;
      }
      isValid = false;
    } else {
      if (cepError) {
        cepError.hidden = true;
      }
    }

    return isValid;
  }

  private cadastrarCandidato() {
    const formulario = document.getElementById("formulario");

    if (formulario) {
      formulario.addEventListener("submit", (event) => {
        event.preventDefault();

        const id: number = this.candidatos[this.candidatos.length - 1].id + 1;

        const nomeInput = document.getElementById("nomeInput") as HTMLInputElement;
        const nome: string = nomeInput.value.trim();

        const emailInput = document.getElementById("emailInput") as HTMLInputElement;
        const email: string = emailInput.value.trim();

        const cpfInput = document.getElementById("cpfInput") as HTMLInputElement;
        const cpf: string = cpfInput.value.trim();

        const paisInput = document.getElementById("paisInput") as HTMLInputElement;
        const pais: string = paisInput.value.trim();

        const estadoInput = document.getElementById("estadoInput") as HTMLInputElement;
        const estado: string = estadoInput.value.trim();

        const cepInput = document.getElementById("cepInput") as HTMLInputElement;
        const cep: string = cepInput.value.trim();

        const descricaoInput = document.getElementById("descricaoTextArea") as HTMLTextAreaElement;
        let descricao = descricaoInput.value.trim();

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

        if (this.validarCadastroCandidato(candidato)) {
          this.candidatos.push(candidato);
          localStorage.setItem('candidatos', JSON.stringify(this.candidatos));

          let login: LoginDomain = {
            id: id,
            email: email,
            tipo: "candidato",
          };

          localStorage.setItem('login', JSON.stringify(login));

          window.location.href = `${__webpack_public_path__}perfilCandidato.html`;
        }
      });
    }
  }

  private validarCadastroCandidato(candidato: CandidatoDomain): boolean {
    const nomeRegex: RegExp = /^[\p{L},\s]{1,255}$/u;
    const emailRegex: RegExp = /^\S+@\w+\.\w{2,6}(\.\w{2})?$/;
    const cpfRegex: RegExp = /^\d{3}\.?\d{3}\.?\d{3}\-?\d{2}$/;
    const cepRegex: RegExp = /^\d{5}\-?\d{3}$/;

    const nomeError = document.getElementById("nomeError");
    const emailError = document.getElementById("emailError");
    const cpfError = document.getElementById("cpfError");
    const paisError = document.getElementById("paisError");
    const estadoError = document.getElementById("estadoError");
    const cepError = document.getElementById("cepError");

    let isValid = true;

    if (!nomeRegex.test(candidato.nome)) {
      if (nomeError) {
        nomeError.hidden = false;
      }
      isValid = false;
    } else {
      if (nomeError) {
        nomeError.hidden = true;
      }
    }

    if (!emailRegex.test(candidato.email)) {
      if (emailError) {
        emailError.hidden = false;
      }
      isValid = false;
    } else {
      if (emailError) {
        emailError.hidden = true;
      }
    }

    if (!cpfRegex.test(candidato.cpf)) {
      if (cpfError) {
        cpfError.hidden = false;
      }
      isValid = false;
    } else {
      if (cpfError) {
        cpfError.hidden = true;
      }
    }

    if (!nomeRegex.test(candidato.pais)) {
      if (paisError) {
        paisError.hidden = false;
      }
      isValid = false;
    } else {
      if (paisError) {
        paisError.hidden = true;
      }
    }

    if (!nomeRegex.test(candidato.estado)) {
      if (estadoError) {
        estadoError.hidden = false;
      }
      isValid = false;
    } else {
      if (estadoError) {
        estadoError.hidden = true;
      }
    }

    if (!cepRegex.test(candidato.cep)) {
      if (cepError) {
        cepError.hidden = false;
      }
      isValid = false;
    } else {
      if (cepError) {
        cepError.hidden = true;
      }
    }

    return isValid;
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

        if (this.validarCadastroVaga(vaga)) {
          this.vagas.push(vaga);
          localStorage.setItem('vagas', JSON.stringify(this.vagas));

          window.location.href = `${__webpack_public_path__}vagaDetalhes.html?vaga=${id}.html`;
        }
      });
    }
  }

  private validarCadastroVaga(vaga: VagaDomain): boolean {
    const nomeRegex: RegExp = /^[\p{L},\s]{1,255}$/u;

    const nomeError = document.getElementById("nomeError");

    let isValid = true;

    if (!nomeRegex.test(vaga.nome)) {
      if (nomeError) {
        nomeError.hidden = false;
      }
      isValid = false;
    } else {
      if (nomeError) {
        nomeError.hidden = true;
      }
    }

    return isValid;
  }

  private login() {
    const formulario = document.getElementById("formulario");

    if (formulario) {
      formulario.addEventListener("submit", (event) => {
        event.preventDefault();

        const emailInput = document.getElementById("emailInput") as HTMLInputElement;
        const email: string = emailInput.value;

        let id: number = 0;
        let tipo: string = "";

        const empresa =  this.empresas.find((empresa) => empresa.email == email);
        if (empresa) {
          id = empresa.id;
          tipo = "empresa";
        }

        const candidato =  this.candidatos.find((candidato) => candidato.email == email);
        if (candidato) {
          id = candidato.id;
          tipo = "candidato";
        }

        let login: LoginDomain = {
          id: id,
          email: email,
          tipo: tipo,
        };

        if (this.validarLogin(login)) {
          if (tipo) {
            localStorage.setItem('login', JSON.stringify(login));

            if (tipo === "empresa")
              window.location.href = `${__webpack_public_path__}perfilEmpresa.html`;
            else if (tipo === "candidato")
              window.location.href = `${__webpack_public_path__}perfilCandidato.html`;
          } else {
            alert("Email não cadastrado");
          }
        }
      });
    }
  }

  private validarLogin(login: loginDomain): boolean {
    const emailRegex: RegExp = /^\S+@\w+\.\w{2,6}(\.\w{2})?$/;

    const emailError = document.getElementById("emailError");

    let isValid = true;
    if (!emailRegex.test(login.email)) {
      if (emailError) {
        emailError.hidden = false;
      }
      isValid = false;
    } else {
      if (emailError) {
        emailError.hidden = true;
      }
    }

    return isValid;
  }
}

export default Cadastro;
