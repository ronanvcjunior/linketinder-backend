# Linketinder Backend (MVP)

Autor: Ronan Vieira do Carmo Junior

### v1.0.0

Use o comando abaixo para executar o Linketinder:

```bash
java -jar linketinder-backend.jar
```

#### Seguem as opções:

- ajuda: Mostra todas as opções disponíveis
- candidato cadastrar: Para cadastrar um novo candidato
- candidato listar: Mostra todos os candidatos
- empresa cadastrar: Para cadastrar uma nova empresa
- empresa listar: Mostra todas as empresas
- sair: Para sair do programa

## FrontEnd

Pode ser visualizado com o link: https://ronanvcjunior.github.io/linketinder-backend/frontend/dist/

usa-se o jquery, select2, webpack e chart.js.

## Banco de Dados

Segue o Link para a documentação da modelagem do Linketinder: 
https://docs.google.com/document/d/111R26LkdVmqt9GBiHxdhPhFTjt6V6eofKp5wjSBo470/edit?usp=sharing

Os arquivos de criação e teste do banco estão na pasta [modelo-linketinder](./modelo-linketinder/)

## Gradle

Com o uso do Gradle é possível gerenciar as dependências do projeto de forma mais simples, com isso a forma de utilização da conexão com o banco de dados mudou. Agora usa-se o .env para manter as informações seguras (URL, USUARIO e SENHA).

## Clean Code

1. Nomes alto explicativos: busquei ao máximo desde o início do projeto em colocar nomes autoexplicativos, então não teve grandes alterações no projeto;
2. Regra do escoteiro: revisei a maioria do código, fazendo melhorias onde coube;
3. Crie funções pequenas: onde observei que fosse necessário, extrai partes da função para mantelá pequena;
4. Don't Repeat Yourself (DRY): removi as replicações de código que ocorria na inserção de dados de candidato, empresa e competências para os dois;
5. Somente comente se for estritamente necessário: Apenas têm comentários básicos nos testes (given, when, then);
6. Tratamento de erros: busquei tratar sempre os erros usando try/catch nas classes de controller, service, dao e util;
7. Testagem: Fiz testes unitários para as classes service e dao, além da classe mapperUtils;
8. Princípio da Responsabilidade Única: Criei novas classes de controller, service e dao para dividir as classes de competência e conta, criando novas classes CandidatoCOmpetencia, VagaCompetencia e Autenticacao para manter uma clareza maior das classes;

## S.O.L.I.D.

1. Princípio da responsabilidade única: A principal parte do código que é a de controller, service e dao tem suas responsabilidades bem definidas. As classes Controller ficaram responsáveis por gerenciar as dependências e fazer a chamada de suas respectivas service. As services ficaram responsáveis por manipular as lógicas de négocio, assegurar a correta transação de dados e a chamada da sua respectiva classe dao. As classes Dao ficaram responsáveis por manipular as operações do banco de dados. Por uma questão de design, foi abstraio o controle de transação da classe dao para manter uma comunicação entre classes apenas na camade de Service.
2. Princípio aberto/fechado: Qualquer adição de métodos as classes não afeta o funcionamento do sistema;
3. Princípio de substituição de Liskov: Não há nenhuma herança no sistema, por uma escolha de design, busquei evitar o uso de herança;
4. princípio da segregação de interface: Foi implementado a classe ConexaoRepository, para fazer com que seja possível a partir de qualquer biblioteca de manipulação de banco, seja facilmente alterada no sistema;
5. princípio da inversão de dependência: As dependências são gerenciadas pelas classes Controller;