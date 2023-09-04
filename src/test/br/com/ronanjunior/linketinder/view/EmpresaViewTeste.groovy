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

    @Test
    void testeCadastrarEmpresa() {
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
        String input =  "${empresa.nome}\n" +
                "${empresa.email}\n" +
                "${empresa.cnpj}\n" +
                "${empresa.pais}\n" +
                "${empresa.estado}\n" +
                "${empresa.cep}\n" +
                "${empresa.competenciasEsperadas[0].nome}\n\n" +
                "${empresa.descricao}\n";
        inputStream = new ByteArrayInputStream(input.getBytes());

        empresaView.setScanner(new Scanner(inputStream));
        empresaView.setCompetenciasCadastradas([new Competencia(nome: empresa.competenciasEsperadas[0].nome)]);

        //When:
        Empresa empresaResultado = empresaView.cadastrarEmpresa();

        //Then:
        assertEquals(empresa.nome, empresaResultado.nome);
        assertEquals(empresa.email, empresaResultado.email);
        assertEquals(empresa.cnpj, empresaResultado.cnpj);
        assertEquals(empresa.pais, empresaResultado.pais);
        assertEquals(empresa.estado, empresaResultado.estado);
        assertEquals(empresa.cep, empresaResultado.cep);
        assertEquals(empresa.descricao, empresaResultado.descricao);
        assertEquals(empresa.competenciasEsperadas, empresaResultado.competenciasEsperadas);
        assertEquals(empresa, empresaResultado);

        System.setIn(originalSystemIn);
    }

    @Test
    void testeCadastrarEmpresaCompetenciaQueNaoEstaNaListaCopetencias() {
        //Given:
        Empresa empresa = new Empresa(
                nome: "ZG Soluções",
                email: "zg.solucoes@gmail.com",
                pais: "Brasil",
                estado: "Goiás",
                cep: "73754657",
                descricao: "ZG Soluções",
                cnpj: "14488144000143",
                competenciasEsperadas: [new Competencia(nome: "Groovy"), new Competencia(nome: "TypeScript")]
        );
        String nomeCompetenciaQueNaoPertenceListaCompetencias = "Python";
        String input =  "${empresa.nome}\n" +
                "${empresa.email}\n" +
                "${empresa.cnpj}\n" +
                "${empresa.pais}\n" +
                "${empresa.estado}\n" +
                "${empresa.cep}\n" +
                "${empresa.competenciasEsperadas[0].nome}\n" +
                "${nomeCompetenciaQueNaoPertenceListaCompetencias}\n" +
                "${empresa.competenciasEsperadas[1].nome}\n\n" +
                "${empresa.descricao}\n";
        inputStream = new ByteArrayInputStream(input.getBytes());

        empresaView.setScanner(new Scanner(inputStream));
        empresaView.setCompetenciasCadastradas([new Competencia(nome: empresa.competenciasEsperadas[0].nome), new Competencia(nome: empresa.competenciasEsperadas[1].nome)]);

        //When:
        Empresa empresaResultado = empresaView.cadastrarEmpresa();

        //Then:
        assertEquals(empresa.nome, empresaResultado.nome);
        assertEquals(empresa.email, empresaResultado.email);
        assertEquals(empresa.cnpj, empresaResultado.cnpj);
        assertEquals(empresa.pais, empresaResultado.pais);
        assertEquals(empresa.estado, empresaResultado.estado);
        assertEquals(empresa.cep, empresaResultado.cep);
        assertEquals(empresa.descricao, empresaResultado.descricao);
        assertEquals(empresa.competenciasEsperadas, empresaResultado.competenciasEsperadas);
        assertEquals(empresa, empresaResultado);

        System.setIn(originalSystemIn);
    }
}
