package test.br.com.ronanjunior.linketinder.controller

import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.controller.CandidatoController
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.jupiter.api.Assertions.assertEquals

class CandidatoControllerTeste {
    static CandidatoController candidatoRepository;

    @BeforeClass
    static void instanciaCandidatoRepository() {
        candidatoRepository = new CandidatoController();
    }

    @Test
    void testeCadastrarCandidato() {
        //Given:
        Candidato candidato = new Candidato();
        List<Candidato> candidatos = [];
        candidatos.add(candidato);

        //When:
        candidatoRepository.adicionarCandidato(candidato);
        //Then:
        assertEquals(candidatoRepository.getCandidatos(), candidatos);
    }
}
