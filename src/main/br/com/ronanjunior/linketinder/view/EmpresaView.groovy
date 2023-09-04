package main.br.com.ronanjunior.linketinder.view

import main.br.com.ronanjunior.linketinder.model.Competencia
import main.br.com.ronanjunior.linketinder.model.Empresa

class EmpresaView {
    List<Empresa> empresas;
    Scanner scanner = new Scanner(System.in);
    List<Competencia> competenciasCadastradas = [];

    void exibirEmpresas() {
        empresas.each {empresa ->
            print   "(" +
                    " nome: " + empresa.nome + "," +
                    " email: " + empresa.email + "," +
                    " cnpj: " + empresa.cnpj + "," +
                    " pais: " + empresa.pais + "," +
                    " estado: " + empresa.estado + "," +
                    " cep: " + empresa.cep + "," +
                    " competências Esperadas: " + empresa.competenciasEsperadas + "," +
                    " descrição: " + empresa.descricao +
                    ")\n";
        }
    }

    Empresa cadastrarEmpresa() {
        println("Cadastro de uma nova empresa:")

        print "Nome: ";
        String nome = scanner.nextLine();
        print "Email: ";
        String email = scanner.nextLine();
        print "CNPJ: ";
        String cnpj = scanner.nextLine();
        print "Pais: ";
        String pais = scanner.nextLine();
        print "Estado: ";
        String estado = scanner.nextLine();
        print "CEP: ";
        String cep = scanner.nextLine();

        List<Competencia> competenciasEsperadas = [];
        while (true) {
            print "Competência (ou deixe em branco para encerrar): ";
            String nomeCompetencia = scanner.nextLine();
            Competencia competencia = new Competencia(nome: nomeCompetencia);
            if (nomeCompetencia.isEmpty()) {
                break;
            }
            if (competenciasCadastradas.contains(competencia)) {
                competenciasEsperadas.add(competencia);
            } else {
                println("a competência ${nomeCompetencia} não está presente na lista de competências");
            }

        }

        print "Descrição: ";
        String descricao = scanner.nextLine();

        return new Empresa(
                nome: nome,
                email: email,
                cnpj: cnpj,
                pais: pais,
                estado: estado,
                cep: cep,
                competenciasEsperadas: competenciasEsperadas,
                descricao: descricao
        );
    }
}
