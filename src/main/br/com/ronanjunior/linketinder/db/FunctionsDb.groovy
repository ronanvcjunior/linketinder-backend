package main.br.com.ronanjunior.linketinder.db

import groovy.sql.Sql
import main.br.com.ronanjunior.linketinder.dao.CandidatoDao
import main.br.com.ronanjunior.linketinder.dao.VagaDao
import main.br.com.ronanjunior.linketinder.dto.CandidatoListaDaEmpresaDto
import main.br.com.ronanjunior.linketinder.dto.VagaListaDoCandidadoDto
import main.br.com.ronanjunior.linketinder.model.Empresa

//CandidatoDao c = new CandidatoDao();
//List<CandidatoListaDaEmpresaDto> ca = c.ListarCandidatosParaEmpresa(5);
//
//ca.forEach {candidato -> {
//    println candidato.competencias
//}}

VagaDao v = new VagaDao();
List<VagaListaDoCandidadoDto> va = v.ListarTodasVagas();

va.forEach {vaga -> {
    println vaga.nome
}}