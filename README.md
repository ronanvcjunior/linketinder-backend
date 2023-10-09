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