create table profiles
(
    id      bigserial not null
        constraint profiles_pk
            primary key,
    email   varchar   not null,
    age     integer   not null,
    name    varchar   not null,
    created timestamp
);

alter table profiles
    owner to default_user;

create unique index profiles_email_uindex
    on profiles (email);

create unique index profiles_id_uindex
    on profiles (id);


create table errors
(
    id      bigserial not null
        constraint errors_pk
            primary key,
    message text      not null,
    created timestamp not null
);

alter table errors
    owner to default_user;

create unique index errors_id_uindex
    on errors (id);
