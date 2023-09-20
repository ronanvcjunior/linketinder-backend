import "./assets/bootstrap/bootstrap.css"
import "./assets/css/styles.css"

import { library, dom } from "@fortawesome/fontawesome-svg-core";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import {faGithub} from "@fortawesome/free-brands-svg-icons";

import Navbar from "./components/Navbar";
import Footer from "./components/Footer";

library.add(faBars);
library.add(faGithub)
dom.watch();

new Navbar('navbar-container', true);
new Footer('footer-container');

