create keyspace if not exists tgr with replication={
    'class': 'SimpleStrategy',
    'replication_factor': 3
};

create table if not exists tgr.product_price(
    id bigint,
    price float,
    currency ascii,
    primary key (id)
);

insert into tgr.product_price (
    id,
    price,
    currency
) values (
    13860428,
    19.98,
    'USD'
);

insert into tgr.product_price (
    id,
    price,
    currency
) values (
    75878624,
    2.49,
    'USD'
);

insert into tgr.product_price (
    id,
    price,
    currency
) values (
    17098823,
    165.99,
    'USD'
);