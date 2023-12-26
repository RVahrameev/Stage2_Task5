
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
    register_type_start_date date,
    register_type_end_date date,
    account_type varchar(30)
);
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
    fio varchar(200),
    birthday date
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

--======================================
create table tpp_product (
    id serial primary key,
    parent_product_id integer,
    product_code_id integer,
    client_id integer,
    type varchar(30),
    calculation_schedule_id integer,
    calculation_accounting_schedule_id integer,
    calculation_pay_schedule_id integer,
    number varchar(30),
    priority smallint,
    date_of_conclusion date,
    start_date_time date,
    end_date_time date,
    days smallint,
    penalty_rate decimal(7, 4),
    nso decimal(30, 10),
    threshold_amount decimal(30, 10),
    register_type integer,
    interest_rate_type varchar(30),
    tax_rate decimal(7, 4),
    reason_close varchar(2000),
    state varchar(30),
    currency varchar(3),
    branch integer
);
alter table tpp_product
    add constraint fk_parent_product
        foreign key (parent_product_id)
            references tpp_product (id);
alter table tpp_product
    add constraint fk_product_product_class
        foreign key (product_code_id)
            references tpp_ref_product_class (id);
alter table tpp_product
    add constraint fk_product_register_type
        foreign key (register_type)
            references tpp_ref_product_register_type (id);
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
    client integer,
    currency varchar(3),
    branch integer
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
    add constraint fk_register_client_ref
        foreign key (client)
            references tpp_client (id);
alter table tpp_product_register
    add constraint fk_register_currency
        foreign key (currency)
            references tpp_currency (code);
alter table tpp_product_register
    add constraint fk_register_branch
        foreign key (branch)
            references tpp_branch (id);

--======================================
create table agreements(
    id serial primary key,
    product_id integer,
    number varchar(30)
);
alter table agreements
    add constraint fk_product
        foreign key (product_id)
            references tpp_product (id);

--======================================
