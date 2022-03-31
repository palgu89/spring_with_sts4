CREATE TABLE `product` (
  `pno` bigint NOT NULL AUTO_INCREMENT,
  `category` varchar(100) DEFAULT NULL,
  `pname` varchar(100) NOT NULL,
  `price` int NOT NULL,
  `writer` varchar(100) DEFAULT NULL,
  `description` text,
  `made_by` varchar(100) NOT NULL,
  `reg_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `mod_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `read_count` int DEFAULT '0',
  `cmt_qty` int DEFAULT '0',
  PRIMARY KEY (`pno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `member` (
  `email` varchar(100) NOT NULL,
  `pwd` varchar(1000) NOT NULL,
  `nick_name` varchar(100) NOT NULL,
  `reg_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_login` datetime DEFAULT NULL,
  `grade` tinyint DEFAULT '10',
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `comment` (
  `cno` bigint NOT NULL AUTO_INCREMENT,
  `pno` bigint DEFAULT -1,
  `writer` varchar(100) DEFAULT NULL,
  `content` varchar(1000) NOT NULL,
  `reg_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `mod_at` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table product add constraint fk_product_writer
foreign key (writer) references member(email)
on update cascade
on delete no action;

alter table comment add constraint fk_commnet_writer
foreign key (writer) references member(writer)
on update cascade
on delete no action;

alter table comment add constraint fk_commnet_pno
foreign key (pno) references product(pno)
on update cascade
on delete no action;

