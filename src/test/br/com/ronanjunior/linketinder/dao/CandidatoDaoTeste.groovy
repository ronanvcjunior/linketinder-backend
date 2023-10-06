package test.br.com.ronanjunior.linketinder.dao

import main.br.com.ronanjunior.linketinder.dao.CandidatoDao
import main.br.com.ronanjunior.linketinder.dao.ContaDao
import main.br.com.ronanjunior.linketinder.model.Candidato
import main.br.com.ronanjunior.linketinder.model.Conta
import main.br.com.ronanjunior.linketinder.utils.Conexao
import main.br.com.ronanjunior.linketinder.view.CandidatoView
import org.junit.BeforeClass
import org.junit.Test

import java.text.SimpleDateFormat

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue

class CandidatoDaoTeste {
    static Conexao conexao;
    static CandidatoDao candidatoDao;

    @BeforeClass
    static void setUp() {
        conexao = new Conexao();
        candidatoDao = new CandidatoDao(conexao);
    }



    @Test
    void testeVerificarExistenciaCompetenciaParaCandidato() {
        //Given:
        //When:
        Boolean existe = candidatoDao.verificarExistenciaCompetenciaParaCandidato(54, 2, conexao.abrirConexao());

        //Then:
        assertTrue(existe)
    }
}
