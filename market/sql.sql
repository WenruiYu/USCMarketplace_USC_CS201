use market;

drop table if exists user;
create table if not exists user
(
    id         integer primary key auto_increment,
    username   varchar(255) not null,
    password   varchar(255) not null,
    tel        varchar(255) not null,
    gmt_update timestamp    not null default current_timestamp()
)
    character set 'utf8mb4';


drop table if exists auth;
create table if not exists auth
(
    username varchar(255)  not null primary key ,
    token    varchar(1024) not null
)
    character set  'utf8mb4';
