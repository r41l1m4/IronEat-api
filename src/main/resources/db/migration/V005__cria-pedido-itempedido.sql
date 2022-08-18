
create table pedido (
    id bigint not null auto_increment,
    subtotal decimal(19,2) not null,
    taxa_frete decimal(19,2) not null,
    valor_total decimal(19,2) not null,
    data_criacao datetime not null,
    data_confirmacao datetime,
    data_cancelamento datetime,
    data_entrega datetime,
    status_pedido varchar(10) not null,
    endereco_bairro varchar(60) not null,
    endereco_cep varchar(10) not null,
    endereco_complemento varchar(100),
    endereco_logradouro varchar(100) not null,
    endereco_numero int not null,

    forma_pagamento_id bigint not null,
    restaurante_id bigint not null,
    cliente_id bigint not null,
    endereco_cidade_id bigint not null,

    primary key (id)
) engine=InnoDB charset=utf8;

alter table pedido add constraint fk_pedido_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento (id);
alter table pedido add constraint fk_pedido_restaurante foreign key (restaurante_id) references restaurante (id);
alter table pedido add constraint fk_pedido_cliente foreign key (cliente_id) references usuario (id);
alter table pedido add constraint fk_pedido_endereco_cidade foreign key (endereco_cidade_id) references cidade (id);

create table item_pedido (
    id bigint not null auto_increment,
    quantidade bigint not null,
    preco_unitario decimal(19,2) not null,
    preco_total decimal(19,2) not null,
    observacao varchar(100),

    produto_id bigint not null,
    pedido_id bigint not null,

    primary key (id)
) engine=InnoDB charset=utf8;

alter table item_pedido add constraint fk_item_pedido_produto foreign key (produto_id) references produto (id);
alter table item_pedido add constraint fk_item_pedido_pedido foreign key (pedido_id) references pedido (id);