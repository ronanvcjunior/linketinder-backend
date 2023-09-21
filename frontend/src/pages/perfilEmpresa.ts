import "../assets/bootstrap/bootstrap.css"
import "../assets/css/styles.css"

import { library, dom } from "@fortawesome/fontawesome-svg-core";
import { faBars } from "@fortawesome/free-solid-svg-icons";

import Navbar from "../components/Navbar";
import Footer from "../components/Footer";
import DashboardEmpresa from "../components/DashboardEmpresa";

library.add(faBars);
dom.watch();

new Navbar('navbar-container', false);
new DashboardEmpresa("content-container");
new Footer('footer-container');