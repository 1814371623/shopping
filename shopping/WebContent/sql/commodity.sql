-- commodity�� ������Ʒ��Ϣ --
create table commodity
(
    commodity_number int(10) primary key not null auto_increment,
    commodity_name   varchar(255) not null,
    commodity_made   varchar(255),
    commodity_price  float(10,2) not null,
    commodity_balance int(10) not null,
    commodity_pic    varchar(255)not null,
    commodity_id int(11) ,
    foreign key(commodity_id) references classify(gid) 
);


       
         
