package test.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.view.EmpresaView
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.jupiter.api.Assertions.assertEquals


class EmpresaViewTeste {
    static EmpresaView empresaView;
    static ByteArrayOutputStream outputStream;
    static PrintStream originalOut;

    @BeforeClass
    static void setUp() {
        empresaView = new EmpresaView();
        outputStream = new ByteArrayOutputStream();
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void restoreStreams() {
        outputStream.reset();
        System.setOut(originalOut);
    }

    @Test
    void testeExibirEmpresas() {
        //Given:
        Empresa empresa = new Empresa(
                nome: "ZG Soluções",
                email: "zg.solucoes@gmail.com",
                pais: "Brasil",
                estado: "Goiás",
                cep: "73754657",
                descricao: "ZG Soluções",
                cnpj: "14488144000143",
                competenciasEsperadas: [new Competencia(nome: "Groovy")]
        );
        String expectativaOutput =  "(" +
                " nome: " + empresa.nome + "," +
                " email: " + empresa.email + "," +
                " cnpj: " + empresa.cnpj + "," +
                " pais: " + empresa.pais + "," +
                " estado: " + empresa.estado + "," +
                " cep: " + empresa.cep + "," +
                " competências Esperadas: " + empresa.competenciasEsperadas + "," +
                " descrição: " + empresa.descricao +
                ")\n";
        empresaView.setEmpresas([empresa]);

        //When:
        empresaView.exibirEmpresas();

        //Then:
        String resultadoOutput = outputStream;
        assertEquals(expectativaOutput, resultadoOutput);
    }
}
