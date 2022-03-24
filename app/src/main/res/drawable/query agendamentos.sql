-- BancoSiteApp21*
use elsongabrieldb1;

/*create table Agendamentos(
id int auto_increment primary key,
nome_cliente nvarchar(100) not null,
telefone_cliente1 nvarchar(15) not null,
telefone_cliente2 nvarchar(15) null,
endereco_cliente nvarchar(200) null,
ponto_ref_endereco nvarchar(100) null,
email_cliente nvarchar(100) null,
data_cadastro datetime not null default now(),
data_agendada datetime not null,
confirmado tinyint not null default 0,
ativo tinyint not null default 1
) ENGINE = innodb;
-- 2015-06-11 13:25:59

create table Usuarios(
id int primary key,
login nvarchar(30) not null,
senha nvarchar(100) not null,
nome nvarchar(50) not null,
permissao int not null,
email nvarchar(100) null,
telefone nvarchar(15) not null,
data_cadastro datetime not null,
ativo tinyint not null default 1
) ENGINE = innodb;*/

/*INSERT INTO Usuarios(id, login, senha, nome, permissao, telefone, data_cadastro)
VALUES (1995, 'polly', md5('polly'), 'Pollyanna', 1, '(81)99916-7540', now());
INSERT INTO Usuarios(id,login, senha, nome, permissao,telefone,data_cadastro)
VALUES (300, 'teste', md5('elson1234567890'), 'TESTE T ES TEET', 1, '123', now());*/
-- 2015-08-05 17:10:00

-- select * from Agendamentos;
 select * from Usuarios;