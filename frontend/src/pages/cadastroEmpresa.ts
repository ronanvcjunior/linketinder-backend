import "../assets/bootstrap/bootstrap.css"
import "../assets/css/styles.css"

import { library, dom } from "@fortawesome/fontawesome-svg-core";
import { faBars } from "@fortawesome/free-solid-svg-icons";

import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import Cadastro from "../components/Cadastro";
import cadastroEmpresaView from "../view/cadastroEmpresaView.html";

library.add(faBars);
dom.watch();

new Navbar('navbar-container', false);
new Cadastro("content-container", cadastroEmpresaView);
new Footer('footer-container');
