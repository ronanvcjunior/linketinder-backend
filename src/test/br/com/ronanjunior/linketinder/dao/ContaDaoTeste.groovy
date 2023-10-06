package test.br.com.ronanjunior.linketinder.dao

import main.br.com.ronanjunior.linketinder.dao.ContaDao;
import main.br.com.ronanjunior.linketinder.model.Candidato;
import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Conta
import main.br.com.ronanjunior.linketinder.model.Empresa
import main.br.com.ronanjunior.linketinder.utils.Conexao;
import main.br.com.ronanjunior.linketinder.view.CandidatoView;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContaDaoTeste {
    static Conexao conexao;
    static ContaDao contaDao;
    static CandidatoView candidatoView;
    static ByteArrayOutputStream outputStream;
    static PrintStream originalOut;
    static ByteArrayInputStream inputStream;
    static InputStream originalSystemIn;

    @BeforeClass
    static void setUp() {
        conexao = new Conexao();
        contaDao = new ContaDao(conexao);
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
    void testeRegistrarCandidato() {
        //Given:
        String dataNascimento = "03/10/1993"
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy")
        Candidato candidato = new Candidato(null,"Stefany","Torres","51511285079",sdf.parse(dataNascimento),"Brasil","GO","73754657","Engenheira de Software",null);

        Conta conta = new Conta(null,"stefany@gmail.com","stefany",candidato,null);


        //When:
        Long idConta = contaDao.registrarCandidato(conta);

        //Then:
        assertNotNull(idConta);
    }

    @Test
    void testeRegistrarEmpresa() {
        //Given:
        Empresa empresa = new Empresa(null,"Empresa","12345678905398","Brasil","73754657",null);

        Conta conta = new Conta(null,"empresa@gmail.com","empresa",null,empresa);


        //When:
        Long idConta = contaDao.registrarEmpresa(conta);

        //Then:
        assertNotNull(idConta);
    }

    @Test
    void buscarContaPorId() {
        //When:
        Conta conta = contaDao.buscarContaPorId(1);

        //Then:
        println conta.candidato
        println conta.empresa
        assertNotNull(conta);
        assertTrue(conta.empresa || conta.candidato);
    }
}
