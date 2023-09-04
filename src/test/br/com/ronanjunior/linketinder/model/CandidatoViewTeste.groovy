package test.br.com.ronanjunior.linketinder.model

import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.view.CandidatoView
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class CandidatoViewTeste {
    static CandidatoView candidatoView
    static ByteArrayOutputStream outputStream;

    @BeforeClass
    static void instanciaCandidatoView() {
        candidatoView = new CandidatoView();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream((outputStream)));
    }

    @Test
    void testeExibirCandidatos() {
        //Given:
        Candidato candidato = new Candidato(
                nome: "Stefany",
                email: "stefany@gmail.com",
                pais: "Brasil",
                estado: "Goiás",
                cep: "73754657",
                descricao: "Engenheira de Software",
                cpf: "51511285079",
                idade: 30,
                competencias: [new Competencia(nome: "Python")]
        );
        String expectativaOutput =  "(" +
                                    " nome: " + candidato.getNome() + "," +
                                    " email: " + candidato.getEmail() + "," +
                                    " cpf: " + candidato.getCpf() + "," +
                                    " idade: " + candidato.getIdade() + "," +
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
}
