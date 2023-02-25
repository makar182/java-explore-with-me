drop table if exists compilation_event;
drop table if exists compilations;
drop table if exists user_requests;
drop table if exists events;
drop table if exists categories;
drop table if exists users;

create table if not exists users
(
    id    bigint generated by default as identity not null,
    name  varchar(255)                            not null,
    email varchar(255)                            not null,
    constraint pk_user
        primary key (id),
    constraint un_user_email unique (email)
);

create table if not exists categories
(
    id      bigint generated by default as identity not null,
    name    varchar(255)                            not null,
    user_id bigint                                  not null,
    constraint pk_category
        primary key (id),
    constraint fk_category_user_id
        foreign key (user_id) references users (id),
    constraint un_category_name unique (name)
);

create table if not exists events
(
    id                 bigint generated by default as identity not null,
    title              varchar(255)                            not null,
    annotation         varchar(4000)                           not null,
    is_paid            boolean                                 not null,
    views              bigint,
    confirmed_requests bigint,
    event_date         timestamp without time zone             not null,
    user_id            bigint                                  not null,
    category_id        bigint                                  not null,
    constraint pk_event
        primary key (id),
    constraint fk_event_user_id
        foreign key (user_id) references users (id),
    constraint fk_event_category_id
        foreign key (category_id) references categories (id)
);

create table if not exists user_requests
(
    id       bigint generated by default as identity not null,
    event_id bigint                                  not null,
    user_id  bigint                                  not null,
    status   varchar(50)                             not null,
    created  timestamp without time zone             not null,
    constraint pk_user_request
        primary key (id),
    constraint fk_user_request_user_id
        foreign key (user_id) references users (id),
    constraint fk_user_request_event_id
        foreign key (event_id) references events (id)
);

create table if not exists compilations
(
    id        bigint generated by default as identity not null,
    title     varchar(255)                            not null,
    is_pinned boolean                                 not null,
    user_id   bigint                                  not null,
    constraint pk_compilation
        primary key (id),
    constraint fk_compilation_user_id
        foreign key (user_id) references users (id)
);

create table if not exists compilation_event
(
    id             bigint generated by default as identity not null,
    compilation_id bigint                                  not null,
    event_id       bigint                                  not null,
    constraint pk_compilation_event
        primary key (id),
    constraint fk_compilation_event_compilation_id
        foreign key (compilation_id) references compilations (id),
    constraint fk_compilation_event_event_id
        foreign key (event_id) references users (id)
);