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
    static ByteArrayInputStream inputStream;
    static InputStream originalSystemIn;

    @BeforeClass
    static void setUp() {
        empresaView = new EmpresaView();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        originalSystemIn = System.in;
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outputStream));
    }

    @After
    public void restoreStreams() {
        outputStream.reset();
        System.setOut(originalOut);
        System.setIn(originalSystemIn);
    }

    @Test
    void testeExibirEmpresas() {
        //Given:
        Empresa empresa = new Empresa(
                nome: "ZG Soluções",
                pais: "Brasil",
                cep: "73754657",
                descricao: "ZG Soluções",
                cnpj: "14488144000143"
        );
        String expectativaOutput =  "(" +
                " nome: " + empresa.nome + "," +
                " cnpj: " + empresa.cnpj + "," +
                " pais: " + empresa.pais + "," +
                " cep: " + empresa.cep + "," +
                " descrição: " + empresa.descricao +
                ")\n";
        empresaView.setEmpresas([empresa]);

        //When:
        empresaView.exibirEmpresas();

        //Then:
        String resultadoOutput = outputStream;
        assertEquals(expectativaOutput, resultadoOutput);
    }

    @Test
    void testeCadastrarEmpresa() {
        //Given:
        Empresa empresa = new Empresa(
                nome: "ZG Soluções",
                pais: "Brasil",
                cep: "73754657",
                descricao: "ZG Soluções",
                cnpj: "14488144000143"
        );
        String input =  "${empresa.nome}\n" +
                "${empresa.cnpj}\n" +
                "${empresa.pais}\n" +
                "${empresa.cep}\n" +
                "${empresa.descricao}\n";
        inputStream = new ByteArrayInputStream(input.getBytes());

        empresaView.setScanner(new Scanner(inputStream));

        //When:
        Empresa empresaResultado = empresaView.cadastrarEmpresa();

        //Then:
        assertEquals(empresa.nome, empresaResultado.nome);
        assertEquals(empresa.cnpj, empresaResultado.cnpj);
        assertEquals(empresa.pais, empresaResultado.pais);
        assertEquals(empresa.cep, empresaResultado.cep);
        assertEquals(empresa.descricao, empresaResultado.descricao);
        assertEquals(empresa, empresaResultado);

        System.setIn(originalSystemIn);
    }
}
