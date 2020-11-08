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
    gmt_update bigint       not null
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
    id               integer primary key auto_increment,
    owner_id         integer     not null,
    item_name        varchar(80) not null,
    item_description varchar(500),
    quantity         integer     not null,
    # quality     enum('Brand New', 'New-Open Box', 'Like New', 'Used-Very Good', 'Used-Good', 'Used-Acceptable') not null,
    quality          int         not null,
    image            varchar(1024),
    # pickupLoc   enum('Tommy Trojan', 'Hecuba', 'Lyon Center', 'SAL', 'USC Bookstore', 'Leavey') not null,
    pickup_loc       int         not null,
    sold             boolean     not null,
    held             boolean     not null,
    FOREIGN KEY (owner_iD) REFERENCES user (id)
) character set 'utf8mb4';


drop table if exists favorite_listing;
create table if not exists favorite_listing
(
    user_id    int  not null,
    listing_id int  not null,
    gmt_create long not null,
    primary key (user_id, listing_id),
    foreign key (user_id) references user (id),
    foreign key (listing_id) references listing (id)
)