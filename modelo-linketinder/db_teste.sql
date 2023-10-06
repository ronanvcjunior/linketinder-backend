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
	
SELECT va.* FROM Vaga va
INNER JOIN Empresa em ON em.id_empresa = va.id_empresa
ORDER BY em.id_empresa;
	
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

SELECT  FROM Candidato can
LEFT JOIN Conta con ON con.id_candidato = can.id_candidato;

SELECT
    em.nome AS nome_empresa,
    em.pais,
    em.descricao,
    CASE
        WHEN match.id_candidato IS NOT NULL
        THEN em.nome
        ELSE 'Nome Personalizado' -- Substitua 'Nome Personalizado' pelo nome desejado
    END AS nome_analisado
FROM Empresa em
LEFT JOIN Conta con ON con.id_empresa = em.id_empresa
LEFT JOIN (
    SELECT DISTINCT id_candidato
    FROM Match
    WHERE data_curtida_candidato IS NOT NULL
    AND data_curtida_vaga IS NOT NULL
) match
ON con.id_candidato = match.id_candidato
ORDER BY nome_empresa;


SELECT DISTINCT em.id_empresa, em.nome FROM Match ma
LEFT JOIN Vaga va ON va.id_vaga = ma.id_vaga
LEFT JOIN Empresa em ON em.id_empresa = va.id_empresa;

SELECT 
	DISTINCT em.id_empresa, 
	em.nome, 
	data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL AS match
FROM Empresa em
LEFT JOIN Vaga va ON va.id_empresa = em.id_empresa
LEFT JOIN Match ma ON ma.id_vaga = va.id_vaga
ORDER BY match DESC, nome;

SELECT * FROM Empresa em
LEFT JOIN Vaga va ON va.id_empresa = em.id_empresa;

SELECT DISTINCT em.id_empresa, em.nome, em.pais, em.descricao
FROM Empresa em
LEFT JOIN Vaga va ON va.id_empresa = em.id_empresa;


SELECT 
    DISTINCT em.id_empresa, 
    CASE
        WHEN (data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL) THEN em.nome
        ELSE 'Anonimo'
    END AS nome,
    data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL AS match,
	ma.*
FROM Empresa em
LEFT JOIN Vaga va ON va.id_empresa = em.id_empresa
LEFT JOIN (
	SELECT * FROM Match WHERE id_candidato = 2
) ma ON ma.id_vaga = va.id_vaga
ORDER BY match DESC, nome;

SELECT
    em.id_empresa,
    CASE
        WHEN MAX(data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL) THEN MAX(em.nome)
        ELSE 'Anonimo'
    END AS nome,
    MAX(data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL) AS match
FROM Empresa em
LEFT JOIN Vaga va ON va.id_empresa = em.id_empresa
LEFT JOIN (
    SELECT * FROM Match WHERE id_candidato = 2
) ma ON ma.id_vaga = va.id_vaga
GROUP BY em.id_empresa
ORDER BY match DESC, nome;

SELECT * FROM VAG;

INSERT INTO Match (data_curtida_candidato, data_curtida_vaga, id_candidato, id_vaga)
VALUES
    ('2023-09-01', '2023-09-01', 23, 28),
    ('2023-09-02', '2023-09-02', 2, 23)
;

SELECT
    em.id_empresa,
    CASE
        WHEN MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) = 1 THEN em.nome
        ELSE 'Anonimo'
    END AS nome,
    MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) AS match
FROM Empresa em
LEFT JOIN Vaga va ON va.id_empresa = em.id_empresa
LEFT JOIN (
    SELECT * FROM Match WHERE id_candidato = 2
) ma ON ma.id_vaga = va.id_vaga
GROUP BY em.id_empresa
ORDER BY match DESC, nome ASC, id_empresa ASC;



SELECT
    can.id_candidato,
    CASE
        WHEN MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) = 1 THEN CONCAT(can.nome, ' ', can.sobrenome)
        ELSE 'Anonimo'
    END AS nome_completo,
    MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) AS match
FROM Candidato can
LEFT JOIN (
    SELECT * FROM Match WHERE id_vaga IN (
		SELECT va.id_vaga FROM Vaga va
		RIGHT JOIN Empresa em ON em.id_empresa = va.id_empresa
		WHERE em.id_empresa = 5
	)
) ma ON ma.id_candidato = can.id_candidato
GROUP BY can.id_candidato
ORDER BY match DESC, nome_completo ASC, id_candidato ASC;

SELECT con.id_competencia, con.nome FROM Candidato_Competencia cc
LEFT JOIN Competencia con ON con.id_competencia = cc.id_competencia
WHERE cc.id_candidato = 2

SELECT
    va.id_vaga,
    CASE
        WHEN MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) = 1 THEN va.nome
        ELSE 'Anonimo'
    END AS nome,
    MAX(CASE WHEN data_curtida_candidato IS NOT NULL AND data_curtida_vaga IS NOT NULL THEN 1 ELSE 0 END) AS match
FROM Vaga va
LEFT JOIN (
    SELECT * FROM Match WHERE id_candidato = 2
) ma ON ma.id_vaga = va.id_vaga
GROUP BY va.id_vaga
ORDER BY match DESC, nome ASC, va.id_vaga ASC;

SELECT id_vaga, nome
FROM Vaga
