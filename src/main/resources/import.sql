insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Italiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Fim do Universo', 136.50, 2);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Da Esquina', 2.74, 3);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('X-Ratão', 5.00, 1);

insert into estado (id, nome) values (1, 'Alagoas');
insert into estado (id, nome) values (2, 'Pernambuco');
insert into estado (id, nome) values (3, 'Paraiba');

insert into cidade (id, nome, estado_id) values (1, 'Craibas', 1);
insert into cidade (id, nome, estado_id) values (2, 'Recife', 2);
insert into cidade (id, nome, estado_id) values (3, 'Quixeramobim', 2);

insert into forma_pagamento (descricao) values ('Cartão de Crédito');
insert into forma_pagamento (descricao) values ('Dinheiro');

insert into permissao (nome, descricao) values ('Pode fazer X', 'pode fazer X detalhado');
insert into permissao (nome, descricao) values ('Pode fazer Y', 'pode fazer Y detalhado');
