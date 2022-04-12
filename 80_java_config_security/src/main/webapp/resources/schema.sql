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

-- uuid : 파일의 정보
-- 나중엔 bigint를 20정도로 해두는게 좋다
-- int는 10으로 하는게 맞고
-- bigint는 11이 가장 최솟값이다
-- 파일은 mod_at이 의미가 없다(파일 자체의 수정은 안 되기 때문에)

-- 현재 board의 cmt_qty를 실제 댓글량과 비교해서 갯수를 업데이트 하는 쿼리
update board set cmt_qty = (
select count(cno) from b_comment
where b_comment.bno = board.bno
);


drop table member cascade;

CREATE TABLE `member` (
  `email` varchar(100) NOT NULL,
  `pwd` varchar(1000) NOT NULL,
  `nick_name` varchar(100) NOT NULL,
  `reg_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_login` datetime DEFAULT NULL,
  `grade` tinyint DEFAULT '10',
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

drop table auth_member cascade;

CREATE TABLE `auth_member` (
  `email` varchar(100) NOT NULL,
  `auth` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table auth_member add constraint fk_auth
foreign key (email) references member(email);
비번을 길게 설정하는 이유는 보안설정하면 길어지기 때문에