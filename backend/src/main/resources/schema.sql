create table if not exists user (
    id          identity        not null,
    full_name   varchar(255)    not null,
    user_name   varchar(255)    not null,
    password    varchar(255)    not null,
    status      varchar(255)    not null,
    role        varchar(255)    not null,
    loan        DOUBLE
);