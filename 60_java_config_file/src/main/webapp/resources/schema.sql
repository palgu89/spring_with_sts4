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

drop table if exists b_file;

create table b_file (
uuid varchar(256) primary key,
save_dir varchar(256) not null,
file_name varchar(512) not null,
file_type tinyint(1) default 0,
bno bigint(11),
file_size bigint,
reg_at datetime default now()
);

uuid : 파일의 정보
나중엔 bigint를 20정도로 해두는게 좋다
int는 10으로 하는게 맞고
bigint는 11이 가장 최솟값이다
파일은 mod_at이 의미가 없다(파일 자체의 수정은 안 되기 때문에)