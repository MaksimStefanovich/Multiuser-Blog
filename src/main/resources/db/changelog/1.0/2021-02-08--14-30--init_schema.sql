--liquibase formatted sql

--changeset stefanovich:t-1
--comment: создание и заполнение таблиц

create table captcha_codes
(
    id          int      not null
        primary key,
    code        tinytext not null,
    secret_code tinytext not null,
    time        datetime not null
);

create table global_settings
(
    id    int          not null
        primary key,
    code  varchar(255) not null,
    name  varchar(255) not null,
    value varchar(255) not null
);

create table hibernate_sequence
(
    next_val bigint null
);

create table tags
(
    id   int          not null
        primary key,
    name varchar(255) not null
);

create table users
(
    id           int          not null
        primary key,
    code         varchar(255) null,
    email        varchar(255) not null,
    is_moderator tinyint      not null,
    name         varchar(255) not null,
    password     varchar(255) not null,
    photo        text         null,
    reg_time     datetime     not null
);

create table posts
(
    id                int          not null
        primary key,
    is_active         tinyint      not null,
    moderation_status varchar(255) null,
    text              text         not null,
    time              datetime     not null,
    title             varchar(255) not null,
    view_count        int          null,
    moderator_id      int          null,
    user_id           int          not null,
    constraint FK5lidm6cqbc7u4xhqpxm898qme
        foreign key (user_id) references users (id),
    constraint FK6m7nr3iwh1auer2hk7rd05riw
        foreign key (moderator_id) references users (id)
);

create table post_comments
(
    id          int      not null
        primary key,
    text        longtext not null,
    time        datetime not null,
    messages_id int      not null,
    parent_id   int      null,
    user_id     int      not null,
    constraint FK833asosrxtjqv20bhk3q8fq2u
        foreign key (messages_id) references posts (id),
    constraint FKc3b7s6wypcsvua2ycn4o1lv2c
        foreign key (parent_id) references post_comments (id),
    constraint FKsnxoecngu89u3fh4wdrgf0f2g
        foreign key (user_id) references users (id)
);

create table post_votes
(
    id          int      not null
        primary key,
    time        datetime not null,
    value       tinyint  not null,
    messages_id int      not null,
    user_id     int      not null,
    constraint FK3b7wllsgh8rdvys7ihyl9bbfo
        foreign key (messages_id) references posts (id),
    constraint FK9q09ho9p8fmo6rcysnci8rocc
        foreign key (user_id) references users (id)
);

create table posts_tags
(
    posts_id int not null,
    tags_id  int not null,
    constraint FK79lx4quime8ct09nbmmf6wuao
        foreign key (tags_id) references tags (id),
    constraint FKi7se260d9epoxous2p6f1sl5k
        foreign key (posts_id) references posts (id)
);
