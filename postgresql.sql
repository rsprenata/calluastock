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
	UNIQUE (cpfCnpj)
);