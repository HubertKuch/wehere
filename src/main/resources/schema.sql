create table if not exists account (
    id varchar(36) not null primary key,
    username text not null,
    password text not null
);