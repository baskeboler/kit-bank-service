CREATE TABLE if not exists users (
    id uuid primary key,
    name text not null,
    email text not null,
    password text not null,
    created_at timestamp default current_timestamp
) ;