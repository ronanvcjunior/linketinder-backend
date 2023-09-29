-- Criar Banco 
CREATE DATABASE db_Linketinder;

-- Criar tabelas
CREATE TABLE Candidato (
    id_candidato SERIAL NOT NULL,
    nome VARCHAR(20) NOT NULL,
    sobrenome VARCHAR(50) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    data_nascimento DATE NOT NULL,
    Pais VARCHAR(50) NOT NULL,
    CEP VARCHAR(8) NOT NULL,
    Estado VARCHAR(50) NOT NULL,
    descricao VARCHAR(255) NULL,
	CONSTRAINT pk_id_candidato PRIMARY KEY (id_candidato),
	CONSTRAINT unq_cpf UNIQUE (cpf)
);

CREATE TABLE Empresa (
    id_empresa SERIAL NOT NULL,
    nome VARCHAR(50) NOT NULL,
    cnpj VARCHAR(14) NOT NULL,
    descricao VARCHAR(255) NULL,
    pais VARCHAR(50) NOT NULL,
    cep VARCHAR(8) NOT NULL,
	CONSTRAINT pk_id_empresa PRIMARY KEY (id_empresa),
	CONSTRAINT unq_cnpj UNIQUE (cnpj)
);

CREATE TABLE Competencia (
    id_competencia SERIAL NOT NULL,
    nome VARCHAR(50) NOT NULL,
	CONSTRAINT pk_id_competencia PRIMARY KEY (id_competencia),
	CONSTRAINT unq_nome UNIQUE (nome)
);

CREATE TABLE Vaga (
    id_vaga SERIAL NOT NULL,
    nome VARCHAR(50) NOT NULL,
    descricao VARCHAR(255) NULL,
    estado VARCHAR(50) NULL,
    cidade VARCHAR(50) NULL,
    id_empresa INTEGER NOT NULL,
	CONSTRAINT pk_id_vaga PRIMARY KEY (id_vaga),
	CONSTRAINT fk_id_empresa FOREIGN KEY (id_empresa)
    	REFERENCES Empresa (id_empresa)
    	ON DELETE CASCADE
);

CREATE TABLE Match (
    id_match SERIAL NOT NULL,
    data_curtida_candidato DATE NULL,
    data_curtida_vaga DATE NULL,
    id_candidato INTEGER NOT NULL,
    id_vaga INTEGER NOT NULL,
	CONSTRAINT pk_id_match PRIMARY KEY (id_match),
	CONSTRAINT fk_id_candidato FOREIGN KEY (id_candidato)
    	REFERENCES Candidato (id_candidato)
    	ON DELETE CASCADE,
	CONSTRAINT fk_id_vaga FOREIGN KEY (id_vaga)
    	REFERENCES Vaga (id_vaga)
    	ON DELETE CASCADE,
	CONSTRAINT unique_candidato_vaga UNIQUE (id_candidato, id_vaga)
);

CREATE TABLE Candidato_Competencia (
    id_candidato_competencia SERIAL NOT NULL,
    id_candidato INTEGER NOT NULL,
    id_competencia INTEGER NOT NULL,
	CONSTRAINT pk_id_candidato_competencia PRIMARY KEY (id_candidato_competencia),
	CONSTRAINT fk_id_candidato FOREIGN KEY (id_candidato)
    	REFERENCES Candidato (id_candidato)
    	ON DELETE CASCADE,
	CONSTRAINT fk_id_competencia FOREIGN KEY (id_competencia)
    	REFERENCES Competencia (id_competencia)
    	ON DELETE RESTRICT,
	CONSTRAINT unique_candidato_competencia UNIQUE (id_candidato, id_competencia)
);

CREATE TABLE Vaga_Competencia (
    id_vaga_competencia SERIAL NOT NULL,
    id_vaga INTEGER NOT NULL,
    id_competencia INTEGER NOT NULL,
	CONSTRAINT pk_id_vaga_competencia PRIMARY KEY (id_vaga_competencia),
	CONSTRAINT fk_id_vaga FOREIGN KEY (id_vaga)
    	REFERENCES Vaga (id_vaga)
    	ON DELETE CASCADE,
	CONSTRAINT fk_id_competencia FOREIGN KEY (id_competencia)
    	REFERENCES Competencia (id_competencia)
    	ON DELETE RESTRICT,
	CONSTRAINT unique_vaga_competencia UNIQUE (id_vaga, id_competencia)
);

CREATE TABLE Conta (
    id_conta SERIAL NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    id_candidato INTEGER NULL,
    id_empresa INTEGER NULL,
	CONSTRAINT pk_id_conta PRIMARY KEY (id_conta),
	CONSTRAINT fk_id_candidato FOREIGN KEY (id_candidato)
    	REFERENCES Candidato (id_candidato)
    	ON DELETE CASCADE,
	CONSTRAINT fk_id_empresa FOREIGN KEY (id_empresa)
    	REFERENCES Empresa (id_empresa)
    	ON DELETE CASCADE,
	CONSTRAINT unq_email UNIQUE (email),
	CONSTRAINT unq_id_candidato UNIQUE (id_candidato),
	CONSTRAINT unq_id_empresa UNIQUE (id_empresa),
	CONSTRAINT chk_candidato_ou_empresa 
        CHECK (
			(id_candidato IS NULL AND id_empresa IS NOT NULL) 
			OR (id_candidato IS NOT NULL AND id_empresa IS NULL)
		)

);

-- Carga de dados para teste
INSERT INTO Competencia (nome) VALUES
    ('Python'),
    ('Java'),
    ('JavaScript'),
    ('C++'),
    ('Ruby'),
    ('Swift'),
    ('React'),
    ('Angular'),
    ('Node.js'),
    ('Django'),
    ('Ruby on Rails'),
    ('Spring Boot'),
    ('Vue.js'),
    ('PostgreSQL'),
    ('MongoDB'),
    ('SQL Server'),
    ('Git'),
    ('AWS'),
    ('Docker'),
    ('Kubernetes'),
    ('HTML'),
    ('CSS'),
    ('PHP'),
    ('C#'),
    ('Groovy'),
    ('TypeScript'),
    ('Android Development'),
    ('iOS Development'),
    ('Front-end Development'),
    ('Back-end Development'),
    ('Full Stack Development'),
    ('Data Science'),
    ('Machine Learning'),
    ('Artificial Intelligence'),
    ('DevOps'),
    ('Blockchain'),
    ('UI/UX Design'),
    ('Cybersecurity'),
    ('Network Security'),
    ('Cloud Computing'),
    ('Web Development'),
    ('Mobile App Development'),
    ('Game Development'),
    ('Big Data'),
    ('Augmented Reality (AR)'),
    ('Virtual Reality (VR)'),
    ('IoT (Internet of Things)'),
    ('Microservices'),
    ('Serverless Architecture'),
    ('Ethical Hacking')
;

INSERT INTO Candidato (nome, sobrenome, cpf, data_nascimento, pais, cep, estado, descricao)
VALUES
    ('João', 'Silva', '12345678901', '1990-05-15', 'Brasil', '12345678', 'SP', 'Descrição 1'),
    ('Maria', 'Santos', '23456789012', '1985-02-20', 'Brasil', '23456789', 'RJ', 'Descrição 2'),
    ('Pedro', 'Ferreira', '34567890123', '1992-11-10', 'Brasil', '34567890', 'MG', 'Descrição 3'),
    ('Ana', 'Oliveira', '45678901234', '1987-07-05', 'Brasil', '45678901', 'RS', 'Descrição 4'),
    ('Carlos', 'Ribeiro', '56789012345', '1995-03-25', 'Brasil', '56789012', 'PR', 'Descrição 5'),
    ('Sandra', 'Araujo', '67890123456', '1991-08-12', 'Brasil', '67890123', 'SC', 'Descrição 6'),
    ('Luiz', 'Pereira', '78901234567', '1984-06-30', 'Brasil', '78901234', 'BA', 'Descrição 7'),
    ('Fernanda', 'Gomes', '89012345678', '1989-04-18', 'Brasil', '89012345', 'CE', 'Descrição 8'),
    ('Ricardo', 'Martins', '90123456789', '1993-12-03', 'Brasil', '90123456', 'GO', 'Descrição 9'),
    ('Juliana', 'Almeida', '01234567890', '1988-09-22', 'Brasil', '01234567', 'PE', 'Descrição 10'),
    ('José', 'Pereira', '78912345670', '1986-07-10', 'Brasil', '23456789', 'MG', 'Descrição 11'),
    ('Amanda', 'Fernandes', '98765432109', '1994-11-08', 'Brasil', '34567890', 'SP', 'Descrição 12'),
    ('André', 'Ramos', '23456789098', '1983-04-03', 'Brasil', '45678901', 'RJ', 'Descrição 13'),
    ('Camila', 'Costa', '76543210987', '1990-02-15', 'Brasil', '56789012', 'RS', 'Descrição 14'),
    ('Paulo', 'Vieira', '32109876543', '1989-06-27', 'Brasil', '67890123', 'PR', 'Descrição 15'),
    ('Cristina', 'Silveira', '65432109876', '1987-01-19', 'Brasil', '78901234', 'SC', 'Descrição 16'),
    ('Marcos', 'Mendes', '10987654321', '1992-09-22', 'Brasil', '89012345', 'BA', 'Descrição 17'),
    ('Carolina', 'Pereira', '98765432108', '1984-08-12', 'Brasil', '90123456', 'CE', 'Descrição 18'),
    ('Rafael', 'Santos', '12309876543', '1991-12-05', 'Brasil', '01234567', 'GO', 'Descrição 19'),
    ('Patrícia', 'Lima', '43210987654', '1985-03-30', 'Brasil', '12345678', 'PE', 'Descrição 20')
;

INSERT INTO Conta (email, senha, id_candidato)
VALUES
    ('joao@example.com', 'senha123', 1),
    ('maria@example.com', 'senha456', 2),
    ('pedro@example.com', 'senha789', 3),
    ('ana@example.com', 'senha012', 4),
    ('carlos@example.com', 'senha345', 5),
    ('sandra@example.com', 'senha678', 6),
    ('luiz@example.com', 'senha901', 7),
    ('fernanda@example.com', 'senha234', 8),
    ('ricardo@example.com', 'senha567', 9),
    ('juliana@example.com', 'senha890', 10),
    ('jose@example.com', 'senha111', 11),
    ('amanda@example.com', 'senha222', 12),
    ('andre@example.com', 'senha333', 13),
    ('camila@example.com', 'senha444', 14),
    ('paulo@example.com', 'senha555', 15),
    ('cristina@example.com', 'senha666', 16),
    ('marcos@example.com', 'senha777', 17),
    ('carolina@example.com', 'senha888', 18),
    ('rafael@example.com', 'senha999', 19),
    ('patricia@example.com', 'senha000', 20)
;

INSERT INTO Candidato_Competencia (id_candidato, id_competencia)
VALUES
    (1, 7), (1, 15), (1, 23), (1, 32), (1, 12),
    (2, 42), (2, 19), (2, 5), (2, 28), (2, 36),
    (3, 11), (3, 30), (3, 8), (3, 47), (3, 14),
    (4, 6), (4, 18), (4, 25), (4, 39), (4, 50),
    (5, 31), (5, 9), (5, 46), (5, 16), (5, 33),
    (6, 20), (6, 2), (6, 44), (6, 29), (6, 38),
    (7, 45), (7, 10), (7, 21), (7, 13), (7, 24),
    (8, 35), (8, 26), (8, 4), (8, 48), (8, 17),
    (9, 37), (9, 22), (9, 1), (9, 27), (9, 49),
    (10, 3), (10, 34), (10, 40), (10, 43), (10, 41),
    (11, 7), (11, 15), (11, 23), (11, 32), (11, 12),
    (12, 42), (12, 19), (12, 5), (12, 28), (12, 36),
    (13, 11), (13, 30), (13, 8), (13, 47), (13, 14),
    (14, 6), (14, 18), (14, 25), (14, 39), (14, 50),
    (15, 31), (15, 9), (15, 46), (15, 16), (15, 33),
    (16, 20), (16, 2), (16, 44), (16, 29), (16, 38),
    (17, 45), (17, 10), (17, 21), (17, 13), (17, 24),
    (18, 35), (18, 26), (18, 4), (18, 48), (18, 17),
    (19, 37), (19, 22), (19, 1), (19, 27), (19, 49),
    (20, 3), (20, 34), (20, 40), (20, 43), (20, 41)
;

INSERT INTO Empresa (nome, cnpj, descricao, pais, cep)
VALUES
	('Tech Solutions Ltda', '12345678000100', 'Uma empresa de tecnologia', 'Brasil', '12345678'),
	('Consultoria Brasil S.A.', '98765432000101', 'Uma empresa de consultoria', 'Brasil', '54321012'),
	('Serviços de Engenharia Ltda', '24681357000102', 'Uma empresa de engenharia', 'Brasil', '87654321'),
	('Indústria Alimentícia S.A.', '13579246000103', 'Uma empresa de alimentos', 'Brasil', '32106543'),
	('Consultoria Financeira Ltda', '11223344000104', 'Uma empresa de consultoria financeira', 'Brasil', '98765432'),
	('Indústria Automobilística S.A.', '99887766000105', 'Uma empresa automobilística', 'Brasil', '76543210'),
	('Tecnologia Avançada Ltda', '11223378000106', 'Uma empresa de tecnologia avançada', 'Brasil', '67890123'),
	('Serviços de Saúde S.A.', '54321098700107', 'Uma empresa de serviços de saúde', 'Brasil', '87654321'),
	('Consultoria Internacional Ltda', '76543210900108', 'Uma empresa de consultoria internacional', 'Brasil', '23456789'),
	('Indústria de Eletrônicos S.A.', '54321098760109', 'Uma empresa de eletrônicos', 'Brasil', '98761234')
;

INSERT INTO Conta (email, senha, id_empresa)
VALUES
	('contato@techsolutions.com', 'senhadificil', 1),
	('contato@consultoriabrasil.com', 'strongpassword', 2),
	('contato@engenheiros.com', 'engenharia123', 3),
	('contato@alimentosbr.com', 'food123', 4),
	('contato@consultoriafinanceira.com', 'finance123', 5),
	('contato@industriaautomobilistica.com', 'auto123', 6),
	('contato@tecnologiaavancada.com', 'tech123', 7),
	('contato@servicosdesaude.com', 'saude123', 8),
	('contato@consultoriainternacional.com', 'inter123', 9),
	('contato@eletronicosbr.com', 'eletronica123', 10)
;

INSERT INTO Vaga (nome, descricao, estado, cidade, id_empresa)
VALUES
    ('Desenvolvedor Web', 'Desenvolvimento de aplicativos da web', 'São Paulo', 'São Paulo', 1),
    ('Engenheiro de Software', 'Design e desenvolvimento de software', 'Rio de Janeiro', 'Rio de Janeiro', 3),
    ('Analista de Dados', 'Análise de dados e geração de relatórios', 'Minas Gerais', 'Belo Horizonte', 5),
    ('Designer Gráfico', 'Criação de elementos visuais', 'São Paulo', 'São Paulo', 7),
    ('Gerente de Projetos', 'Gestão de projetos de TI', 'Rio de Janeiro', 'Rio de Janeiro', 9),
    ('Analista de Marketing', 'Estratégias de marketing digital', 'Bahia', 'Salvador', 2),
    ('Engenheiro Eletricista', 'Projetos de sistemas elétricos', 'Minas Gerais', 'Belo Horizonte', 4),
    ('Técnico de Suporte', 'Suporte técnico ao usuário', 'São Paulo', 'São Paulo', 6),
    ('Contador', 'Gestão contábil e financeira', 'Rio de Janeiro', 'Rio de Janeiro', 8),
    ('Engenheiro Mecânico', 'Projeto de máquinas e equipamentos', 'Bahia', 'Salvador', 10),
    ('Arquiteto de Software', 'Design de arquitetura de software', 'São Paulo', 'São Paulo', 1),
    ('Analista de Sistemas', 'Desenvolvimento de sistemas', 'Rio de Janeiro', 'Rio de Janeiro', 3),
    ('Consultor de Negócios', 'Consultoria empresarial', 'Minas Gerais', 'Belo Horizonte', 5),
    ('Designer de Interiores', 'Design de espaços internos', 'São Paulo', 'São Paulo', 7),
    ('Gerente de Recursos Humanos', 'Gestão de RH', 'Rio de Janeiro', 'Rio de Janeiro', 9),
    ('Analista de Recursos Humanos', 'Recrutamento e seleção', 'Bahia', 'Salvador', 2),
    ('Engenheiro Civil', 'Projetos de construção', 'Minas Gerais', 'Belo Horizonte', 4),
    ('Analista de Qualidade', 'Controle de qualidade', 'São Paulo', 'São Paulo', 6),
    ('Advogado', 'Assessoria jurídica', 'Rio de Janeiro', 'Rio de Janeiro', 8),
    ('Desenvolvedor Mobile', 'Desenvolvimento de aplicativos móveis', 'Bahia', 'Salvador', 10),
    ('Analista de Marketing', 'Estratégias de marketing digital', 'Bahia', 'Salvador', 2),
    ('Engenheiro Eletricista', 'Projetos de sistemas elétricos', 'Minas Gerais', 'Belo Horizonte', 4),
    ('Técnico de Suporte', 'Suporte técnico ao usuário', 'São Paulo', 'São Paulo', 6),
    ('Contador', 'Gestão contábil e financeira', 'Rio de Janeiro', 'Rio de Janeiro', 8),
    ('Engenheiro Mecânico', 'Projeto de máquinas e equipamentos', 'Bahia', 'Salvador', 10),
    ('Arquiteto de Software', 'Design de arquitetura de software', 'São Paulo', 'São Paulo', 1),
    ('Analista de Sistemas', 'Desenvolvimento de sistemas', 'Rio de Janeiro', 'Rio de Janeiro', 3),
    ('Consultor de Negócios', 'Consultoria empresarial', 'Minas Gerais', 'Belo Horizonte', 5),
    ('Designer de Interiores', 'Design de espaços internos', 'São Paulo', 'São Paulo', 7),
    ('Gerente de Recursos Humanos', 'Gestão de RH', 'Rio de Janeiro', 'Rio de Janeiro', 9),
    ('Analista de Recursos Humanos', 'Recrutamento e seleção', 'Bahia', 'Salvador', 2),
    ('Engenheiro Civil', 'Projetos de construção', 'Minas Gerais', 'Belo Horizonte', 4),
    ('Analista de Qualidade', 'Controle de qualidade', 'São Paulo', 'São Paulo', 6),
    ('Advogado', 'Assessoria jurídica', 'Rio de Janeiro', 'Rio de Janeiro', 8),
    ('Desenvolvedor Mobile', 'Desenvolvimento de aplicativos móveis', 'Bahia', 'Salvador', 10)
;

INSERT INTO Vaga_Competencia (id_vaga, id_competencia)
VALUES
	(1, 7), (1, 20), (1, 13), (1, 42), (1, 1),
    (2, 28), (2, 12), (2, 50), (2, 6), (2, 16),
    (3, 25), (3, 4), (3, 39), (3, 9), (3, 30),
    (4, 35), (4, 22), (4, 8), (4, 47), (4, 3),
    (5, 11), (5, 15), (5, 31), (5, 36), (5, 29),
    (6, 2), (6, 38), (6, 24), (6, 10), (6, 43),
    (7, 46), (7, 27), (7, 33), (7, 21), (7, 44),
    (8, 19), (8, 14), (8, 40), (8, 5), (8, 34),
    (9, 26), (9, 18), (9, 45), (9, 23), (9, 37),
    (10, 32), (10, 41), (10, 17), (10, 48), (10, 7),
    (11, 27), (11, 8), (11, 21), (11, 35), (11, 43),
    (12, 9), (12, 26), (12, 44), (12, 3), (12, 18),
    (13, 49), (13, 33), (13, 14), (13, 30), (13, 19),
    (14, 45), (14, 10), (14, 36), (14, 24), (14, 13),
    (15, 4), (15, 20), (15, 11), (15, 39), (15, 5),
    (16, 32), (16, 46), (16, 17), (16, 2), (16, 7),
    (17, 48), (17, 25), (17, 38), (17, 6), (17, 31),
    (18, 1), (18, 42), (18, 16), (18, 34), (18, 23),
    (19, 15), (19, 37), (19, 40), (19, 28), (19, 50),
    (20, 12), (20, 22), (20, 41), (20, 47), (20, 29),
    (21, 19), (21, 11), (21, 47), (21, 37), (21, 32),
    (22, 36), (22, 17), (22, 8), (22, 26), (22, 43),
    (23, 2), (23, 31), (23, 6), (23, 44), (23, 21),
    (24, 24), (24, 13), (24, 5), (24, 39), (24, 28),
    (25, 45), (25, 9), (25, 35), (25, 3), (25, 10),
    (26, 12), (26, 38), (26, 20), (26, 22), (26, 30),
    (27, 48), (27, 25), (27, 41), (27, 27), (27, 15),
    (28, 46), (28, 23), (28, 14), (28, 50), (28, 1),
    (29, 17), (29, 37), (29, 2), (29, 48), (29, 32),
    (30, 16), (30, 7), (30, 12), (30, 19), (30, 33),
    (31, 43), (31, 20), (31, 11), (31, 35), (31, 9),
    (32, 21), (32, 5), (32, 24), (32, 26), (32, 3),
    (33, 13), (33, 39), (33, 46), (33, 28), (33, 22),
    (34, 44), (34, 4), (34, 31), (34, 50), (34, 10),
    (35, 8), (35, 36), (35, 15), (35, 1), (35, 27)
;

INSERT INTO Match (data_curtida_candidato, data_curtida_vaga, id_candidato, id_vaga)
VALUES
    ('2023-09-01', NULL, 1, 11),
    ('2023-09-02', NULL, 2, 12),
    ('2023-09-03', NULL, 3, 13),
    ('2023-09-04', '2023-09-05', 4, 14),
    (NULL, '2023-09-06', 5, 15),
    ('2023-09-06', NULL, 6, 16),
    ('2023-09-07', NULL, 7, 17),
    ('2023-09-08', '2023-09-09', 8, 18),
    ('2023-09-09', NULL, 9, 19),
    ('2023-09-10', NULL, 10, 20),
	(NULL, '2023-09-12', 1, 12),
    ('2023-09-12', '2023-09-13', 2, 13),
    ('2023-09-13', '2023-09-14', 3, 14),
    (NULL, '2023-09-15', 4, 15),
    (NULL, '2023-09-16', 5, 16),
    ('2023-09-16', NULL, 6, 17),
    ('2023-09-17', NULL, 7, 18),
    ('2023-09-18', '2023-09-19', 8, 19),
    ('2023-09-19', NULL, 9, 20),
    ('2023-09-20', '2023-09-21', 10, 11)
;