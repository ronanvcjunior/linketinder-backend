package test.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.view.CandidatoView
import main.br.com.ronanjunior.linketinder.view.TerminalInterativoView
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class TerminalInterativoViewTeste {
    static TerminalInterativoView terminalInterativoView;
    static ByteArrayOutputStream outputStream;
    static PrintStream originalOut;

    @BeforeClass
    static void setUp() {
        terminalInterativoView = new TerminalInterativoView();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
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
    public void testeExibirAjuda() {
        //Given:
        String expectativaOutput =  "Opções disponíveis:\n" +
                " - ajuda: Mostra todas as opções disponíveis\n" +
                " - candidato cadastrar: Para cadastrar um novo candidato\n" +
                " - candidato listar: Mostra todos os candidatos\n" +
                " - empresa cadastrar: Para cadastrar uma nova empresa\n" +
                " - empresa listar: Mostra todas as empresas\n" +
                " - sair: Para sair do programa\n" +
                "\n";

        //When:
        terminalInterativoView.exibirAjuda();

        //Then:
        String resultadoOutput = outputStream;
        assertEquals(expectativaOutput, resultadoOutput);
    }

    @Test
    public void testeExibirOpcaoInvalida() {
        //Given:
        String expectativaOutput =  "Opção inválida\n" +
                " digite 'ajuda', para mostrar todas as opções disponíveis\n" +
                "\n";

        //When:
        terminalInterativoView.exibirOpcaoInvalida();

        //Then:
        String resultadoOutput = outputStream;
        assertEquals(expectativaOutput, resultadoOutput);
    }
}
