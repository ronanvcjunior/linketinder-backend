import "./assets/bootstrap/bootstrap.css"
import "./assets/css/styles.css"

import { library, dom } from "@fortawesome/fontawesome-svg-core";
import { faBars } from "@fortawesome/free-solid-svg-icons";

import Navbar from "./components/Navbar";

library.add(faBars);
dom.watch();

new Navbar('navbar-container', true);

