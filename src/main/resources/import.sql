
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

insert into produto (id, ativo , descricao, nome, preco, restaurante_id) values (1, true, "delicioso espeto de carne exótica", "Espeto de filé miau", 5.20, 3);
insert into produto (id, ativo , descricao, nome, preco, restaurante_id) values (2, true, "coqua", "bebida gourmet", 74.20, 2);


insert into forma_pagamento (descricao) values ('Cartão de Crédito');
insert into forma_pagamento (descricao) values ('Dinheiro');

insert into permissao (nome, descricao) values ('Pode fazer X', 'pode fazer X detalhado');
insert into permissao (nome, descricao) values ('Pode fazer Y', 'pode fazer Y detalhado');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (2, 1), (3, 2);

