import "select2/dist/css/select2.min.css";

class Cadastro {
  private container: HTMLElement;
  private cadastroView: string;

  constructor(containerId: string, cadastroView: string) {
    const container: (HTMLElement|null) = document.getElementById(containerId);

    if (container) {
      console.log(containerId)
      this.container = container;
    } else {
      throw new Error(`Element with id "${containerId}" is not an HTMLElement.`);
    }

    this.cadastroView = cadastroView;

    this.render();
    this.montarSelect2();
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
}

export default Cadastro;
