create table micro.user
(
    id int auto_increment
        primary key,
    name varchar(10) not null,
    password varchar(15) default '123456' not null,
    address varchar(25) null,
    phone varchar(15) null,
    createTime timestamp null,
    updateTime timestamp null
)
    charset=gb2312;

