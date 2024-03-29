-- noinspection SqlNoDataSourceInspectionForFile

create table forma_pagamento (
    id bigint not null auto_increment,
    descricao varchar(60) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table grupo (
    id bigint not null auto_increment,
    nome varchar(100) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table grupo_permissao (
    grupo_id bigint not null,
    permissao_id bigint not null
) engine=InnoDB default charset=utf8;

create table permissao (
    id bigint not null auto_increment,
    descricao varchar(100) not null,
    nome varchar(60) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table produto (
    id bigint not null auto_increment,
    ativo bit not null,
    descricao varchar(100),
    nome varchar(60),
    preco decimal(19,2),
    restaurante_id bigint,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurante (
    id bigint not null auto_increment,
    data_atualizacao datetime not null,
    data_cadastro datetime not null,
    endereco_bairro varchar(100),
    endereco_cep varchar(9),
    endereco_complemento varchar(100),
    endereco_logradouro varchar(100),
    endereco_numero varchar(10),
    nome varchar(60) not null,
    taxa_frete decimal(19,2) not null,
    cozinha_id bigint not null,
    endereco_cidade_id bigint,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table restaurante_forma_pagamento (
    restaurante_id bigint not null,
    forma_pagamento_id bigint not null
) engine=InnoDB default charset=utf8;

create table usuario (
    id bigint not null auto_increment,
    data_cadastro datetime not null,
    email varchar(255) not null,
    nome varchar(60) not null,
    senha varchar(255) not null,

    primary key (id)
) engine=InnoDB default charset=utf8;

create table usuario_grupo (
    usuario_id bigint not null,
    grupo_id bigint not null
) engine=InnoDB default charset=utf8;

alter table grupo_permissao add constraint FKh21kiw0y0hxg6birmdf2ef6vy foreign key (permissao_id) references permissao (id);

alter table grupo_permissao add constraint FKta4si8vh3f4jo3bsslvkscc2m foreign key (grupo_id) references grupo (id);

alter table produto add constraint FKb9jhjyghjcn25guim7q4pt8qx foreign key (restaurante_id) references restaurante (id);

alter table restaurante add constraint FK76grk4roudh659skcgbnanthi foreign key (cozinha_id) references cozinha (id);

alter table restaurante add constraint FKbc0tm7hnvc96d8e7e2ulb05yw foreign key (endereco_cidade_id) references cidade (id);

alter table restaurante_forma_pagamento add constraint FK7aln770m80358y4olr03hyhh2 foreign key (forma_pagamento_id) references forma_pagamento (id);

alter table restaurante_forma_pagamento add constraint FKa30vowfejemkw7whjvr8pryvj foreign key (restaurante_id) references restaurante (id);

alter table usuario_grupo add constraint FKk30suuy31cq5u36m9am4om9ju foreign key (grupo_id) references grupo (id);

alter table usuario_grupo add constraint FKdofo9es0esuiahyw2q467crxw foreign key (usuario_id) references usuario (id);
