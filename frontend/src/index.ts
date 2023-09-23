import "./assets/bootstrap/bootstrap.css"
import "./assets/css/styles.css"

import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import { dom, library } from "@fortawesome/fontawesome-svg-core";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import { faGithub } from "@fortawesome/free-brands-svg-icons";

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

    new Navbar("navbar-container", true);
    new Footer("footer-container");
    
    const localStorageEmpresas: (string|null) = localStorage.getItem('empresas');
    const localStorageCandidatos: (string|null) = localStorage.getItem('candidatos');
    const localStorageVagas: (string|null) = localStorage.getItem('vagas');
    const localStorageCompetencias: (string|null) = localStorage.getItem('competencias');
    
    if (!localStorageEmpresas || !localStorageCandidatos || !localStorageVagas || !localStorageCompetencias) {
        import("./utils/LocalStorageManager").then((module): void => {
            const localStorageManager = module.default;
            new localStorageManager();
        });
    }
})();

