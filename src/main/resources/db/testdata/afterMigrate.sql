set foreign_key_checks=0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;
delete from pedido;
delete from item_pedido;

set foreign_key_checks=1;

alter table cidade auto_increment=1;
alter table cozinha auto_increment=1;
alter table estado auto_increment=1;
alter table forma_pagamento auto_increment=1;
alter table grupo auto_increment=1;
alter table permissao auto_increment=1;
alter table produto auto_increment=1;
alter table restaurante auto_increment=1;
alter table usuario auto_increment=1;
alter table pedido auto_increment=1;
alter table item_pedido auto_increment=1;

insert into estado (id, nome) values (1, 'Alagoas');
insert into estado (id, nome) values (2, 'Pernambuco');
insert into estado (id, nome) values (3, 'Paraiba');

insert into cidade (id, nome, estado_id) values (1, 'Craibas', 1);
insert into cidade (id, nome, estado_id) values (2, 'Recife', 2);
insert into cidade (id, nome, estado_id) values (3, 'Quixeramobim', 2);

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Italiana');

insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro) values ('Fim do Universo', 136.50, 2, utc_timestamp, utc_timestamp, 2, "57320-000", "Rua do Fim dos Tempos", "-1", "Não tem erro", "Endis Mundis");
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('Da Esquina', 2.74, 3, utc_timestamp, utc_timestamp);
insert into restaurante (nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values ('X-Ratão', 5.00, 1, utc_timestamp, utc_timestamp);

insert into forma_pagamento (descricao) values ('Cartão de Crédito');
insert into forma_pagamento (descricao) values ('Dinheiro');

insert into permissao (id, nome, descricao) values (1, "CONSULTAR_COZINHAS", "Permite consultar cozinhas");
insert into permissao (id, nome, descricao) values (2, "EDITAR_COZINHAS", "Permite editar cozinhas");

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (2, 1), (3, 2);

insert into produto (ativo , descricao, nome, preco, restaurante_id) values (true, "delicioso espeto de carne exótica", "Espeto de filé miau", 5.20, 3);
insert into produto (ativo , descricao, nome, preco, restaurante_id) values (true, "coqua", "bebida gourmet", 74.20, 2);
