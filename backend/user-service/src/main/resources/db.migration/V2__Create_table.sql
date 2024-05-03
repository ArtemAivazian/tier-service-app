create table users (
   user_id bigint not null,
   first_name varchar(50) not null,
   last_name varchar(50) not null,
   email varchar(120) not null unique,
   encrypted_password varchar(255) not null unique,
   role varchar(255) check (role in ('ADMIN','USER')),
   primary key (user_id)
);


CREATE SEQUENCE user_sequence
    START WITH 1
    INCREMENT BY 1;
