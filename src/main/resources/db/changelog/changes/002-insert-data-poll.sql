--liquibase formatted sql
--changeset author:ah561
ALTER SEQUENCE global_seq RESTART WITH 1;
delete from poll;

insert into poll (title, start_date, end_date, activity)
values ('Деньги', '2020-05-05', '2020-06-05', true),
      ('Работа', '2020-05-05', '2020-06-15', true),
       ('Auto', '2020-05-05', '2020-06-15', false),
      ('Программирование', '2020-05-05', '2020-06-15', true);