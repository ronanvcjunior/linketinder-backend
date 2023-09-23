import "../assets/bootstrap/bootstrap.css"
import "../assets/css/styles.css"


import { library, dom } from "@fortawesome/fontawesome-svg-core";
import { faBars } from "@fortawesome/free-solid-svg-icons";

import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import Cadastro from "../components/Cadastro";
import loginView from "../view/loginView.html";

library.add(faBars);
dom.watch();

new Navbar('navbar-container', true);

const login = document.getElementById("login");
if (login)
    login.classList.add("ativo");


new Cadastro("content-container", loginView, "login");
new Footer('footer-container');