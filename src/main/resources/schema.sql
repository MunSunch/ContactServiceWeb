create schema if not exists contact_service;

create table if not exists contact_service.contacts(
    id serial primary key,
    firstname text not null,
    lastname text not null,
    email text not null,
    phone text not null
);