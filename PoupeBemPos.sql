drop database PoupeBemPos;
create database PoupeBemPos;
use PoupeBemPos;

create table cliente(
cod_cliente int auto_increment,
nome varchar(50),
CPF char(11),
sexo char(1),
endereco varchar(50),
telefone char(15),
email varchar(100),
primary key(cod_cliente));

insert into cliente values(1,'Bill Clinton','12999786543','M','Av. Paulista, n. 100', '11999786543',
 'william@gmail.com'),
 (2,'Trump', '13999786534', 'M','Av. Das Américas, n. 200', '11999186543', 'trump@gmail.com'),
 (3,'Richard', '13999786124', 'M','Av. Rondon Pacheco, n. 20', '11992316543', 'richard@gmail.com'),
 (4,'Cleiton', '13999786444', 'M','R. Joaquim Silva Carvalho, n. 10', '22999186543', 'cleiton@gmail.com'),
 (5,'Joao', '13999781114', 'M','R. Das Palmeira, n. 29', '119991863123', 'jaum@gmail.com'),
 (6,'Michael', '13999782224', 'M','Av. Das Américas, n. 21', '11999236543', 'michael@gmail.com'),
 (7,'Jorjão Bush', '13999782224', 'M','Av. Das Américas, n. 21', '11999236543', 'bush@gmail.com');

create table conta_corrente(
cod_conta int auto_increment,
dt_hora_abertura date,
saldo numeric(9,2),
cod_cliente int,
primary key(cod_conta),
foreign key(cod_cliente)references cliente(cod_cliente));

insert into conta_corrente values (1,current_date(),50,1);
insert into conta_corrente values (2,current_date(),200,2);
insert into conta_corrente values (3,current_date(),100,3);
insert into conta_corrente values (4,current_date(),5600,4);
insert into conta_corrente values (5,current_date(),4500,5);
insert into conta_corrente values (6,current_date(),3300,6);
insert into conta_corrente values (7,current_date(),3300,7);


create table Registro_Saque(
cod_saque int auto_increment,
cod_conta int,
dt_saque date,
valor_saque numeric(9,2),
primary key(cod_saque),
foreign key(cod_conta)references conta_corrente(cod_conta));

insert into Registro_Saque values
(1, 1,current_date(), 10),
(2, 1,current_date(), 20),
(3, 2,current_date(), 100),
(4, 3,current_date(), 100),
(5, 4,current_date(), 1000),
(6, 4,current_date(), 1020);


 create table Registro_Deposito(
cod_deposito int auto_increment,
cod_conta int,
dt_deposito date,
valor_deposito numeric(9,2),
primary key(cod_deposito),
foreign key(cod_conta)references conta_corrente(cod_conta));

insert into Registro_Deposito values
(1, 1,current_date(), 1000),
(2, 1,current_date(), 2200),
(3, 2,current_date(), 100),
(4, 2,current_date(), 20),
(5, 3,current_date(), 1230),
(6, 4,current_date(), 3400);


select * from conta_corrente;
select * from cliente;

(
select c.nome, co.cod_conta, concat("Saque: ", cast(sum(re.valor_saque) as char(10)))
from cliente c
inner join conta_corrente co
	on co.cod_conta = c.cod_cliente
inner join Registro_Saque re
	on re.cod_conta = co.cod_conta
group by c.nome, co.cod_conta
)
union
(select c.nome, co.cod_conta, concat("Deposito: ",cast(sum(ro.valor_deposito) as char(10)))
from cliente c
inner join conta_corrente co
	on co.cod_conta = c.cod_cliente
inner join Registro_Deposito ro
	on ro.cod_conta = co.cod_conta
group by c.nome, co.cod_conta
);


select co.cod_conta, c.nome, c.telefone, c.email
from conta_corrente co
inner join cliente c
	on co.cod_conta = c.cod_cliente
where co.cod_conta 
not in (select cod_conta from Registro_Saque where dt_saque > date_add(now(), interval -6 month)
union all select cod_conta from Registro_Deposito where dt_deposito > date_add(now(), interval -6 month));


(select co.cod_conta, year(re.dt_saque), month(re.dt_saque), concat("Saque: ", cast(sum(re.valor_saque) as char(10)))
from cliente c
inner join conta_corrente co
	on co.cod_conta = c.cod_cliente
inner join Registro_Saque re
	on re.cod_conta = co.cod_conta
group by co.cod_conta)
union all
(
select co.cod_conta, year(ro.dt_deposito), month(ro.dt_deposito), concat("Deposito: ", cast(sum(ro.valor_dep) as char(10)))
from cliente c
inner join conta_corrente co
	on co.cod_conta = c.cod_cliente
inner join Registro_Deposito ro
	on ro.cod_conta = co.cod_conta
group by co.cod_conta);