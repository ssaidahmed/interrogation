--liquibase formatted sql
--changeset author:ah26212
DROP TABLE IF EXISTS poll cascade ;
DROP TABLE IF EXISTS question;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE IF NOT EXISTS global_seq START 1;

create table if not exists poll (
  id integer primary key default nextval('global_seq'),
  title varchar(50) not null,
  start_date timestamp not null,
  end_date timestamp not null,
  activity bool default true not null
);

create table if not exists question (
  id integer primary key default nextval('global_seq'),
  poll_id integer not null,
  question_text text not null,
  display varchar(50) not null,
  foreign key (poll_id) references poll(id) on delete cascade
);

create unique index display_poll_id on question (poll_id, display);