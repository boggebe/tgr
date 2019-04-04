create keyspace if not exists tgr with replication={
    'class': 'SimpleStrategy',
    'replication_factor': 3
};

create table if not exists tgr.product_price(
    partNumber bigint,
    price float,
    primary key (partNumber)
);

insert into tgr.product_price (
    partNumber,
    price
) values (
    13860428,
    19.98
);

insert into tgr.product_price (
    partNumber,
    price
) values (
    75878624,
    2.49
);

insert into tgr.product_price (
    partNumber,
    price
) values (
    17098823,
    165.99
);