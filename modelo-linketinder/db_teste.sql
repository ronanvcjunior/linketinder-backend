DROP SCHEMA public CASCADE;
CREATE SCHEMA public;


SELECT * FROM Competencia ORDER BY nome;

SELECT * FROM Candidato;

SELECT * FROM Empresa;

SELECT * FROM Conta;

SELECT * FROM Vaga;

SELECT * FROM candidato_competencia;

SELECT * FROM vaga_competencia;

SELECT * FROM match;

SELECT ca.*, co.email, co.senha FROM Candidato ca
INNER JOIN Conta co ON ca.id_candidato = co.id_candidato;

SELECT e.*, c.email, c.senha FROM empresa e
INNER JOIN Conta c ON c.id_empresa = e.id_empresa;

SELECT ca.id_candidato, ca.nome, co.nome as competencia FROM candidato ca
INNER JOIN candidato_competencia cc ON ca.id_candidato = cc.id_candidato
INNER JOIN competencia co ON co.id_competencia = cc.id_competencia
ORDER BY ca.id_candidato;

SELECT e.id_empresa, e.nome, v.nome FROM empresa e
INNER JOIN vaga v ON e.id_empresa = v.id_empresa
ORDER BY e.id_empresa;

SELECT v.id_vaga, v.nome, c.nome as competencia FROM vaga v
INNER JOIN vaga_competencia vc ON  vc.id_vaga = v.id_vaga
INNER JOIN competencia c ON c.id_competencia = vc.id_competencia
ORDER BY v.id_vaga, c.nome;

SELECT c.nome As candidato, v.nome AS vaga FROM match m
INNER JOIN candidato c ON c.id_candidato = m.id_candidato
INNER JOIN vaga v ON v.id_vaga = m.id_vaga
WHERE m.data_curtida_candidato IS NOT NULL
	AND m.data_curtida_vaga IS NOT NULL;
	
DO $$
DECLARE
    variavel_id_candidato INTEGER;
BEGIN
    INSERT INTO Candidato (nome, sobrenome, cpf, data_nascimento, Pais, CEP, Estado, descricao)
    VALUES ('João', 'Silva', '58697386913', '1990-01-15', 'Brasil', '12345678', 'São Paulo', 'Alguma descrição')
    RETURNING id_candidato INTO variavel_id_candidato;
    
    INSERT INTO Conta (email, senha, id_candidato)
	VALUES ('joaoz@email.com', 'senha123', variavel_id_candidato);
END $$;

DO $$
DECLARE
    variavel_id_candidato INTEGER;
    competencias INTEGER[]; -- Uma matriz de IDs de competências
    competencia_id INTEGER;
BEGIN
    -- Inserir o candidato e obter o ID gerado
    INSERT INTO Candidato (nome, sobrenome, cpf, data_nascimento, Pais, CEP, Estado, descricao)
    VALUES ('João', 'Silva', '05964961239', '1990-01-15', 'Brasil', '12345678', 'São Paulo', 'Alguma descrição')
    RETURNING id_candidato INTO variavel_id_candidato;

    -- Defina a lista de IDs de competências
    competencias := ARRAY[1, 3, 6, 42, 7];

    -- Insira as competências no loop
    FOREACH competencia_id IN ARRAY competencias
    LOOP
        INSERT INTO Candidato_Competencia (id_candidato, id_competencia)
        VALUES (variavel_id_candidato, competencia_id);
    END LOOP;

    -- Inserir a conta vinculada ao candidato
    INSERT INTO Conta (email, senha, id_candidato)
    VALUES ('joaozin@email.com', 'senha123', variavel_id_candidato);
END $$;

SELECT
    id_match,
    id_candidato,
    id_vaga,
    data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL AS match
FROM Match;

DO $$
DECLARE
    variavel_id_candidato INTEGER;
    variavel_id_empresa INTEGER;
BEGIN
    INSERT INTO Candidato (nome, sobrenome, cpf, data_nascimento, Pais, CEP, Estado, descricao)
    VALUES ('João', 'Silva', '58697386913', '1990-01-15', 'Brasil', '12345678', 'São Paulo', 'Alguma descrição')
    RETURNING id_candidato INTO variavel_id_candidato;
	
	INSERT INTO Empresa (nome, cnpj, descricao, pais, cep)
	VALUES ('Tech Solutions Ltda', '49570123869123', 'Uma empresa de tecnologia', 'Brasil', '12345678')
	RETURNING id_empresa INTO variavel_id_empresa;
    
    INSERT INTO Conta (email, senha, id_candidato, id_empresa)
	VALUES ('joaoz@email.com', 'senha123', variavel_id_candidato, variavel_id_empresa);
END $$;
