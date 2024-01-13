
create table tpp_ref_product_class(
    id serial primary key,
    value varchar(30),
    gbl_code varchar(30),
    gbl_name varchar(100),
    product_row_code varchar(30),
    product_row_name varchar(100),
    subclass_code varchar(30),
    subclass_name varchar(100)
);
create unique index idx_product_class_value on tpp_ref_product_class(value);

--======================================
create table tpp_ref_account_type (
    id serial primary key,
    value varchar(30)
);
create unique index idx_account_type_value on tpp_ref_account_type(value);

--======================================
create table tpp_ref_product_register_type (
    id serial primary key,
    value varchar(30),
    register_type_name varchar(100),
    product_class_code varchar(30),
    account_type varchar(30)
);
create unique index idx_register_type_value on tpp_ref_product_register_type(value);
alter table tpp_ref_product_register_type
    add constraint fk_register_type_account_type
        foreign key (account_type)
            references tpp_ref_account_type (value);
alter table tpp_ref_product_register_type
    add constraint fk_register_type_product_class
        foreign key (product_class_code)
            references tpp_ref_product_class (value);

--======================================
create table tpp_client (
    id serial primary key,
    mdm_code varchar(30),
    name varchar(200),
    kpp varchar(20)
);
create unique index idx_client_mdm_code on tpp_client(mdm_code);

--======================================
create table tpp_currency (
    code varchar(3) primary key,
    name varchar(100)
);

--======================================
create table tpp_branch (
    id serial primary key,
    code varchar(5),
    name varchar(100)
);
create unique index idx_branch_code on tpp_branch(code);

--======================================
create table tpp_product (
    id serial primary key,
    agreement_id serial,
    parent_product_id integer,
    product_code_id integer,
    client_id integer,
    type varchar(30),
    number varchar(30),
    priority integer,
    date_of_conclusion date,
    start_date_time date,
    end_date_time date,
    days smallint,
    penalty_rate decimal(7, 4),
    nso decimal(30, 10),
    threshold_amount decimal(30, 10),
    interest_rate_type varchar(30),
    tax_rate decimal(7, 4),
    reason_close varchar(2000),
    state varchar(30),
    currency varchar(3),
    branch integer
);
create unique index idx_product_agreement_id on tpp_product(agreement_id);
alter table tpp_product
    add constraint fk_parent_product
        foreign key (parent_product_id)
            references tpp_product (id);
alter table tpp_product
    add constraint fk_product_product_class
        foreign key (product_code_id)
            references tpp_ref_product_class (id);
alter table tpp_product
    add constraint fk_product_client_ref
        foreign key (client_id)
            references tpp_client (id);
alter table tpp_product
    add constraint fk_product_currency
        foreign key (currency)
            references tpp_currency (code);
alter table tpp_product
    add constraint fk_product_branch
        foreign key (branch)
            references tpp_branch (id);


--======================================
create table tpp_product_register(
    id serial primary key,
    product_id integer,
    register_type integer,
    account_num varchar(25),
    currency varchar(3),
    state varchar(10)
);
alter table tpp_product_register
    add constraint fk_register_product
        foreign key (product_id)
            references tpp_product (id);
alter table tpp_product_register
    add constraint fk_product_register_type
        foreign key (register_type)
            references tpp_ref_product_register_type (id);
alter table tpp_product_register
    add constraint fk_register_currency
        foreign key (currency)
            references tpp_currency (code);

--======================================
create table agreements(
    id serial primary key,
    agreement_id integer,
    number varchar(30)
);
alter table agreements
    add constraint fk_product
        foreign key (agreement_id)
            references tpp_product (agreement_id);

--======================================
create table account_pool(
    id serial primary key,
    branch_code varchar(5),
    currency_code varchar(3),
    mdm_code varchar(30),
    priority_code varchar(2),
    register_type_code varchar(30),
    account_coll serial
);
create unique index idx_account_coll on account_pool(account_coll);
alter table account_pool
    add constraint fk_branch_code
        foreign key (branch_code)
            references tpp_branch (code);
alter table account_pool
    add constraint fk_currency_code
        foreign key (currency_code)
            references tpp_currency (code);
alter table account_pool
    add constraint fk_mdm_code
        foreign key (mdm_code)
            references tpp_client (mdm_code);
alter table account_pool
    add constraint fk_register_type_code
        foreign key (register_type_code)
            references tpp_ref_product_register_type (value);

--======================================
create table account(
    account_coll integer,
    account varchar(25)
);
alter table account
    add constraint fk_account_collection
        foreign key (account_coll)
            references account_pool (account_coll);


--======================================
--======================================
insert into tpp_ref_account_type (value) values ('Клиентский');
insert into tpp_ref_account_type (value) values ('Внутрибанковский');

insert into tpp_ref_product_class (value, gbl_code, gbl_name, product_row_code, product_row_name, subclass_code, subclass_name)
    values ('03.012.002', '03', 'Розничный бизнес', '012', 'Драг. металлы', '002', 'Хранение');
insert into tpp_ref_product_class (value, gbl_code, gbl_name, product_row_code, product_row_name, subclass_code, subclass_name)
    values ('02.001.005', '02', 'Розничный бизнес', '001', 'Сырье', '005', 'Продажа');

insert into tpp_ref_product_register_type (value, register_type_name, product_class_code, account_type)
    values ('03.012.002_47533_ComSoLd', 'Хранение ДМ.', '03.012.002', 'Клиентский');
insert into tpp_ref_product_register_type (value, register_type_name, product_class_code, account_type)
    values ('02.001.005_45343_CoDowFF', 'Серебро. Выкуп.', '02.001.005', 'Клиентский');

insert into tpp_client (mdm_code, name, kpp)
    values ('cl001','"ООО" Бабкоруб', '15130417');

insert into tpp_branch (code, name)
    values ('001', 'Головной филиал в г.Москва');

insert into tpp_currency (code, name) values
    ('A98', 'Золото'),
    ('RUB', 'Рубль'),
    ('USD', 'Доллар'),
    ('EUR', 'Евро'),
    ('CYN', 'Юань');

DO $$
declare
    coll integer;
begin
    insert into account_pool (branch_code, currency_code, mdm_code, priority_code, register_type_code)
        values ('001', 'A98', 'cl001', '00', '03.012.002_47533_ComSoLd')
        returning account_coll
        into coll;
    insert into account (account_coll, account) values
        (coll, '475335516415314841861'),
        (coll, '4753321651354151'),
        (coll, '4753352543276345');
    insert into account_pool (branch_code, currency_code, mdm_code, priority_code, register_type_code)
        values ('001', 'A98', 'cl001', '00', '02.001.005_45343_CoDowFF')
        returning account_coll
        into coll;
    insert into account (account_coll, account) values
         (coll, '453432352436453276'),
         (coll, '45343221651354151'),
         (coll, '4534352543276345');
end;
$$;
