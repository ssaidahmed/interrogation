--liquibase formatted sql
--changeset author:ahh253
delete from question;
insert into question (question_text, display, poll_id)
values ('Сколько стоит доллар в рублях?', '1',1),
       ('Что такое девальвация?', '2',1),
      ('Подскажите сайты, на которых можно заработать хотя бы от 150 руб. в сутки?', '1',2),
      ('Подскажите сайты, на которых можно заработать хотя бы от 22150 руб. в месяц?', '2',2),
      ('Сколько лет java?', '1',3),
      ('Сколько лет C#?', '2',3),
      ('Назовите журнал о деньгах?', '3',2);

