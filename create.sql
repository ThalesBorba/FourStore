
    create table clients (
       cpf varchar(255) not null,
        name varchar(255) not null,
        paymentData varchar(255),
        paymentMethod integer not null,
        primary key (cpf)
    ) engine=InnoDB;

    create table products (
       sku varchar(255) not null,
        brand varchar(255),
        buyPrice double precision not null,
        category varchar(255),
        color varchar(255),
        department varchar(255),
        description varchar(255) not null,
        season varchar(255),
        sellPrice double precision not null,
        size varchar(255),
        type varchar(255),
        primary key (sku)
    ) engine=InnoDB;

    create table stocks (
       id integer not null auto_increment,
        quantity integer,
        product_id varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table transactions (
       id bigint not null auto_increment,
        profit double precision,
        client_id varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table stocks 
       add constraint FKff7be959jyco0iukc1dcjj9qm 
       foreign key (product_id) 
       references products (sku);

    alter table transactions 
       add constraint FKjp6w7dmqrj0h9vykk2pbtik2 
       foreign key (client_id) 
       references clients (cpf);
