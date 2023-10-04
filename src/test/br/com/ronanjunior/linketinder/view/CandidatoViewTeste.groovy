package test.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.view.CandidatoView
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import java.text.SimpleDateFormat

import static org.junit.jupiter.api.Assertions.assertEquals

class CandidatoViewTeste {
    static CandidatoView candidatoView;
    static ByteArrayOutputStream outputStream;
    static PrintStream originalOut;
    static ByteArrayInputStream inputStream;
    static InputStream originalSystemIn;

    @BeforeClass
    static void setUp() {
        candidatoView = new CandidatoView();
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
    void testeExibirCandidatos() {
        //Given:
        String dataNascimento = "03/10/1993"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
        Candidato candidato = new Candidato(
                nome: "Stefany",
                pais: "Brasil",
                estado: "Goiás",
                cep: "73754657",
                descricao: "Engenheira de Software",
                cpf: "51511285079",
                dataNascimento: sdf.parse(dataNascimento),
                competencias: [new Competencia(1, "Python")]
        );
        String expectativaOutput =  "(" +
                " nome: " + candidato.getNome() + "," +
                " cpf: " + candidato.getCpf() + "," +
                " Data de nascimento: " + dataNascimento + "," +
                " pais: " + candidato.getPais() + "," +
                " estado: " + candidato.getEstado() + "," +
                " cep: " + candidato.getCep() + "," +
                " competências: " + candidato.getCompetencias() + "," +
                " descrição: " + candidato.getDescricao() +
                ")\n";
        candidatoView.setCandidatos([candidato]);

        //When:
        candidatoView.exibirCandidatos();

        //Then:
        String resultadoOutput = outputStream;
        assertEquals(expectativaOutput, resultadoOutput);
    }

    @Test
    void testeCadastrarCandidato() {
        //Given:
        String dataNascimento = "03/10/1993"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
        Candidato candidato = new Candidato(
                nome: "Stefany",
                pais: "Brasil",
                estado: "Goiás",
                cep: "73754657",
                descricao: "Engenheira de Software",
                cpf: "51511285079",
                dataNascimento: sdf.parse(dataNascimento),
                competencias: [new Competencia(null, "Python")]
        );
        String input =  "${candidato.nome}\n" +
                "${candidato.cpf}\n" +
                "${dataNascimento}\n" +
                "${candidato.pais}\n" +
                "${candidato.estado}\n" +
                "${candidato.cep}\n" +
                "${candidato.competencias[0].nome}\n\n" +
                "${candidato.descricao}\n";
        inputStream = new ByteArrayInputStream(input.getBytes());

        candidatoView.setScanner(new Scanner(inputStream));
        candidatoView.setCompetenciasCadastradas([new Competencia(candidato.competencias[0].id, candidato.competencias[0].nome)]);

        //When:
        Candidato candidatoResultado = candidatoView.cadastrarCandidato();

        //Then:
        assertEquals(candidato.nome, candidatoResultado.nome);
        assertEquals(candidato.cpf, candidatoResultado.cpf);
        assertEquals(candidato.dataNascimento, candidatoResultado.dataNascimento);
        assertEquals(candidato.pais, candidatoResultado.pais);
        assertEquals(candidato.estado, candidatoResultado.estado);
        assertEquals(candidato.cep, candidatoResultado.cep);
        assertEquals(candidato.descricao, candidatoResultado.descricao);
        assertEquals(candidato.competencias, candidatoResultado.competencias);
        assertEquals(candidato, candidatoResultado);

        System.setIn(originalSystemIn);
    }

    @Test
    void testeCadastrarCandidatoCompetenciaQueNaoEstaNaListaCopetencias() {
        //Given:
        String dataNascimento = "03/10/1993"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
        Candidato candidato = new Candidato(
                nome: "Stefany",
                pais: "Brasil",
                estado: "Goiás",
                cep: "73754657",
                descricao: "Engenheira de Software",
                cpf: "51511285079",
                dataNascimento: sdf.parse(dataNascimento),
                competencias: [new Competencia(null, "Python"), new Competencia(null, "Java")]
        );
        String nomeCompetenciaQueNaoPertenceListaCompetencias = "Groovy";
        String input =  "${candidato.nome}\n" +
                "${candidato.cpf}\n" +
                "${dataNascimento}\n" +
                "${candidato.pais}\n" +
                "${candidato.estado}\n" +
                "${candidato.cep}\n" +
                "${candidato.competencias[0].nome}\n" +
                "${nomeCompetenciaQueNaoPertenceListaCompetencias}\n" +
                "${candidato.competencias[1].nome}\n\n" +
                "${candidato.descricao}\n";
        inputStream = new ByteArrayInputStream(input.getBytes());

        candidatoView.setScanner(new Scanner(inputStream));
        candidatoView.setCompetenciasCadastradas(
                [
                        new Competencia(candidato.competencias[0].id, candidato.competencias[0].nome),
                        new Competencia(candidato.competencias[1].id, candidato.competencias[1].nome)
                ]
        );

        //When:
        Candidato candidatoResultado = candidatoView.cadastrarCandidato();

        //Then:
        assertEquals(candidato.nome, candidatoResultado.nome);
        assertEquals(candidato.cpf, candidatoResultado.cpf);
        assertEquals(candidato.dataNascimento, candidatoResultado.dataNascimento);
        assertEquals(candidato.pais, candidatoResultado.pais);
        assertEquals(candidato.estado, candidatoResultado.estado);
        assertEquals(candidato.cep, candidatoResultado.cep);
        assertEquals(candidato.descricao, candidatoResultado.descricao);
        assertEquals(candidato.competencias, candidatoResultado.competencias);
        assertEquals(candidato, candidatoResultado);
    }
}
