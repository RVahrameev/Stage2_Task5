
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
create table tpp_ref_product_class (
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
    add constraint fk_product_class
        foreign key (account_type)
            references tpp_ref_account_type (value);
alter table tpp_ref_product_register_type
    add constraint fk_product_class
        foreign key (product_class_code)
            references tpp_ref_product_class (value);

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
    reason_close varchar2(2000),
    state varchar2(30)
);
alter table tpp_product
    add constraint fk_parent_product
        foreign key (parent_product_id)
            references tpp_product (id);
alter table tpp_product
    add constraint fk_product_class
        foreign key (product_code_id)
            references tpp_ref_product_class (id);
alter table tpp_product
    add constraint fk_product_register_type
        foreign key (register_type)
            references tpp_ref_product_register_type (id);


--======================================
create table agreements(
    id serial primary key,
    product_id integer
);
alter table agreements
    add constraint fk_product
        foreign key (product_id)
            references tpp_product (id);

--======================================
