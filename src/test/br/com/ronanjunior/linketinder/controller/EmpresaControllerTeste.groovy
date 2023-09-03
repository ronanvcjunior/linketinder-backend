package test.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.controller.EmpresaController
import main.br.com.ronanjunior.linketinder.model.Empresa
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class EmpresaControllerTeste {
    static EmpresaController empresaController;

    @BeforeClass
    static void instanciaEmpresaRepository() {
        empresaController = new EmpresaController();
    }

    @Test
    void testeAdicionarEmpresa() {
        //Given:
        Empresa empresa = new Empresa();
        List<Empresa> empresas = [];
        empresas.add(empresa);

        //When:
        empresaController.adicionarEmpresa(empresa);
        //Then:
        assertEquals(empresaController.getEmpresas(), empresas);
    }
}
