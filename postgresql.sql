/**********************
* CRIAÇÃO DAS TABELAS *
***********************/

CREATE TABLE Usuario (
	id SERIAL,
	nome VARCHAR(128),
	cpf VARCHAR(11),
	senha VARCHAR(32),
	administrador BOOLEAN,

	PRIMARY KEY (id),
	UNIQUE (cpf)
);

CREATE TABLE Produto (
	id SERIAL,
	descricao VARCHAR(1024),
	valor DECIMAL,

	PRIMARY KEY (id)
);

CREATE TABLE AlterarSenhaToken (
	idUsuario INT,
	token VARCHAR(128)
);

ALTER TABLE Usuario ADD COLUMN email VARCHAR(128);

/*COLOCADO AQUI DIA 16/11/2018*/
ALTER TABLE Produto ADD COLUMN quantidade INT DEFAULT 0;

/**********
* INSERTS *
**********/

INSERT INTO Usuario (nome, cpf, senha, administrador) VALUES ('Renata Soares Pereira', '09683644970', '123', true );


INSERT INTO Produto (descricao, valor) VALUES ('Conversor USB para Serial', 10.90 );
INSERT INTO Produto (descricao, valor) VALUES ('SSD 120GB Sandisk SSD Plus 310MB/530MB/s 20X', 500.90 );
INSERT INTO Produto (descricao, valor) VALUES ('Fonte ATX 350W reais C3Tech PS-350 24 pinos c/ chave', 50.99 );
INSERT INTO Produto (descricao, valor) VALUES ('Teclado e mouse com fio Logitech Desktop MK120', 59.00 );
INSERT INTO Produto (descricao, valor) VALUES ('Placa de rede D-Link', 108.90 );
INSERT INTO Produto (descricao, valor) VALUES ('Bateria chumbo-acido Unipower UP1270E, 12V, 7Ah, F187', 200.00 );
INSERT INTO Produto (descricao, valor) VALUES ('Álcool isopropílico puro, Isopropanol Implastec, 110ml', 10.90 );