import "../assets/bootstrap/bootstrap.css"
import "../assets/css/styles.css"

import { library, dom } from "@fortawesome/fontawesome-svg-core";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import {faGithub} from "@fortawesome/free-brands-svg-icons";

import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import DashboardUsuario from "../components/DashboardUsuario";

declare const __webpack_public_path__: string;

(() => {
    const localStorageLogin = localStorage.getItem("login")

    if (localStorageLogin) {
        const parsedLogin = JSON.parse(localStorageLogin);
        if(parsedLogin.tipo == "empresa") {
            window.location.href = `${__webpack_public_path__}perfilEmpresa.html`;
            return;
        }
    } else {
        window.location.href = `${__webpack_public_path__}`;
        return;
    }

    library.add(faBars);
    library.add(faGithub);
    dom.watch();

    new Navbar('navbar-container', false);
    new DashboardUsuario("content-container");
    new Footer('footer-container');
})();


