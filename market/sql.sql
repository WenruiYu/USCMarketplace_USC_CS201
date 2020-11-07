use market;

drop table if exists user;
create table if not exists user
(
    id         integer primary key auto_increment,
    username   varchar(255) not null,
    password   varchar(255) not null,

    real_name  varchar(512) not null,
    email      varchar(512) not null,
    usc_id     varchar(512) not null,
    mobile     varchar(512) not null,
    gmt_update bigint not null
)
    character set 'utf8mb4';


drop table if exists auth;
create table if not exists auth
(
    username varchar(255)  not null primary key,
    token    varchar(1024) not null
)
    character set 'utf8mb4';

drop table if exists listing;
create table if not exists listing
(
    listingID   integer primary key auto_increment,
    ownerID     integer not null,
    itemName    varchar(80) not null,
    itemDescription varchar(500),
    quantity    integer not null,
    quality     enum('Brand New', 'New-Open Box', 'Like New', 'Used-Very Good', 'Used-Good', 'Used-Acceptable') not null,
    image       varchar(100),
    pickupLoc   enum('Tommy Trojan', 'Hecuba', 'Lyon Center', 'SAL', 'USC Bookstore', 'Leavey') not null,
    sold        boolean not null,
    held        boolean not null,
    FOREIGN KEY (ownerID) REFERENCES user(id)
)
