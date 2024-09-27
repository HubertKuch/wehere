create table if not exists account
(
    id       varchar(36) not null,
    username text        not null,
    password text        not null,
    gender   text        not null,
    hashtag  text        not null default ''
);

create table if not exists avatar
(
    account_id        varchar(36) not null,
    avatar            bytea       not null,
    thumbnail_32_32   bytea       not null,
    thumbnail_126_126 bytea       not null
);

create table if not exists friendship
(
    id               varchar(36) not null,
    first_friend_id  varchar(36) not null,
    second_friend_id varchar(36) not null,
    created_at       timestamp            default current_timestamp,
    status           text        not null default 'PENDING',
    unique (first_friend_id, second_friend_id)
);
