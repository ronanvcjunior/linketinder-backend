import "../assets/bootstrap/bootstrap.css"
import "../assets/css/styles.css"

import { library, dom } from "@fortawesome/fontawesome-svg-core";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import {faGithub} from "@fortawesome/free-brands-svg-icons";

import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import Cadastro from "../components/Cadastro";
import cadastroEmpresaView from "../view/cadastroEmpresaView.html";

declare const __webpack_public_path__: string;

(() => {
    const localStorageLogin = localStorage.getItem("login")

    if (localStorageLogin) {
        const parsedLogin = JSON.parse(localStorageLogin);
        if(parsedLogin.tipo == "empresa") {
            window.location.href = `${__webpack_public_path__}perfilEmpresa.html`;
            return;
        }
        else if (parsedLogin.tipo == "candidato") {
            window.location.href = `${__webpack_public_path__}perfilCandidato.html`;
            return;
        }
    }

    library.add(faBars);
    library.add(faGithub);
    dom.watch();

    new Navbar('navbar-container', true);

    const cadastroEmpresa = document.getElementById("cadastroEmpresa");
    if (cadastroEmpresa)
        cadastroEmpresa.classList.add("ativo");


    new Cadastro("content-container", cadastroEmpresaView, "empresa");
    new Footer('footer-container');
})();
