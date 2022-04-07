drop table if exists board;

create table board (
bno bigint not null auto_increment,
title varchar(200) not null,
content text not null,
writer varchar(100) not null,
reg_at datetime default now(),
mod_at datetime default now(),
read_count int(10) default 0,
primary key (bno)
);

drop table if exists b_comment;

create table b_comment (
cno bigint auto_increment,
bno bigint not null,
writer varchar(100) not null,
content text not null,
reg_at datetime default now(),
mod_at datetime default now(),
primary key (cno)
);